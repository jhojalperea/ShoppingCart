package com.cart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class HandlerValidationController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> validationMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	       
	    FieldError fieldError = ex.getBindingResult().getFieldError();	    
	    Map<String, Object> response = new HashMap<String, Object>();	    
	    response.put("status", HttpStatus.BAD_REQUEST.value());
	    response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
	    response.put( "message", new StringBuilder(fieldError.getField() )
	    						.append(": ")
	    						.append(fieldError.getDefaultMessage())
	    						.toString() );	    	    
	    return response;
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> validationConstraintViolationException(ConstraintViolationException ex) {
	       
	    Map<String, Object> response = new HashMap<String, Object>();	    
	    response.put("status", HttpStatus.BAD_REQUEST.value());
	    response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
	    response.put( "message", ex.getMessage() );	    	    
	    return response;
	}
	
}
