package com.employee.registration.service;

import java.util.List;

import com.employee.registration.dto.Department;

/**
 * @author Avinash Gurumurthy
 *
 */
public interface DepartmentService {

	/**
	 * Service  method to fetch all available departments from Department Master
	 * @return
	 */
	List<Department> getAllDepartments();
}
