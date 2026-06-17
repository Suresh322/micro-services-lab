package com.microlab.account_service.exception;

import com.microlab.account_service.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGlobalException(
			ResourceNotFoundException ex,
			HttpServletRequest request) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				request.getRequestURI(),
				HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getMessage(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(
			Exception ex,
			HttpServletRequest request) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				request.getRequestURI(),
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
	}

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExist(
			CustomerAlreadyExistsException ex,
			HttpServletRequest request) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				request.getRequestURI(),
				HttpStatus.CONFLICT,
				ex.getMessage(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(error);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidationException(
			ValidationException ex,
			HttpServletRequest request) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				request.getRequestURI(),
				HttpStatus.BAD_REQUEST,
				ex.getMessage(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}



}
