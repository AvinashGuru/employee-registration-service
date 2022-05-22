package com.employee.registration.empregservice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.employee.registration.dto.Employee;
import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.repository.DepartmentRepository;
import com.employee.registration.repository.EmployeeRepository;
import com.employee.registration.service.EmployeeValidationService;
import com.employee.registration.service.EmployeeValidationServiceImpl;
import com.employee.registration.utils.Constants;

/**
 * @author Avinash Gurumurthy
 *
 */
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeValidationServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private DepartmentRepository deptRepository;
	
	private static DepartmentModel dept;

	private static EmployeeModel testUser1;
	
	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {
		@Bean
		public EmployeeValidationService employeeValidationService() {
			return new EmployeeValidationServiceImpl();
		}
	}
	
	@Autowired
	private EmployeeValidationService validationService;

	@BeforeAll
	public static void setUp() {
		dept = new DepartmentModel();
		dept.setDepartmentId("HR");
		dept.setDepartmentName("Human Resource");
		
		testUser1 = new EmployeeModel();
		testUser1.setEmployeeNum(101L);
		testUser1.setEmployeeName("Jhon");
		testUser1.setDepartment(dept);
		testUser1.setDateOfJoining("11/11/2001");
		testUser1.setSalary(11111L);
	}
	
	@BeforeEach
	public void beforeEachSetup() {
		Mockito.when(employeeRepository.findById(101L)).thenReturn(Optional.of(testUser1));
		Mockito.when(deptRepository.findById("HR")).thenReturn(Optional.of(dept));
	}
	
	@Test
	public void validateInvalidDepartmentTest() {
		Employee emp = new Employee(testUser1);
		emp.setDepartment("TT");
		assertThatThrownBy(() -> validationService.validateEmployee(emp, Constants.CREATE));
	}
	
	@Test
	public void validateInvalidEmployeeTest() {
		Employee emp = new Employee(testUser1);
		emp.setEmployeeNum(110L);
		assertThatThrownBy(() -> validationService.validateEmployee(emp, Constants.PATCH));
	}
	
}
