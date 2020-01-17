/**
 * 
 */
package com.omantel.restapi.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dhiraj Gour
 * @date 26 August 2019
 *
 */
public interface ApiLoggingService {

	void insertApiLog (HttpServletRequest request, String message);
}
