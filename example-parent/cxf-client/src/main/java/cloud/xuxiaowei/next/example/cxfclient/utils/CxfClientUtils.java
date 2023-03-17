package cloud.xuxiaowei.next.example.cxfclient.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;

import java.util.List;
import java.util.Map;

/**
 * CXF 客户端 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class CxfClientUtils {

	/**
	 * 创建 CXF 客户端
	 * @param address 地址
	 * @param serviceClass （WebService响应结果）服务接口
	 * @param headers 请求头
	 * @param <T> 泛型
	 * @return 返回 （WebService响应结果）服务接口
	 */
	public static <T> T create(String address, Class<T> serviceClass, Map<String, List<String>> headers) {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

		jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
		jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

		jaxWsProxyFactoryBean.setAddress(address);
		jaxWsProxyFactoryBean.setServiceClass(serviceClass);
		@SuppressWarnings("all")
		T service = (T) jaxWsProxyFactoryBean.create();

		if (headers != null) {
			Client client = ClientProxy.getClient(service);
			client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
		}

		return service;
	}

}
