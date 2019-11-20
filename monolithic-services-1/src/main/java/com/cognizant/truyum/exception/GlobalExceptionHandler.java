package com.cognizant.truyum.exception;

import java.util.ArrayList;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;



public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.debug("Start");
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Bad Request");
		List<String> errors = new ArrayList<String>();

		if (ex.getCause() instanceof InvalidFormatException) {

			final Throwable cause = ex.getCause() == null ? ex : ex.getCause();
			for (InvalidFormatException.Reference reference : ((InvalidFormatException) cause).getPath()) {
				LOGGER.debug(" fieldName......." + reference.getFieldName());

				body.put("message", "Incorrect format for field '" + reference.getFieldName() + "'");

			}

			LOGGER.debug("**************Message:" + ex.getMessage());

		}

		return new ResponseEntity<>(body, headers, status);
	}

	

}
