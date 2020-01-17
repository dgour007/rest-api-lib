/**
 * 
 */
package com.omantel.restapi.exception;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}
}
