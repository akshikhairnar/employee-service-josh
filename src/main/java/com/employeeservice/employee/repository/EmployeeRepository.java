package com.employeeservice.employee.repository;

import com.employeeservice.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee save(Employee employee);

    //e.empId,e.firstName,e.lastName
    @Query("SELECT e FROM  Employee e, EmployeeProject ep WHERE e.empId != ep.empId")
    List<Employee> employeeWithNoProject();


}
