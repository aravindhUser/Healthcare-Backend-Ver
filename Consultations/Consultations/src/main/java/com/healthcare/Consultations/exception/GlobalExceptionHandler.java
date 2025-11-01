package com.healthcare.Consultations.exception;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// Handle Resource Not Found (404)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
	//Handle Bad Request (400)
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String>handleBadRequset(BadRequestException ex) {
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	
	//Handle all other exceptions(500)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return new ResponseEntity<>("Internal Server Error:" + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
