package com.employee.registration.exception;

import lombok.Getter;

/**
 * @author Avinash Gurumurthy
 *
 */
@Getter
public class BusinessValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public BusinessValidationException(String message) {
		super(message);
		this.message = message;
	}
	
}
