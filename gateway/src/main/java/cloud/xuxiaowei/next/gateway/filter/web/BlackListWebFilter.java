package cloud.xuxiaowei.next.gateway.filter.web;

import cloud.xuxiaowei.next.core.properties.CloudBlackListProperties;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.IpAddressMatcher;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

/**
 * 黑名单 过滤器
 * <p>
 * 指定 IP、URL、用户、客户、域名（非授权域名解析）等禁止访问
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Component
public class BlackListWebFilter implements WebFilter, Ordered {

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 30000;

	private CloudBlackListProperties cloudBlackListProperties;

	@Autowired
	public void setCloudBlackListProperties(CloudBlackListProperties cloudBlackListProperties) {
		this.cloudBlackListProperties = cloudBlackListProperties;
	}

	@Setter
	private int order = ORDERED;

	@Override
	public int getOrder() {
		return order;
	}

	@NonNull
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		URI uri = request.getURI();
		String path = uri.getPath();

		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress == null) {
			Response<?> error = Response.error(CodeEnums.X10003.code, CodeEnums.X10003.msg);
			return ResponseUtils.writeWith(response, error);
		}
		InetAddress address = remoteAddress.getAddress();
		String hostAddress = address.getHostAddress();

		List<String> ipList = cloudBlackListProperties.getIpList();

		for (String ip : ipList) {
			IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ip);
			boolean matches = ipAddressMatcher.matches(hostAddress);
			if (matches) {
				Response<?> error = Response.error(CodeEnums.X10004.code, CodeEnums.X10004.msg);
				return ResponseUtils.writeWith(response, error);
			}
		}

		AntPathMatcher antPathMatcher = new AntPathMatcher();
		List<CloudBlackListProperties.BlackList> services = cloudBlackListProperties.getServices();
		for (CloudBlackListProperties.BlackList service : services) {
			String name = service.getName();
			List<String> pathList = service.getPathList();
			for (String p : pathList) {
				String pattern = name.startsWith("/") ? name : "/" + name;
				pattern = p.startsWith("/") ? pattern + p : pattern + "/" + p;
				boolean match = antPathMatcher.match(pattern, path);
				if (match) {
					Response<?> error = Response.error(CodeEnums.X10005.code, CodeEnums.X10005.msg);
					return ResponseUtils.writeWith(response, error);
				}
			}
		}

		return chain.filter(exchange);
	}

}
