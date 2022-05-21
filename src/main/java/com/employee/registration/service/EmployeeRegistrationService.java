package com.employee.registration.service;

import java.util.List;

import com.employee.registration.dto.Employee;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceNotFoundException;

public interface EmployeeRegistrationService {

	Long registerEmployee(Employee employee) throws BusinessValidationException;
	
	Long updateOrRegisterEmployee(Employee employee) throws BusinessValidationException;
	
	Long updateEmployee(Employee employee) throws BusinessValidationException;
	
	Long deleteEmployee(Long employeeNum) throws BusinessValidationException;

	Employee getEmployeeByEmpNum(Long employeeNum) throws ResourceNotFoundException;
	
	List<Employee> getEmployeeByEmpName(String employeeName) throws ResourceNotFoundException;
}
