/**
 * 
 */
package com.omantel.restapi.exception;

/**
 * @author Dhiraj Gour
 * @date 27 August 2019
 *
 */
public class ExpectationFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpectationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpectationFailureException(String message) {
		super(message);
	}

	public ExpectationFailureException(Throwable cause) {
		super(cause);
	}
}
