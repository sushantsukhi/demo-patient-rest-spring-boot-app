package com.example.patient.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(ServletRequestBindingException.class)
	public final ResponseEntity<ErrorResponse> handleHeaderException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Bad Request", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> exceptionToDoHandler(Exception ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NumberFormatException.class)
	public final ResponseEntity<ErrorResponse> handleNFException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Number Format Exception", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
