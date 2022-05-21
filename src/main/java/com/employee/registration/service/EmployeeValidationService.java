package com.employee.registration.service;

import javax.validation.Valid;

import com.employee.registration.dto.Employee;
import com.employee.registration.exception.BusinessValidationException;

public interface EmployeeValidationService {
 
	void validateEmployee(@Valid Employee empObj, String action) throws BusinessValidationException;
	
	void validateEmployeeForDeletion(Long empId) throws BusinessValidationException;
}
