package com.employeeservice.employee.externalservices;

import com.employeeservice.employee.dto.DepartmentDTO;
import com.employeeservice.employee.dto.ProjectDTO;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EmployeeDaprComponent {

    private static final Logger log = LoggerFactory.getLogger(EmployeeDaprComponent.class);
    @Autowired
    private DaprClient daprClient;

    @Value("${department.dapr.id}")
    private String departmentDaprId;

    @Value("${department.dapr.method}")
    private String daprMethodName;

    @Value("${project.dapr.id}")
    private String projectDaprId;

    @Value("${project.dapr.method}")
    private String projectMethodName;


    public Mono<DepartmentDTO> invokeGetDepartmentById(Long deptId) {
        return daprClient.invokeMethod(departmentDaprId, daprMethodName + deptId, null, HttpExtension.GET, DepartmentDTO.class);
    }

    public Mono<ProjectDTO> invokeGetProjectById(Long projectId) {
        return daprClient.invokeMethod(projectDaprId, projectMethodName + projectId, null, HttpExtension.GET, ProjectDTO.class);
    }

    public void  notifyServices(String msg){
        //log.info("Notification service get call");
       // daprClient.invokeBinding("employeepubsub","test","New Employee Added");
       //return
        daprClient.publishEvent("employeepubsub","testdapr",msg).block();
    }


}
