package com.employee.registration.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.registration.dto.ResponseDTO;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceConflictException;
import com.employee.registration.exception.ResourceNotFoundException;
import com.employee.registration.utils.Constants;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ExceptionHandlers  {
	
	@ExceptionHandler(value = { BusinessValidationException.class })
	protected ResponseEntity<ResponseDTO> handleBusinessValidationException(BusinessValidationException ex) {
		String bodyOfResponse = ex.getMessage();
		return new ResponseEntity<>(new ResponseDTO(Constants.ERROR, bodyOfResponse), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
		String bodyOfResponse = ex.getMessage();
		return new ResponseEntity<>(new ResponseDTO(Constants.ERROR, bodyOfResponse), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { ResourceConflictException.class })
	protected ResponseEntity<ResponseDTO> handleConflict(ResourceConflictException ex) {
		String bodyOfResponse = ex.getMessage();
		return new ResponseEntity<>(new ResponseDTO(Constants.ERROR, bodyOfResponse), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
		String bodyOfResponse = ex.getMessage();
		log.error("Constraint validation error -> {}",bodyOfResponse);
		List<String> splitedVals = Arrays.asList(bodyOfResponse.split(","));
		String newRespBody = splitedVals.stream().map( resp -> resp.split(": ")[1]).collect(Collectors.joining(" , "));
		return new ResponseEntity<>(new ResponseDTO(Constants.ERROR, newRespBody), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
		String bodyOfResponse = ex.getMessage();
		return new ResponseEntity<>(new ResponseDTO(Constants.ERROR, bodyOfResponse), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
