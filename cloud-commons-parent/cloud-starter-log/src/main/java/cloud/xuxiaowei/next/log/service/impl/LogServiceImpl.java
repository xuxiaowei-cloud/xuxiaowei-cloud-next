package cloud.xuxiaowei.next.log.service.impl;

import cloud.xuxiaowei.next.log.entity.Log;
import cloud.xuxiaowei.next.log.mapper.LogMapper;
import cloud.xuxiaowei.next.log.service.ILogService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-01
 */
@Service
@DS("log")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
