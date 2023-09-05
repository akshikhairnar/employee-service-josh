package com.employeeservice.employee.service;

import com.employeeservice.employee.entity.Employee;
import com.employeeservice.employee.entity.EmployeeProject;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Optional<Employee> getEmployee(Long id);

    Employee addEmployee(Employee e);

    Employee updateEmployee(Employee e, Long id);

    void deleteAllEmployees();

    EmployeeProject addEmployeeProject(EmployeeProject employeeProject);

}
