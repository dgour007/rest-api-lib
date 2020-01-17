/**
 * 
 */
package com.omantel.restapi.exception;

/**
 * @author Dhiraj Gour
 * @date 22 Aug 2019
 *
 */
public class DataNotFoundError extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DataNotFoundError(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundError(String message) {
		super(message);
	}

	public DataNotFoundError(Throwable cause) {
		super(cause);
	}
}
