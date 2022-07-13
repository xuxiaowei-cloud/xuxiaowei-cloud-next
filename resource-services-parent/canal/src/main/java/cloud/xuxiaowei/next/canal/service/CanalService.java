package cloud.xuxiaowei.next.canal.service;

/**
 * canal 服务 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface CanalService {

	/**
	 * 插入
	 * @param sql SQL
	 * @return 返回 插入结果
	 */
	int insert(String sql);

	/**
	 * 更新
	 * @param sql SQL
	 * @return 返回 更新结果
	 */
	int update(String sql);

	/**
	 * 删除
	 * @param sql SQL
	 * @return 返回 删除结果
	 */
	int delete(String sql);

}
