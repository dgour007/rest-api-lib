/**
 * 
 */
package com.omantel.restapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
//import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author Dhiraj Gour
 * @date 29 Jan 2018
 *
 */
@Component
public class MDCFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			HttpServletRequest req = (HttpServletRequest)request;
			if (req!=null) {
				
				String logSessionId=req.getHeader("logSessionId");
		    	String logRequestId=req.getHeader("logRequestId");
		    	String logUserId=req.getHeader("logUserId");
		    	
		    	//System.out.println("logUserId "+logUserId+" logSessionId "+logSessionId+" logRequestId "+logRequestId);
		    	
		    	//MDC.put("userid", logUserId);
				//MDC.put("sessionid", logSessionId);
				//MDC.put("requestid", logRequestId);
		    	
				ThreadContext.put("userid", logUserId);
				ThreadContext.put("sessionid", logSessionId);
				ThreadContext.put("requestid", logRequestId);
				
			}
			chain.doFilter(request, response);
		} finally {
			/*MDC.remove("sessionid");
			MDC.remove("userid");
			MDC.remove("requestid");*/
			
			ThreadContext.remove("sessionid");
			ThreadContext.remove("userid");
			ThreadContext.remove("requestid");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void destroy() {}
}
