package com.govi.batch.springbatchlearning.writer;

import org.springframework.batch.item.ItemWriter;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;

import java.util.List;

public class EmailSenderWriter implements ItemWriter<EmployeeDTO> {
    @Override
    public void write(List<? extends EmployeeDTO> list) throws Exception {
        System.out.println("Email send successfully to all the employees.");
    }
}
