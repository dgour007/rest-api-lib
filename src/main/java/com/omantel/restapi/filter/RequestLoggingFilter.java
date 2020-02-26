/**
 * 
 */
package com.omantel.restapi.filter;

import java.util.Locale;
//import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.omantel.restapi.service.ApiLoggingService;

import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.message.ObjectMessage;

/**
 * @author Dhiraj Gour
 * @date 21 August 2019
 *
 */
@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	//private static final Logger log = LogManager.getLogger(RequestLoggingFilter.class);
	
	@Autowired
	ApiLoggingService apiLoggingService;
	
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		/*Map<String, Object> newMap = null;
		
		if (message.indexOf("payload=") != -1) {
			try {
				newMap = new ObjectMapper().readValue(
						message.substring(message.indexOf("payload=")+8), 
						new TypeReference<Map<String, Object>>() {});
				
				log.info("{} {}", 
						message.substring(0, message.indexOf("payload=")+8), 
						new ObjectMessage(newMap));
				
			} catch (JsonMappingException e) {
				log.error("Failed to get the payload from message {} due to {}", 
						message, e.getMessage());
			} catch (JsonProcessingException e) {
				log.error("Failed to get the payload from message {} due to {}", 
						message, e.getMessage());
			}
		} else {
			log.info(message);
		}*/
		
		//message = message.replaceAll("\"", "'");
		log.info(message.replaceAll("\"", "'"));
		apiLoggingService.insertApiLog(request, message);
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		String localeStr = request.getHeader("Accept-Language");
		if (localeStr != null) {
			LocaleContextHolder.setLocale(new Locale(localeStr));
		}
	}
}