package com.employee.registration.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.employee.registration.dto.Employee;
import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.exception.BusinessValidationException;
import com.employee.registration.exception.ResourceConflictException;
import com.employee.registration.repository.DepartmentRepository;
import com.employee.registration.repository.EmployeeRepository;
import com.employee.registration.utils.Constants;

import lombok.extern.log4j.Log4j2;

@Service
@Validated
@Log4j2
public class EmployeeValidationServiceImpl implements EmployeeValidationService {

	@Autowired
	DepartmentRepository departmentRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;

	@Override
	public void validateEmployee(@Valid Employee empObj, String action) throws BusinessValidationException {
		if (Constants.CREATE.equalsIgnoreCase(action) || Constants.PUT.equalsIgnoreCase(action)
				|| Constants.PATCH.equalsIgnoreCase(action)) {
			performMaxLenValidation(empObj);
			validateDateFormat(empObj.getDateOfJoining());
			validateDepartmentExists(empObj.getDepartment());
		}
		if (Constants.CREATE.equalsIgnoreCase(action)) {
			checkEmployeeAlreadyExists(empObj.getEmployeeNum());
		}
		if (Constants.PATCH.equalsIgnoreCase(action)) {
			checkEmployeeExists(empObj.getEmployeeNum());
		}
	}

	/**
	 * @param empObj
	 * @throws BusinessValidationException
	 */
	private void performMaxLenValidation(Employee empObj) throws BusinessValidationException {
		if(empObj.getEmployeeNum().toString().length() > 10) {
			log.error(Constants.EMP_ID_MAX_LEN_ERR);
			throw new BusinessValidationException(Constants.EMP_ID_MAX_LEN_ERR);
		}
		if(empObj.getEmployeeName().length() > 100) {
			log.error(Constants.EMP_NAME_MAX_LEN_ERR);
			throw new BusinessValidationException(Constants.EMP_NAME_MAX_LEN_ERR);
		}
		if(empObj.getDateOfJoining().length() > 10) {
			log.error(Constants.DOJ_MAX_LEN_ERR);
			throw new BusinessValidationException(Constants.DOJ_MAX_LEN_ERR);
		}
		if(empObj.getDepartment().length() > 2) {
			log.error(Constants.DEPT_MAX_LEN_ERR);
			throw new BusinessValidationException(Constants.DEPT_MAX_LEN_ERR);
		}
		if(empObj.getSalary().toString().length() > 10) {
			log.error(Constants.SALARY_MAX_LEN_ERR);
			throw new BusinessValidationException(Constants.SALARY_MAX_LEN_ERR);
		}
	}

	/**
	 * @param department
	 * @throws BusinessValidationException
	 */
	private void validateDepartmentExists(String department) throws BusinessValidationException {
		Optional<DepartmentModel> departObj = departmentRepo.findById(department);
		if(!departObj.isPresent()) {
			log.error("Invalid Department, unable to fetch department with Id -> {} ",department);
			throw new BusinessValidationException(Constants.INVALID_DEPT);
		}
	}

	/**
	 * @param dateOfJoining
	 * @throws BusinessValidationException
	 */
	private void validateDateFormat(String dateOfJoining) throws BusinessValidationException {
		if (!isValid(dateOfJoining, Constants.DOJ_DATE_FORMAT)) {
			log.error("Invalid date format -> {} ", dateOfJoining);
			throw new BusinessValidationException(Constants.INVALID_DOJ);
		}
	}

	/**
	 * @param empId
	 * @throws ResourceConflictException
	 */
	private void checkEmployeeAlreadyExists(Long empId) throws ResourceConflictException {
		Optional<EmployeeModel> empModalObj = employeeRepo.findById(empId);
		if(empModalObj.isPresent()) {
			String error = String.format(Constants.EMP_EXISTS_ERR, empId);
			log.error(error);
			throw new ResourceConflictException(error);
		}
	}
	
	/**
	 * @param empId
	 * @throws ResourceConflictException
	 */
	private void checkEmployeeExists(Long empId) throws ResourceConflictException {
		Optional<EmployeeModel> empModalObj = employeeRepo.findById(empId);
		if(!empModalObj.isPresent()) {
			String error = String.format(Constants.EMP_NOT_EXISTS_ERR, empId);
			log.error(error);
			throw new ResourceConflictException(error);
		}
	}
	
	/**
	 * @param dateStr
	 * @return
	 */
	public boolean isValid(String dateStr, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	@Override
	public void validateEmployeeForDeletion(Long empId) throws BusinessValidationException {
		checkEmployeeExists(empId);
	}
}
