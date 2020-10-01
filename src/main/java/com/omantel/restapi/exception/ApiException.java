/**
 * 
 */
package com.omantel.restapi.exception;

import com.omantel.restapi.bean.ApiError;

/**
 * @author Dhiraj Gour
 * @date 29 Sep 2020
 *
 */
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	ApiError apiError = null;
	
	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}
	
	public ApiException(ApiError e) {
		super(e.getMessage());
		setApiError(e);
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}
	
	

}
