package cloud.xuxiaowei.next.canal.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * canal Mapper 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface CanalMapper {

	/**
	 * 插入
	 * @param sql SQL
	 * @return 返回 插入结果
	 */
	int insert(@Param("sql") String sql);

	/**
	 * 更新
	 * @param sql SQL
	 * @return 返回 更新结果
	 */
	int update(@Param("sql") String sql);

	/**
	 * 删除
	 * @param sql SQL
	 * @return 返回 删除结果
	 */
	int delete(@Param("sql") String sql);

}
