package com.omantel.restapi.config;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dhiraj Gour
 * @since 24 July 2017
 * @version 1.0
 */

@Configuration
public class RestApiRestTemplateConfig {

	@Value("${rest.template.read.timeout}")
	int readTimeout;

	@Value("${rest.template.connection.timeout}")
	int connectTimeout;

	@Value("${rest.template.user}")
	String restUsername;

	@Value("${rest.template.password}")
	String restPassword;

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager result = null;
		try {
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();

			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
			PlainConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();

			Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", plainsf).register("https", csf).build();

			result = new PoolingHttpClientConnectionManager(r);
			// result = new PoolingHttpClientConnectionManager();
			result.setMaxTotal(70);
			result.setDefaultMaxPerRoute(70);

		} catch (Exception e) {

		}
		return result;
	}

	@Bean
	public RequestConfig requestConfig() {

		// logger.debug("Read Timeout value {}, Connect Timeout value {}", readTimeout,
		// connectTimeout);

		RequestConfig result = RequestConfig.custom().setConnectionRequestTimeout(connectTimeout * 1000)
				.setConnectTimeout(connectTimeout * 1000).setSocketTimeout(readTimeout * 1000).build();
		return result;
	}

	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig) {

		CloseableHttpClient result = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingHttpClientConnectionManager).build();

		return result;
	}

	@Bean
	public RestTemplate restTemplate(HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
//		RestTemplate restTemplate = new RestTemplate(requestFactory);
//		restTemplate
//				.setInterceptors(Collections.singletonList(new RestApiRestAuthInterceptor(restUsername, restPassword)));

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (interceptors == null) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RequestResponseLoggingInterceptor());
		interceptors.add(new RestApiRestAuthInterceptor(restUsername, restPassword));
		restTemplate.setInterceptors(interceptors);
		return restTemplate;

	}

}