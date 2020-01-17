/**
 * 
 */
package com.omantel.restapi.bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
@Data
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class ApiError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private HttpStatus status;
	private String message;
	private String debugMessage;
	private List<String> errors;

	public ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected Internal Application error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex, List<String> errors) {
		this();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.debugMessage = ex.getLocalizedMessage();
    }
 
    public ApiError(HttpStatus status, String message, Throwable ex, String error) {
    	this();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
        this.debugMessage = ex.getLocalizedMessage();
    }
}