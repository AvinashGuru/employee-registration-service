package com.employee.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.employee.registration.dto.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Avinash Gurumurthy
 * 
 * Entity model class for Employee Table
 *
 */
@Data
@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
public class EmployeeModel {
	@Id
	@Column(name = "EMPLOYEE_ID", length = 10)
	private Long employeeNum;
	@Column(name = "EMPLOYEE_NAME" , length = 100)
	private String employeeName;
	@Column(name = "DATE_OF_JOINING", length = 10)
	private String dateOfJoining;
	@ManyToOne
	@JoinColumn(name = "DEPT_ID")
	private DepartmentModel department;
	@Column(name = "SALARY", length = 10)
	private Long salary;
	
	public EmployeeModel (Employee model) {
		this.employeeNum = model.getEmployeeNum();
		this.employeeName = model.getEmployeeName();
		this.dateOfJoining = model.getDateOfJoining();
		this.salary = model.getSalary();
	}
}
