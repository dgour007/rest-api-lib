/**
 * 
 */
package com.omantel.restapi.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.omantel.restapi.bean.Log;
import com.omantel.restapi.service.ApiLoggingService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dhiraj Gour
 * @date 26 August 2019 
 *
 */
@Service
@Slf4j
public class ApiLoggingServiceImpl implements ApiLoggingService {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${api.logging.url}")
	String apiLoggingUrl;
	
	@Value("${api.logging.enabled}")
	boolean apiLoggingEnabled;
	
	@Override
	public void insertApiLog(HttpServletRequest request, String message) {
		
		try {
			String apiLog = request.getAttribute("API_LOG") != null ? (String)request.getAttribute("API_LOG") : null;
			
			if ("success".equalsIgnoreCase(apiLog)) {
				
				Log lg = new Log();
				String uri = request.getRequestURI() + (request.getQueryString() != null ? "?"+request.getQueryString() : "");
				String app = null;
				
				lg.setUrl(uri);
				lg.setAppUserId(request.getRemoteUser());
				//lg.setUserId(request.getHeader("From"));
				lg.setSessionId(request.getHeader("logSessionId"));
				lg.setRequestId(request.getHeader("logRequestId"));
				lg.setUserId(request.getHeader("logUserId"));
				
				if (uri != null && uri.indexOf("/") == 0) {
					app = uri.substring(1);
					if (app.indexOf("/") != -1) {
						app = app.substring(0, app.indexOf("/"));
					}
					lg.setApp(app);
				}
				
				if (message != null && message.indexOf("payload=") != -1) {
					String payload = message.substring(message.indexOf("payload=") + 8);
					
					if (payload != null) {
						payload = payload//.replaceAll(" ", "")
								.replaceAll("\n", "").replaceAll("\r", "");
						
						//if (payload != null && payload.length() > 1000) {
						if (payload.length() > 1000) {
							payload = payload.substring(0,  1000);
						}
					}
					
					lg.setPayload(payload);
					
					final ObjectNode node = new ObjectMapper().readValue(payload, ObjectNode.class);
					if (node.has("subscriberNo")) {
					    lg.setExternalId(node.get("subscriberNo").textValue());
					}
					if (node.has("accountNo")) {
					    lg.setAccountNo(node.get("accountNo").textValue());
					}
				}
				if (apiLoggingEnabled) {
					restTemplate.postForObject(apiLoggingUrl, lg, Integer.class);
				}
			}
			
		} catch (Exception e) {
			log.error("Failed to log the api request due to error {}", e.getMessage());
		}
	}
}
