package com.employee.registration.exception;

public class ResourceConflictException extends BusinessValidationException {

	private static final long serialVersionUID = 1L;

	public ResourceConflictException(String message) {
		super(message);
	}
	
}
