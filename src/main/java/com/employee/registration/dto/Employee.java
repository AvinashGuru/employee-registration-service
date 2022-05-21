package com.employee.registration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.utils.Constants;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {
	@NotNull(message = Constants.EMP_ID_REQ_ERR)
	private Long employeeNum;
	
	@NotBlank(message = Constants.EMP_NAME_REQ_ERR)
	private String employeeName;
	
	@NotBlank(message = Constants.DOJ_REQ_ERR)
	private String dateOfJoining;
	
	@NotBlank(message = Constants.DEPT_REQ_ERR)
	private String department;
	
	@NotNull(message = Constants.SALARY_REQ_ERR)
	private Long salary;
	
	public Employee (EmployeeModel model) {
		this.employeeNum = model.getEmployeeNum();
		this.employeeName = model.getEmployeeName();
		this.dateOfJoining = model.getDateOfJoining();
		this.salary = model.getSalary();
		this.department = model.getDepartment().getDepartmentId();
	}
}
