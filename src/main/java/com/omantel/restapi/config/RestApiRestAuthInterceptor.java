/**
 * 
 */
package com.omantel.restapi.config;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Dhiraj Gour
 * @date 31 Dec 2017
 *
 */
public class RestApiRestAuthInterceptor implements ClientHttpRequestInterceptor {

	//private static Logger logger = LoggerFactory.getLogger(RestAuthInterceptor.class);
	
	String user;
	String password;
	
	public RestApiRestAuthInterceptor (String user, String password) {
		this.user=user;
		this.password=password;
	}
	
	@Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
		
		//String auth = "crm" + ":" + "m3r2c1";
		String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64( 
           auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", authHeader);
        
        try {
	        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	        // get the current session without creating a new one
	       /* HttpSession httpSession = httpServletRequest.getSession(false);
	        
	        if (httpSession!=null) {
	        	if(user!=null) {
	        		headers.add("logSessionId", user.getSessionId());
	        		headers.add("logRequestId", user.getRequestId());
	        		headers.add("logUserId", user.getUserId());
	        	}
	        }*/
	        if (httpServletRequest != null) {
	        	headers.add("logSessionId", httpServletRequest.getHeader("logSessionId"));
        		headers.add("logRequestId", httpServletRequest.getHeader("logRequestId"));
        		headers.add("logUserId", httpServletRequest.getHeader("logUserId"));
	        }
        } catch (Exception e) {
        	//logger.error("Exception in RestAuthInterceptor {}", e.getMessage());
        }
        return execution.execute(request, body);
    }
}