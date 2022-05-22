package com.employee.registration.service;

import javax.validation.Valid;

import com.employee.registration.dto.Employee;
import com.employee.registration.exception.BusinessValidationException;

/**
 * @author Avinash Gurumurthy
 *
 */
public interface EmployeeValidationService {
 
	/**
	 * Service method to add all necessary validation for Employee DTO like 
	 * 1) Mandatory fields
	 * 2) Valid Department Check
	 * 3) Max Length of a field validation
	 * 4) Valid Employee check in case action is Patch
	 * 5) Valid Date format for date of joining field check
	 * @param empObj
	 * @param action
	 * @throws BusinessValidationException
	 */
	void validateEmployee(@Valid Employee empObj, String action) throws BusinessValidationException;
	
	/**
	 *  Validation service method to check if employee exists before deletion 
	 * @param empId
	 * @throws BusinessValidationException
	 */
	void validateEmployeeForDeletion(Long empId) throws BusinessValidationException;
}
