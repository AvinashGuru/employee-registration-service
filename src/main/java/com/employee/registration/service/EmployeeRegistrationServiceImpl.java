package com.employee.registration.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.registration.dto.Employee;
import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceNotFoundException;
import com.employee.registration.repository.DepartmentRepository;
import com.employee.registration.repository.EmployeeRepository;
import com.employee.registration.utils.Constants;

import lombok.extern.log4j.Log4j2;

/**
 * @author Avinash Gurumurthy
 * 
 * Implementation class for Employee Service
 *
 */
@Service
@Log4j2
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {
	
	@Autowired 
	EmployeeValidationService validationService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository deptRepository;

	@Override
	@Transactional
	public Long registerEmployee(Employee employee) throws BusinessValidationException {
		try {
			validationService.validateEmployee(employee, Constants.CREATE);
			EmployeeModel empEntity = new EmployeeModel(employee);
			DepartmentModel deptEntity = deptRepository.findById(employee.getDepartment()).get();
			empEntity.setDepartment(deptEntity);
			employeeRepository.save(empEntity);
			log.info("Employee Successfully registerd with num {} ",employee.getEmployeeNum());
		}catch (BusinessValidationException e) {
			throw e;
		}catch (Exception e) {
			log.error("Error while registering employee {} ",e.getMessage());
			throw e;
		}
		return employee.getEmployeeNum();
	}

	@Override
	public Employee getEmployeeByEmpNum(Long employeeNum) throws ResourceNotFoundException {
		Employee response = null;
		try {
			Optional<EmployeeModel> employeeObj = employeeRepository.findById(employeeNum);
			if(!employeeObj.isPresent()) {
				log.info("Unable to find employee with num {} ",employeeNum);
				throw new ResourceNotFoundException(String.format(Constants.EMP_FETCH_ERR_BY_NUM, employeeNum));
			}else {
				response = new Employee(employeeObj.get());
				log.info("Employee Successfully retrieved with num {} ",employeeNum);
			}
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (Exception e) {
			log.error("Error while fetching employee {} ",e.getMessage());
			throw e;
		}
		return response;
	}
	
	@Override
	public List<Employee> getEmployeeByEmpName(String employeeName) throws ResourceNotFoundException {
		List<Employee> response = null;
		try {
			List<EmployeeModel> employeeObjs = employeeRepository.findAllByEmployeeNameOrderByEmployeeNumAsc(employeeName);
			if(employeeObjs == null || employeeObjs.isEmpty()) {
				log.info("Unable to find employees with name {} ",employeeName);
				throw new ResourceNotFoundException(String.format(Constants.EMP_FETCH_ERR_BY_NAME, employeeName));
			}else {
				Function<EmployeeModel, Employee> departmentConstructor = (empModel) -> new Employee(empModel);
				response = employeeObjs.stream().map(departmentConstructor).collect(Collectors.toList());
				log.info("Employees Successfully retrieved with name {} ",employeeName);
			}
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (Exception e) {
			log.error("Error while fetching employees {} ",e.getMessage());
			throw e;
		}
		return response;
	}

	@Override
	@Transactional
	public Long updateOrRegisterEmployee(Employee employee) throws BusinessValidationException {
		try {
			validationService.validateEmployee(employee , Constants.PUT);
			EmployeeModel empEntity = new EmployeeModel(employee);
			DepartmentModel deptEntity = deptRepository.findById(employee.getDepartment()).get();
			empEntity.setDepartment(deptEntity);
			employeeRepository.save(empEntity);
			log.info("Employee details successfully saved with num {} ",employee.getEmployeeNum());
		}catch (BusinessValidationException e) {
			throw e;
		}catch (Exception e) {
			log.error("Error while doing update or register of employee {} ",e.getMessage());
			throw e;
		}
		return employee.getEmployeeNum();
	}

	@Override
	@Transactional
	public Long updateEmployee(Employee employee) throws BusinessValidationException {
		try {
			validationService.validateEmployee(employee, Constants.PATCH);
			EmployeeModel empEntity = new EmployeeModel(employee);
			DepartmentModel deptEntity = deptRepository.findById(employee.getDepartment()).get();
			empEntity.setDepartment(deptEntity);
			employeeRepository.save(empEntity);
			log.info("Employee details successfully saved with num {} ",employee.getEmployeeNum());
		}catch (BusinessValidationException e) {
			throw e;
		}catch (Exception e) {
			log.error("Error while updating employee {} ",e.getMessage());
			throw e;
		}
		return employee.getEmployeeNum();
	}

	@Override
	@Transactional
	public Long deleteEmployee(Long employeeNum) throws BusinessValidationException {
		try {
			Employee emp = new Employee();
			emp.setEmployeeNum(employeeNum);
			validationService.validateEmployeeForDeletion(employeeNum);
			employeeRepository.deleteById(employeeNum);
			log.info("Employee details successfully deleted with num {} ",employeeNum);
		}catch (Exception e) {
			log.error("Error while deleting employee {} ",e.getMessage());
			throw e;
		}
		return employeeNum;
	}

}
