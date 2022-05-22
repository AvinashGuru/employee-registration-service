package com.employee.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Avinash Gurumurthy
 * Wrapper DTO to respond to UI
 *
 */
@Data
@AllArgsConstructor
public class ResponseDTO {
	
	private String status;
	private String message;

}
