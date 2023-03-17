package cloud.xuxiaowei.next.webservice.point;

import cloud.xuxiaowei.next.core.properties.CloudWebServiceProperties;
import cloud.xuxiaowei.next.utils.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.net.MediaType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.soap.SOAPConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.dom4j.*;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * CXF 身份验证入口点
 * <p>
 * 1、要求 WebService 命名空间在 {@link CloudWebServiceProperties#getNamespaceUriList()} 中
 * <p>
 *
 * @author xuxiaowei
 * @see MediaType#XML_UTF_8 SOAP 1.1
 * @see ContentType#APPLICATION_SOAP_XML SOAP 1.2
 * @since 0.0.1
 */
@Slf4j
@Component(CxfAuthenticationEntryPointImpl.CXF_AUTHENTICATIONENTRY_POINT_BEAN_NAME)
public class CxfAuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	public static final String CXF_AUTHENTICATIONENTRY_POINT_BEAN_NAME = "cxfAuthenticationEntryPoint";

	private CloudWebServiceProperties cloudWebServiceProperties;

	@Autowired
	public void setCloudWebServiceProperties(CloudWebServiceProperties cloudWebServiceProperties) {
		this.cloudWebServiceProperties = cloudWebServiceProperties;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		String requestXml = RequestUtils.getInputStream(request);
		log.info("CXF 身份验证入口点-请求：{}", requestXml);

		XmlMapper xmlMapper = new XmlMapper();

		if (!StringUtils.hasText(requestXml)) {
			String msg = String.format("WebService 请求体不能为空：%s", requestXml);
			log.error(msg);
			Response<?> error = Response.error(msg);
			error.setExplain(requestXml);
			String string = xmlMapper.writeValueAsString(error);
			// 请求体存在问题，无需关心返回内容的格式
			ResponseUtils.response(response, string, MediaType.XML_UTF_8.toString());
			return;
		}

		Document requestDocument;
		try {
			requestDocument = DocumentHelper.parseText(requestXml);
		}
		catch (DocumentException e) {
			String msg = "WebService 请求体不是 标准XML";
			log.error(msg, e);
			Response<?> error = Response.error(msg);
			error.setExplain(requestXml);
			String string = xmlMapper.writeValueAsString(error);
			// 请求体存在问题，无需关心返回内容的格式
			ResponseUtils.response(response, string, MediaType.XML_UTF_8.toString());
			return;
		}

		response(requestDocument, response, authException);

	}

	/**
	 * 响应数据
	 * @param requestDocument XML Document
	 * @param response 响应
	 * @param authException 异常
	 * @throws IOException 响应异常
	 */
	private void response(Document requestDocument, HttpServletResponse response, AuthenticationException authException)
			throws IOException {
		// 获取父节点
		Element rootElement = requestDocument.getRootElement();

		// QName 命名空间
		QName rootElementQname = rootElement.getQName();
		// 获取文档工厂
		DocumentFactory documentFactory = rootElementQname.getDocumentFactory();
		// 获取所有 QName 命名空间
		List<QName> qNames = documentFactory.getQNames();

		// 默认值
		String soap = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		String namespace = "http://webservice.xuxiaowei.cloud";
		String methodName = "";

		// 找出调用的方法与命名空间
		for (QName qName : qNames) {

			String name = qName.getName();
			String namespaceUri = qName.getNamespaceURI();

			if ("Envelope".equals(name)) {
				soap = namespaceUri;
			}

			log.info(name + "\t" + namespaceUri);
			if (cloudWebServiceProperties.getNamespaceUriList().contains(namespaceUri)) {
				namespace = namespaceUri;
				methodName = name;
			}
		}

		Document responseDocument = DocumentHelper.createDocument();

		Element envelope = responseDocument.addElement("soap:Envelope");

		if (SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE.contains(soap)) {
			envelope.addAttribute("xmlns:soap", SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE);
			response.setContentType(MediaType.XML_UTF_8.toString());
		}
		else if (SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE.contains(soap)) {
			envelope.addAttribute("xmlns:soap", SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE);
			response.setContentType(ContentType.APPLICATION_SOAP_XML.toString());
		}

		Element body = envelope.addElement("Body");
		body.setName("soap:Body");

		Element methodNameResponse = body.addElement("Response");
		methodNameResponse.setName("ns2:" + methodName + "Response");
		methodNameResponse.addAttribute("xmlns:ns2", namespace);

		Element responseElement = methodNameResponse.addElement("response");

		Element codeElement = responseElement.addElement(Response.CODE);
		Element msgElement = responseElement.addElement(Response.MSG);
		Element dataElement = responseElement.addElement(Response.DATA);
		Element fieldElement = responseElement.addElement(Response.FIELD);
		Element explainElement = responseElement.addElement(Response.EXPLAIN);
		Element requestIdElement = responseElement.addElement(Response.REQUEST_ID);

		if (!(authException instanceof InsufficientAuthenticationException)) {
			explainElement.setText(authException.getMessage());
		}

		String requestId = MDC.get(Constant.REQUEST_ID);
		if (requestId != null) {
			requestIdElement.setText(requestId);
		}

		codeElement.setText(CodeEnums.T00000.code);
		msgElement.setText(CodeEnums.T00000.msg);

		String asXml = Dom4jUtils.asXml(responseDocument);
		log.info("CXF 身份验证入口点-响应：{}", asXml);

		response.getWriter().println(asXml);
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

}
