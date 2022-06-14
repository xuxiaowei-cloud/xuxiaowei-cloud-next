package cloud.xuxiaowei.next.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 权限与权限说明表
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
@Data
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限
     */
    @TableId(type = IdType.INPUT)
    private String authority;

    /**
     * 权限说明
     */
    @TableField("`explain`")
    private String explain;


}
