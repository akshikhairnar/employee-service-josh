package com.employeeservice.employee.mapper;

import com.employeeservice.employee.dto.EmployeeDTO;
import com.employeeservice.employee.entity.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static Employee employeeMapper(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmpId(employeeDTO.getEmpId());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
       // employee.setProjectId(employeeDTO.getProjectId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        return employee;
    }

    public static EmployeeDTO employeeDTOMapper(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employee.getEmpId());
        employeeDTO.setDepartmentId(employee.getDepartmentId());
        //employeeDTO.setDepartmentDTO(DepartmentMapper.departmentDTOMapper(employee.getDepartment()));
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
      //  employeeDTO.setProjectId(employee.getProjectId());
        return employeeDTO;
    }
}
