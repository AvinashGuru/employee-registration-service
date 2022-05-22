package com.employee.registration.empregservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.repository.EmployeeRepository;

/**
 * @author Avinash Gurumurthy
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static EmployeeModel jhon = new EmployeeModel();
	
	
	@BeforeAll
	public static void prepareTest() {
		DepartmentModel dept = new DepartmentModel();
		dept.setDepartmentId("HR");
		dept.setDepartmentName("Human Resource");
		
		jhon.setEmployeeNum(101L);
		jhon.setEmployeeName("Jhon");
		jhon.setDepartment(dept);
		jhon.setDateOfJoining("12/12/2002");
		jhon.setSalary(10000L);
	}
	
	@BeforeEach
	public void persistEmployeeObj(){
		entityManager.persistAndFlush(jhon);
	}

	@Test
	@Order(1) 
	public void saveEmployee() {
		Optional<EmployeeModel> found = employeeRepository.findById(101L);
		assertThat(found.isPresent());
		assertThat(found.get().getEmployeeName()).isEqualTo("Jhon");
	}
	
	@Test
	@Order(2) 
	public void whenFindByName_thenReturnEmployee() {
		List<EmployeeModel> found = employeeRepository.findAllByEmployeeNameOrderByEmployeeNumAsc("Jhon");
		assertThat(found.size() == 1);
	}
	
	@Test
	@Order(3) 
	public void updateEmployee() {
		jhon.setDateOfJoining("01/01/2022");
		entityManager.persistAndFlush(jhon);
		Optional<EmployeeModel> found = employeeRepository.findById(101L);
		assertThat(found.isPresent());
		assertThat(found.get().getDateOfJoining()).isEqualTo("01/01/2022");
	}
	
	@Test
	@Order(4) 
	public void deleteEmployee() {
		entityManager.remove(jhon);
		entityManager.flush();
		Optional<EmployeeModel> found = employeeRepository.findById(101L);
		assertThat(!found.isPresent());
	}

}
