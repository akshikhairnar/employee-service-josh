package com.employeeservice.employee.repository;

import com.employeeservice.employee.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeProjectRepository  extends JpaRepository<EmployeeProject, Long> {

//    @Query("SELECT e FROM EmployeeProject e WHERE e.projectId = null")
//    List<EmployeeProject> employeeWithNoProject();
}
