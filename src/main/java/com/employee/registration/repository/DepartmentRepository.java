package com.employee.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.registration.entity.DepartmentModel;

/**
 * @author Avinash Gurumurthy
 * 
 * Repository class for Department Table, In addition to default methods exposes method to fetch all departments with order by department name in ascending
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, String> {

	public List<DepartmentModel> findAllByOrderByDepartmentNameAsc();
}
