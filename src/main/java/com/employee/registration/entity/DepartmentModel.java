package com.employee.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Avinash Gurumurthy
 * 
 * Entity model class for Department Table
 *
 */
@Data
@Entity
@Table(name = "DEPARTMENT")
@NoArgsConstructor
public class DepartmentModel {
	@Id
	@Column(name = "DEPT_ID", length = 2)
	private String departmentId;
	@Column(name = "DEPT_NAME", length = 100)
	private String departmentName;
}
