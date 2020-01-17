/**
 * 
 */
package com.omantel.restapi.exception;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
public class ExternalInterfaceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExternalInterfaceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalInterfaceException(String message) {
		super(message);
	}

	public ExternalInterfaceException(Throwable cause) {
		super(cause);
	}
}
