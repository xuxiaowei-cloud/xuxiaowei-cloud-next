package cloud.xuxiaowei.next.canal.scheduled;

import cloud.xuxiaowei.next.canal.properties.CloudCanalProperties;
import cloud.xuxiaowei.next.canal.service.CanalService;
import cloud.xuxiaowei.next.utils.DateUtils;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * Canal 命令行运行器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
@EnableAsync
public class CanalScheduled {

	private CloudCanalProperties cloudCanalProperties;

	private CanalService canalService;

	@Autowired
	public void setCanalService(CanalService canalService) {
		this.canalService = canalService;
	}

	@Autowired
	public void setCloudCanalProperties(CloudCanalProperties cloudCanalProperties) {
		this.cloudCanalProperties = cloudCanalProperties;
	}

	@Scheduled(initialDelay = 1000, fixedRate = Integer.MAX_VALUE)
	private void canal() {

		log.info("canal 启动 ……");

		String hostname = cloudCanalProperties.getHostname();
		int port = cloudCanalProperties.getPort();
		String destination = cloudCanalProperties.getDestination();
		String username = cloudCanalProperties.getUsername();
		String password = cloudCanalProperties.getPassword();
		int batchSize = cloudCanalProperties.getBatchSize();

		CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(hostname, port),
				destination, username, password);

