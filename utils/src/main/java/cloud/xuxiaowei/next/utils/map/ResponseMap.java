package cloud.xuxiaowei.next.utils.map;

import cloud.xuxiaowei.next.utils.CodeEnums;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应 Map 数据
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
public class ResponseMap extends cloud.xuxiaowei.next.utils.Response<Map<String, Object>> {

    /**
     * 仅为自动装载数据使用
     */
    private ResponseMap() {
        super(null, null);
    }

    public ResponseMap(String code, String msg) {
        super(code, msg);
    }

    public ResponseMap(String code, String msg, String field) {
        super(code, msg, field);
    }

    public static ResponseMap ok() {
        return new ResponseMap(CodeEnums.OK.code, CodeEnums.OK.msg);
    }

    public static ResponseMap ok(String msg) {
        return new ResponseMap(CodeEnums.OK.code, msg);
    }

    public static ResponseMap ok(Map<String, Object> data) {
        ResponseMap response = new ResponseMap(CodeEnums.OK.code, CodeEnums.OK.msg);
        response.setData(data);
        return response;
    }

    public static ResponseMap ok(Map<String, Object> data, String msg) {
        ResponseMap response = new ResponseMap(CodeEnums.OK.code, CodeEnums.OK.msg);
        response.setData(data);
        return response;
    }

    public static ResponseMap error() {
        return new ResponseMap(CodeEnums.ERROR.code, CodeEnums.ERROR.msg);
    }

    public static ResponseMap error(String msg) {
        return new ResponseMap(CodeEnums.ERROR.code, msg);
    }

    public static ResponseMap error(String code, String msg) {
        return new ResponseMap(code, msg);
    }

    /**
     * 增加响应数据
     *
     * @param key   Key
     * @param value Value
     * @return 返回 支持链式调用
     */
    public ResponseMap put(String key, Object value) {
        Map<String, Object> data = this.getData();
        if (data == null) {
            data = new HashMap<>(8);
            this.setData(data);
        }
        data.put(key, value);
        return this;
    }

}
