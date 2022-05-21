package com.employee.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.registration.entity.DepartmentModel;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, String> {

	public List<DepartmentModel> findAllByOrderByDepartmentNameAsc();
}
