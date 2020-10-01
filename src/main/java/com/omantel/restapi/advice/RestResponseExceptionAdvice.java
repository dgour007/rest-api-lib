package com.omantel.restapi.advice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.omantel.restapi.bean.ApiError;
import com.omantel.restapi.config.Messages;
import com.omantel.restapi.exception.ApiException;
import com.omantel.restapi.exception.DataNotFoundError;
import com.omantel.restapi.exception.ExpectationFailureException;
import com.omantel.restapi.exception.ExternalInterfaceException;
import com.omantel.restapi.exception.InvalidInputException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
@RestControllerAdvice
@Slf4j
public class RestResponseExceptionAdvice extends ResponseEntityExceptionHandler {

	@Autowired
    Messages messages;
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<String>();
		for (FieldError error : exc.getBindingResult().getFieldErrors()) {
			log.info("Validation Failed: Field {} Message {}", error.getField(), error.getDefaultMessage());
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : exc.getBindingResult().getGlobalErrors()) {
			log.info("Validation Failed: Object {} Message {}", error.getObjectName(), error.getDefaultMessage());
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Input arguments validation error", exc, errors);
		return handleExceptionInternal(exc, apiError, headers, apiError.getStatus(), request);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handleException(SQLException e, HandlerMethod handlerMethod) {
		
		for (StackTraceElement ste : e.getStackTrace()) {
			if (ste.getClassName() != null && ste.getClassName().indexOf("om.omantel.") != -1) {
				log.error("SQLException CONTROLLER {} METHOD {} SOURCE_CLASS {} SOURCE_METHOD {} LINE NUMBER {} EXCEPTION {}", 
						handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName(), 
						ste.getClassName(), ste.getMethodName(), ste.getLineNumber(), e.toString());
				break;
			}
		}
		return buildResponseEntity(new ApiError(HttpStatus.BAD_GATEWAY, "SQL Exception occurred", e));
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleException(InvalidInputException exc) {

		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, exc.getMessage(), exc));
	}

	@ExceptionHandler(ExternalInterfaceException.class)
	public ResponseEntity<Object> handleException(ExternalInterfaceException e, HandlerMethod handlerMethod) {
		
		//Object controllerClass = handlerMethod.getMethod().getDeclaringClass();
		//log.info("ExternalInterfaceException in CONTROLLER {} MESSAGE {}", 
		//		controllerClass, e.getMessage());
		
		for (StackTraceElement ste : e.getStackTrace()) {
			if (ste.getClassName() != null && ste.getClassName().indexOf("om.omantel.") != -1) {
				log.info("ExternalInterfaceException CONTROLLER {} METHOD {} SOURCE_CLASS {} SOURCE_METHOD {} LINE NUMBER {} MESSAGE {}", 
						handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName(), 
						ste.getClassName(), ste.getMethodName(), ste.getLineNumber(), e.getMessage());
				break;
			}
		}
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, e.getMessage()));
	}
	
	@ExceptionHandler(DataNotFoundError.class)
	public ResponseEntity<Object> handleException(DataNotFoundError e/*, HandlerMethod handlerMethod*/) {
		
		/*for (StackTraceElement ste : e.getStackTrace()) {
			if (ste.getClassName() != null && ste.getClassName().indexOf("om.omantel.custapi") != -1) {
				log.info("DataNotFoundError CONTROLLER {} METHOD {} SOURCE_CLASS {} SOURCE_METHOD {} LINE NUMBER {} MESSAGE {}", 
						handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName(), 
						ste.getClassName(), ste.getMethodName(), ste.getLineNumber(), e.getMessage());
				break;
			}
		}*/
		log.info("DataNotFoundError response {}", messages.get(e.getMessage()));
		
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, messages.get(e.getMessage())));
	}
	
	@ExceptionHandler(ExpectationFailureException.class)
	public ResponseEntity<Object> handleException(ExpectationFailureException e) {
		
		log.info("ExpectationFailureException response {}", messages.get(e.getMessage()));
		
		return buildResponseEntity(new ApiError(HttpStatus.EXPECTATION_FAILED, messages.get(e.getMessage())));
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Object> handleException(ApiException e) {
		
		log.info("ApiException status {}, message {}", e.getApiError().getStatus(), e.getApiError().getMessage());
		
		return buildResponseEntity(e.getApiError());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e, HandlerMethod handlerMethod) {
		
		for (StackTraceElement ste : e.getStackTrace()) {
			if (ste.getClassName() != null && ste.getClassName().indexOf("om.omantel.") != -1) {
				log.error("Exception CONTROLLER {} METHOD {} SOURCE_CLASS {} SOURCE_METHOD {} LINE NUMBER {} EXCEPTION {}", 
						handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName(), 
						ste.getClassName(), ste.getMethodName(), ste.getLineNumber(), e.toString());
				break;
			}
		}
		
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e));
	}
}
