package com.govi.batch.springbatchlearning.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;
import com.govi.batch.springbatchlearning.model.Employee;

@Component
public class DBToFileEmployeeProcessor implements ItemProcessor<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO process(Employee employee) throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setAge(employee.getAge()+10);
        System.out.println("inside processor " + employee.toString());
        return employeeDTO;
    }
}
