package com.employee.registration.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.registration.dto.Department;
import com.employee.registration.dto.Employee;
import com.employee.registration.dto.ResponseDTO;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceNotFoundException;
import com.employee.registration.service.DepartmentService;
import com.employee.registration.service.EmployeeRegistrationService;
import com.employee.registration.utils.Constants;

/**
 * @author Avinash Gurumurthy
 * 
 * Controller class for employee registration services , exposes restful end points for below
 * 1) Register New employee
 * 2) Patch existing employee details
 * 3) Delete existing employee
 * 4) Retrieve employee details using employee number
 * 5) Retrieve employee details using employee name
 *
 */
@RestController
public class EmployeeRegistrationController {
	
	@Autowired
	EmployeeRegistrationService empRegService;
	
	@Autowired
	DepartmentService deptService;
	
	/**
	 * Rest service exposed to register a new employee 
	 * @param employee
	 * @return
	 * @throws BusinessValidationException
	 */
	@PostMapping("/registerEmployee")
	public ResponseEntity<ResponseDTO> registerEmployee(@RequestBody Employee employee) throws BusinessValidationException{
		Long empNum = empRegService.registerEmployee(employee);
		ResponseDTO resp = new ResponseDTO(Constants.SUCCESS, String.format(Constants.EMP_CREATE_MSG, empNum));
		return new ResponseEntity<ResponseDTO>(resp, HttpStatus.CREATED);
	}
	
	/**
	 * Rest service exposed to register a new employee  or update existing employee details
	 * @param employee
	 * @param employeeNum
	 * @return
	 * @throws BusinessValidationException
	 */
	@PutMapping("/updateOrRegisterEmployee/{employeeNum}")
	public ResponseEntity<ResponseDTO> updateOrRegisterEmployee(@RequestBody Employee employee, @PathVariable Long employeeNum) throws BusinessValidationException{
		employee.setEmployeeNum(employeeNum);
		Long empNum = empRegService.updateOrRegisterEmployee(employee);
		ResponseDTO resp = new ResponseDTO(Constants.SUCCESS, String.format(Constants.EMP_UPDATE_MSG, empNum));
		return new ResponseEntity<ResponseDTO>(resp, HttpStatus.OK);
	}

	/**
	 * Rest service exposed to update existing employee details
	 * @param employee
	 * @param employeeNum
	 * @return
	 * @throws BusinessValidationException
	 */
	@PatchMapping("/updateEmployee/{employeeNum}")
	public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody Employee employee, @PathVariable Long employeeNum) throws BusinessValidationException{
		employee.setEmployeeNum(employeeNum);
		Long empNum = empRegService.updateEmployee(employee);
		ResponseDTO resp = new ResponseDTO(Constants.SUCCESS, String.format(Constants.EMP_UPDATE_MSG, empNum));
		return new ResponseEntity<ResponseDTO>(resp, HttpStatus.OK);
	}
	
	/**
	 * Rest service exposed to delete existing employee 
	 * @param employeeNum
	 * @return
	 * @throws BusinessValidationException
	 */
	@DeleteMapping("/deleteEmployee/{employeeNum}")
	public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable Long employeeNum) throws BusinessValidationException{
		Long empNum = empRegService.deleteEmployee(employeeNum);
		ResponseDTO resp = new ResponseDTO(Constants.SUCCESS, String.format(Constants.EMP_DELETE_MSG, empNum));
		return new ResponseEntity<ResponseDTO>(resp, HttpStatus.OK);
	}
	
	/**
	 * Rest service exposed to get employee details by passing employee number as input
	 * @param employeeNum
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/getEmployee/{employeeNum}")
	public ResponseEntity<Employee> getEmployeeByNumber(@PathVariable Long employeeNum) throws ResourceNotFoundException{
		Employee empObj = empRegService.getEmployeeByEmpNum(employeeNum);
		return new ResponseEntity<Employee>(empObj, HttpStatus.OK);
	}
	
	/**
	 * Rest service exposed to get employee details by passing employee name as input
	 * @param employeeName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/getEmployee")
	public ResponseEntity<List<Employee>> getEmployeesByName(@PathParam("employeeName") String employeeName) throws ResourceNotFoundException{
		List<Employee> empObj = empRegService.getEmployeeByEmpName(employeeName);
		return new ResponseEntity<List<Employee>>(empObj, HttpStatus.OK);
	}
	
	/**
	 * Rest service exposed to get all available departments from department master
	 * @return
	 */
	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<Department>> getAllDepartments(){
		List<Department> departments = deptService.getAllDepartments();
		return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
	}

}
