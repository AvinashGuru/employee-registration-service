package com.employee.registration.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.registration.entity.EmployeeModel;

/**
 * @author Avinash Gurumurthy
 * 
 * Repository class for Employee table, in addition to default methods has logic to fetch all employee by name with order by employee number in ascending
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {
	
	List<EmployeeModel> findAllByEmployeeNameOrderByEmployeeNumAsc(String employeeName);

}
