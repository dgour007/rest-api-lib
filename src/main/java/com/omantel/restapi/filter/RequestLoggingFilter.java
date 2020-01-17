/**
 * 
 */
package com.omantel.restapi.filter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import com.omantel.restapi.service.ApiLoggingService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dhiraj Gour
 * @date 21 August 2019
 *
 */
@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	@Autowired
	ApiLoggingService apiLoggingService;
	
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		log.info(message);
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