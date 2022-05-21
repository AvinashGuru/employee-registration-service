package com.employee.registration.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.registration.entity.EmployeeModel;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {
	
	List<EmployeeModel> findAllByEmployeeNameOrderByEmployeeNumAsc(String employeeName);

}
