package com.employeeservice.employee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private Long projectId;

    private String projectName;

    private LocalDate projectAllocationDate;
}
