package com.employeeservice.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Table(name = "Employee")
@Entity
public class Employee {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private Long departmentId;

//    @JsonIgnore
//    private Long projectId;


}
