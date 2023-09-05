package com.employeeservice.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Employee_Projects")
@IdClass(EmployeeProject.class)
public class EmployeeProject {
   // @Id
    @Id
    private Long empId;

    @Id
    private Long projectId;
}
