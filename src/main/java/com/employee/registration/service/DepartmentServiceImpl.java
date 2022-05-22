package com.employee.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.registration.dto.Department;
import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.repository.DepartmentRepository;

import lombok.extern.log4j.Log4j2;


/**
 * @author Avinash Gurumurthy
 * 
 * Implementation class for Department Service
 *
 */
@Service
@Log4j2
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	DepartmentRepository departmentRepo;

	@Override
	public List<Department> getAllDepartments() {
		List<Department> response = null;
		try {
			List<DepartmentModel> allDepts = departmentRepo.findAllByOrderByDepartmentNameAsc();
			Function<DepartmentModel, Department> departmentConstructor = (deptModel) -> new Department(deptModel);
			if(allDepts != null && !allDepts.isEmpty()) {
				response = allDepts.stream().map(departmentConstructor).collect(Collectors.toList());
			}
		}catch (Exception e) {
			log.error("Error while retrieving departments {} ",e.getMessage());
			response = new ArrayList<>(1);
			throw e;
		}
		return response;
	}

}
