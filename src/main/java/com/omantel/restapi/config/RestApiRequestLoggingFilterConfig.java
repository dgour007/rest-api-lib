/**
 * 
 */
package com.omantel.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.omantel.restapi.filter.RequestLoggingFilter;

/**
 * @author Dhriaj Gour
 * @date 21 August 2019
 *
 */
@Component
public class RestApiRequestLoggingFilterConfig {

	@Bean
    public RequestLoggingFilter logFilter() {
		
		RequestLoggingFilter filter
          = new RequestLoggingFilter();
        
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("Request Body : ");
        filter.setAfterMessageSuffix("");
        return filter;
        
    }
}
