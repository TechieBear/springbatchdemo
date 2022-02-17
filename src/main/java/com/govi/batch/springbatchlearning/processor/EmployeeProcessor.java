package com.govi.batch.springbatchlearning.processor;

import java.util.Random;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;
import com.govi.batch.springbatchlearning.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {
	
	@Autowired
	private ExecutionContext executionContext;

    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        //employee.setEmployeeId(employeeDTO.getEmployeeId()+new Random().nextInt(10000));
        employee.setEmployeeId(employeeDTO.getEmployeeId()+executionContext.getString("customFileName"));
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        System.out.println("inside processor " + employee.toString());
        return employee;
    }
}