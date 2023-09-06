package com.employeeservice.employee.controller;

import com.employeeservice.employee.dto.*;
import com.employeeservice.employee.entity.Employee;
import com.employeeservice.employee.entity.EmployeeProject;
import com.employeeservice.employee.externalservices.EmployeeDaprComponent;
import com.employeeservice.employee.mapper.EmployeeMapper;
import com.employeeservice.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeDaprController {

    @Autowired
    private EmployeeDaprComponent employeeDapr;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/dapr/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        Employee employeeToAdd = EmployeeMapper.employeeMapper(employeeDTO);
        Employee employee = employeeService.addEmployee(employeeToAdd);
        EmployeeDTO employeeDTOAdded = EmployeeMapper.employeeDTOMapper(employee);
        //if (employeeDTOAdded !=null){
            employeeDapr.notifyServices("New Employee Added");
        //}
        return new ResponseEntity<>(employeeDTOAdded, HttpStatus.OK);
    }


    @PostMapping("/dapr/add-employee-department")
    public ResponseEntity<?> addEmployeeToDepartment(@RequestBody EmployeeDepartmetData employeeDepartmetData) {

        Mono<DepartmentDTO> department = employeeDapr.invokeGetDepartmentById(employeeDepartmetData.getDepartmentId());

        if (department.blockOptional().isEmpty()) {
            return new ResponseEntity<>("Department not exist with the Id : " + employeeDepartmetData.getDepartmentId(), HttpStatus.NO_CONTENT);
        }

        Optional<Employee> employee = employeeService.getEmployee(employeeDepartmetData.getEmployeeId());

        if (employee.isEmpty()) {
            return new ResponseEntity<>("Employee not exist with the Id : " + employeeDepartmetData.getEmployeeId(), HttpStatus.NO_CONTENT);
        }
        employee.get().setDepartmentId(department.block().getDepartmentId());
        Employee updatedEmp = employeeService.updateEmployee(employee.get(), employeeDepartmetData.getEmployeeId());

        return new ResponseEntity<>("Employee id : " + employeeDepartmetData.getEmployeeId() + " get added with Department id : " + employeeDepartmetData.getDepartmentId(), HttpStatus.OK);
    }

    @PostMapping("/dapr/add-employee-project")
    public ResponseEntity<?> addEmployeeToProject(@RequestBody EmployeeProjectDTO employeeProjectDTO) {

        Mono<ProjectDTO> projectDTO = employeeDapr.invokeGetProjectById(employeeProjectDTO.getProjectId());

        if (projectDTO.blockOptional().isEmpty()) {
            return new ResponseEntity<>("Project not exist with the Id : " + employeeProjectDTO.getProjectId(), HttpStatus.NO_CONTENT);
        }

        Optional<Employee> employee = employeeService.getEmployee(employeeProjectDTO.getEmpId());

        if (employee.isEmpty()) {
            return new ResponseEntity<>("Employee not exist with the Id : " + employeeProjectDTO.getEmpId(), HttpStatus.NO_CONTENT);
        }
        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setProjectId(projectDTO.block().getProjectId());
        employeeProject.setEmpId(employee.get().getEmpId());
        return new ResponseEntity<EmployeeProject>(employeeService.addEmployeeProject(employeeProject), HttpStatus.OK);
    }
}
