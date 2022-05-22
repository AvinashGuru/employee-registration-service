package com.employee.registration.service;

import java.util.List;

import com.employee.registration.dto.Employee;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceNotFoundException;

/**
 * @author Avinash Gurumurthy
 *
 */
public interface EmployeeRegistrationService {

	/**
	 * Service method to register new Employee, this service method includes all necessary validations before saving details to table
	 * @param employee
	 * @return
	 * @throws BusinessValidationException
	 */
	Long registerEmployee(Employee employee) throws BusinessValidationException;
	
	/**
	 * Service method to register new Employee or update existing employee, this service method includes all necessary validations before saving details to table
	 * @param employee
	 * @return
	 * @throws BusinessValidationException
	 */
	Long updateOrRegisterEmployee(Employee employee) throws BusinessValidationException;
	
	/**
	 * Service method to update existing employee, this service method includes all necessary validations before saving details to table 
	 * @param employee
	 * @return
	 * @throws BusinessValidationException
	 */
	Long updateEmployee(Employee employee) throws BusinessValidationException;
	
	/**
	 * Service method to delete existing employee, this service method includes all necessary validations before saving details to table
	 * @param employeeNum
	 * @return
	 * @throws BusinessValidationException
	 */
	Long deleteEmployee(Long employeeNum) throws BusinessValidationException;

	/**
	 * Service method to get employee using employee number
	 * @param employeeNum
	 * @return
	 * @throws ResourceNotFoundException
	 */
	Employee getEmployeeByEmpNum(Long employeeNum) throws ResourceNotFoundException;
	
	/**
	 * Service method to get employee using employee name
	 * @param employeeName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	List<Employee> getEmployeeByEmpName(String employeeName) throws ResourceNotFoundException;
}
