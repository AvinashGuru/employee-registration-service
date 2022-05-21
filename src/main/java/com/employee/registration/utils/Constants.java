package com.employee.registration.utils;

public interface Constants {
	
	String SUCCESS = "Success";
	String ERROR = "Error";
	
	String CREATE = "CREATE";
	String PUT = "PUT";
	String PATCH = "PATCH";
	String DELETE = "DELETE";
	
	String DOJ_DATE_FORMAT = "dd/MM/yyyy";
	
	String EMP_ID_REQ_ERR = "Employee Number is a mandatory field";
	String EMP_ID_MAX_LEN_ERR = "Employee Number cannot have more than 10 digits";
	
	String EMP_NAME_REQ_ERR = "Employee Name is a mandatory field";
	String EMP_NAME_MAX_LEN_ERR = "Employee Name cannot have more than 100 characters";

	String DOJ_REQ_ERR = "Date Of Joining is a mandatory field";
	String DOJ_MAX_LEN_ERR = "Date Of Joining cannot have more than 10 characters";
	
	String DEPT_REQ_ERR = "Department is a mandatory field";
	String DEPT_MAX_LEN_ERR = "Department cannot have more than 2 characters";

	String SALARY_REQ_ERR = "Salary is a mandatory field";
	String SALARY_MAX_LEN_ERR = "Salary cannot have more than 10 digits";
	
	String INVALID_DOJ = "Date Of Joining: Invalid Date Format , required DD/MM/YYYY ";
	String EMP_EXISTS_ERR = "Employee with Employee Number '%d' already exists";
	String EMP_NOT_EXISTS_ERR = "Employee with Employee Number '%d' not found";
	
	String INVALID_DEPT = "Invalid Department";
	
	String EMP_FETCH_ERR_BY_NUM = "Unable to fetch Employee Data with Employee Number '%d'";
	
	String EMP_FETCH_ERR_BY_NAME = "Unable to fetch Employees with Employee Name '%s'";
	
	String EMP_CREATE_MSG  = "Successfully registered employee with employee number '%d'";
	String EMP_UPDATE_MSG  = "Successfully saved employee details with employee number '%d'";
	String EMP_DELETE_MSG  = "Successfully deleted employee with employee number '%d'";
}
