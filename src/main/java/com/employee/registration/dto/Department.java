package com.employee.registration.dto;

import com.employee.registration.entity.DepartmentModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {
	private String departmentId;
	private String departmentName;
	
	public Department(DepartmentModel model) {
		this.departmentId = model.getDepartmentId();
		this.departmentName = model.getDepartmentName();
	}
}
