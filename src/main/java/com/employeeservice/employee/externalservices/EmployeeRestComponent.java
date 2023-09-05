package com.employeeservice.employee.externalservices;

import com.employeeservice.employee.dto.DepartmentDTO;
import com.employeeservice.employee.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@Component
public class EmployeeRestComponent {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${http.department.base.url}")
    private String httpDepartmentRequest;

    @Value("${http.project.base.url}")
    private String httpProjectRequest;

    public Optional<DepartmentDTO>  getDepartmentById(Long deptId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<DepartmentDTO> entity = new HttpEntity<DepartmentDTO>(headers);

       return Optional.ofNullable(restTemplate.exchange(httpDepartmentRequest + deptId, HttpMethod.GET, entity, DepartmentDTO.class).getBody());

    }
    public Optional<ProjectDTO> getProjectById(Long projectId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ProjectDTO> entity = new HttpEntity<ProjectDTO>(headers);
        return Optional.ofNullable(restTemplate.exchange(httpProjectRequest + projectId, HttpMethod.GET, entity, ProjectDTO.class).getBody());
    }


}
