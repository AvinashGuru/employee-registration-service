package com.employee.registration.exception;

public class ResourceNotFoundException extends BusinessValidationException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