		try {
			connector.connect();
			connector.subscribe("xuxiaowei_cloud_next\\.oauth2.*");
			connector.rollback();
			try {
				while (true) {
					// 尝试从master那边拉去数据batchSize条记录，有多少取多少
					Message message = connector.getWithoutAck(batchSize);
					long batchId = message.getId();
					int size = message.getEntries().size();
					if (batchId == -1 || size == 0) {
						Thread.sleep(1000);
					}
					else {
						dataHandle(message.getEntries());
					}
					connector.ack(batchId);

				}
			}
			catch (InterruptedException | InvalidProtocolBufferException e) {
				log.error("Canal异常：", e);
			}
		}
		finally {
			connector.disconnect();
		}
	}

	private void dataHandle(List<CanalEntry.Entry> entries) throws InvalidProtocolBufferException {
		for (CanalEntry.Entry entry : entries) {
			if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
				CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
				CanalEntry.EventType eventType = rowChange.getEventType();

				if (eventType == CanalEntry.EventType.DELETE) {
					deleteSql(entry);
				}
				else if (eventType == CanalEntry.EventType.UPDATE) {
					updateSql(entry);
				}
				else if (eventType == CanalEntry.EventType.INSERT) {
					insertSql(entry);
				}
			}
		}
	}

	private void insertSql(CanalEntry.Entry entry) {

		CanalEntry.Header header = entry.getHeader();
		long executeTime = header.getExecuteTime();
		String executeTimeFormat = DateUtils.format(executeTime, DEFAULT_DATE_TIME_FORMAT);

		try {
			CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
			List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
			for (CanalEntry.RowData rowData : rowDatasList) {
				List<CanalEntry.Column> columnList = rowData.getAfterColumnsList();

				StringBuilder sql = new StringBuilder("insert into " + entry.getHeader().getTableName() + " (");
				for (CanalEntry.Column column : columnList) {
					sql.append(column.getName()).append(",");
				}

				// 时间
				sql.append("log_date").append(",");
				// 类型
				sql.append("log_type");

				sql.append(") VALUES (");

				for (CanalEntry.Column column : columnList) {
					String value = column.getValue();
					boolean isNull = column.getIsNull();
					if (isNull) {
						sql.append("null");
					}
					else {
						sql.append("'").append(value).append("'");
					}
					sql.append(",");
				}

				// 时间
				sql.append("'").append(executeTimeFormat).append("'").append(",");
				// 类型
				sql.append("'").append("insert").append("'");

				sql.append(")");

				log.info("准备插入：{}", sql);
				int insert = canalService.insert(sql.toString());
				log.info("插入结果：{}", insert);

			}
		}
		catch (InvalidProtocolBufferException e) {
			log.error("Calan 插入异常：", e);
		}
	}

	private void deleteSql(CanalEntry.Entry entry) {

		CanalEntry.Header header = entry.getHeader();
		long executeTime = header.getExecuteTime();

		String executeTimeFormat = DateUtils.format(executeTime, DEFAULT_DATE_TIME_FORMAT);

		try {
			CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
			List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
			for (CanalEntry.RowData rowData : rowDatasList) {

				StringBuilder sql = new StringBuilder("insert into " + entry.getHeader().getTableName() + " (");
				List<CanalEntry.Column> oldColumnList = rowData.getBeforeColumnsList();
				for (CanalEntry.Column column : oldColumnList) {
					sql.append(column.getName()).append(",");
				}

				// 时间
				sql.append("log_date").append(",");
				// 类型
				sql.append("log_type");

				sql.append(") VALUES (");

				for (CanalEntry.Column column : oldColumnList) {
					String value = column.getValue();
					boolean isNull = column.getIsNull();
					if (isNull) {
						sql.append("null");
					}
					else {
						sql.append("'").append(value).append("'");
					}
					sql.append(",");
				}

				// 时间
				sql.append("'").append(executeTimeFormat).append("'").append(",");
				// 类型
				sql.append("'").append("delete").append("'");

				sql.append(")");

				log.info("准备逻辑删除：{}", sql);
				int update = canalService.insert(sql.toString());
				log.info("逻辑删除结果：{}", update);

			}
		}
		catch (InvalidProtocolBufferException e) {
			log.error("Calan 逻辑删除异常：", e);
		}
	}

	@SneakyThrows
	private void updateSql(CanalEntry.Entry entry) {

		CanalEntry.Header header = entry.getHeader();
		long executeTime = header.getExecuteTime();
		ObjectMapper objectMapper = new ObjectMapper();

		String executeTimeFormat = DateUtils.format(executeTime, DEFAULT_DATE_TIME_FORMAT);

		try {
			CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
			List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
			for (CanalEntry.RowData rowData : rowDatasList) {
				List<CanalEntry.Column> newColumnList = rowData.getAfterColumnsList();

				StringBuilder sql = new StringBuilder("insert into " + entry.getHeader().getTableName() + " (");
				for (CanalEntry.Column column : newColumnList) {
					sql.append(column.getName()).append(",");
				}

				// 时间
				sql.append("log_date").append(",");
				// 更新之前
				sql.append("update_before").append(",");
				// 类型
				sql.append("log_type");

				sql.append(") VALUES (");

				for (CanalEntry.Column column : newColumnList) {
					String value = column.getValue();
					boolean isNull = column.getIsNull();
					if (isNull) {
						sql.append("null");
					}
					else {
						sql.append("'").append(value).append("'");
					}
					sql.append(",");
				}

				// 时间
				sql.append("'").append(executeTimeFormat).append("'").append(",");

				List<CanalEntry.Column> oldColumnList = rowData.getBeforeColumnsList();
				Map<String, Object> map = new HashMap<>(16);
				for (CanalEntry.Column column : oldColumnList) {
					String name = column.getName();
					String value = column.getValue();
					try {
						@SuppressWarnings("rawtypes")
						Map readValue = objectMapper.readValue(value, Map.class);
						map.put(name, readValue);
					}
					catch (Exception e) {
						map.put(name, value);
					}
				}
				String value = objectMapper.writeValueAsString(map);
				// 更新之前
				sql.append("'").append(value).append("'").append(",");

				// 类型
				sql.append("'").append("update").append("'");

				sql.append(")");

				log.info("准备更新：{}", sql);
				int update = canalService.insert(sql.toString());
				log.info("更新结果：{}", update);

			}
		}
		catch (InvalidProtocolBufferException e) {
			log.error("Calan 更新异常：", e);
		}
	}

}
