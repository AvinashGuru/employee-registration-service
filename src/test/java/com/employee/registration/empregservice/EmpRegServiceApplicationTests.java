package com.employee.registration.empregservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.employee.registration.EmpRegServiceApplication;
import com.employee.registration.dto.Employee;
import com.employee.registration.entity.DepartmentModel;
import com.employee.registration.entity.EmployeeModel;
import com.employee.registration.repository.EmployeeRepository;
import com.employee.registration.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Avinash Gurumurthy
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EmpRegServiceApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
class EmpRegServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EmployeeRepository repository;
	
	private static Employee jhon = new Employee();
	
	private static Employee jenny = new Employee();
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@BeforeAll
	public static void prepareTest() {
		jhon.setEmployeeNum(101L);
		jhon.setEmployeeName("Jhon");
		jhon.setDepartment("HR");
		jhon.setDateOfJoining("12/12/2002");
		jhon.setSalary(10000L);
		
		jenny.setEmployeeNum(102L);
		jenny.setEmployeeName("Jenny");
		jenny.setDepartment("HR");
		jenny.setDateOfJoining("11/11/2001");
		jenny.setSalary(11000L);
	}
	
	@Test
	@Order(1) 
	public void employeeRegistrationTest() throws IOException, Exception {
		mvc.perform(post("/registerEmployee").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jhon)));
		List<EmployeeModel> found = repository.findAll();
		assertThat(found).extracting( e -> e.getEmployeeName()).anyMatch( name -> name.equalsIgnoreCase("Jhon"));
	}
	
	@Test
	@Order(2) 
	public void updateOrRegisterEmployeeTest() throws IOException, Exception {
		mvc.perform(put("/updateOrRegisterEmployee/102").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jenny)));
		Optional<EmployeeModel> found = repository.findById(102L);
		assertThat(found.isPresent());
		assertThat(found.get().getEmployeeName().equalsIgnoreCase("Jenny"));
	}
	
	@Test
	@Order(3) 
	public void updateEmployeeTest() throws IOException, Exception {
		saveEmployee();
		jhon.setDateOfJoining("01/01/2001");
		mvc.perform(patch("/updateEmployee/101").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jhon)));
		Optional<EmployeeModel> found = repository.findById(101L);
		assertThat(found.isPresent());
		assertThat(found.get().getDateOfJoining().equalsIgnoreCase("01/01/2001"));
	}
	
	@Test
	@Order(4) 
	public void deleteEmployeeTest() throws IOException, Exception {
		saveEmployee();
		mvc.perform(delete("/deleteEmployee/101"));
		Optional<EmployeeModel> found = repository.findById(101L);
		assertThat(!found.isPresent());
	}
	
	@Test
	@Order(5) 
	public void getEmployeeByNumberTest() throws IOException, Exception {
		saveEmployee();
		mvc.perform(get("/getEmployee/101").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@Order(6) 
	public void getEmployeeNotFoundTest() throws IOException, Exception {
		mvc.perform(get("/getEmployee/105").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Order(7)
	public void getEmployeeRegisterDojError() throws IOException, Exception {
		jhon.setDateOfJoining("12-12-2022");
		mvc.perform(post("/registerEmployee").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jhon))).andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString(Constants.INVALID_DOJ)));
	}
	
	@Test
	@Order(8)
	public void getEmployeeRegisterMandatoryError() throws IOException, Exception {
		mvc.perform(post("/registerEmployee").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new Employee()))).andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("mandatory")));
	}
	
	@Test
	@Order(9)
	public void getEmployeesByNameTest() throws IOException, Exception {
		saveEmployee();
		mvc.perform(get("/getEmployee?employeeName=Jhon").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@Order(10)
	public void getAllDepartmentsTest() throws IOException, Exception {
		mvc.perform(get("/getAllDepartments").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	
	private void saveEmployee() {
		DepartmentModel dept = new DepartmentModel();
		dept.setDepartmentId("HR");
		dept.setDepartmentName("Human Resource");
		
		EmployeeModel testUser1 = new EmployeeModel();
		testUser1.setEmployeeNum(101L);
		testUser1.setEmployeeName("Jhon");
		testUser1.setDepartment(dept);
		testUser1.setDateOfJoining("11/11/2001");
		testUser1.setSalary(11111L);
		
		EmployeeModel testUser2 = new EmployeeModel();
		testUser2.setEmployeeNum(102L);
		testUser2.setEmployeeName("Jhon");
		testUser2.setDepartment(dept);
		testUser2.setDateOfJoining("12/12/2002");
		testUser2.setSalary(10000L);
		
		repository.saveAndFlush(testUser1);
		repository.saveAndFlush(testUser2);
    }
}
