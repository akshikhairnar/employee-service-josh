package com.employeeservice.employee.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class EmployeeDTO {

    private Long empId;

    private String firstName;

    private String lastName;

    private Long departmentId;

    private Long projectId;

}
