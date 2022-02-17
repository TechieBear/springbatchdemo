/*package com.govi.batch.springbatchlearning.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;
import com.govi.batch.springbatchlearning.mapper.EmployeeDBRowMapper;
import com.govi.batch.springbatchlearning.model.Employee;
import com.govi.batch.springbatchlearning.processor.DBToFileEmployeeProcessor;

@Configuration
public class DBToFileDemoJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
    private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
    private DBToFileEmployeeProcessor dBToFileEmployeeProcessor;
	
	private Resource outputResource = new FileSystemResource("output/employee_output.csv");
	
	 @Qualifier(value = "dbToFileDemoJob")
	    @Bean
	    public Job dbToFileJob() throws Exception {
	        return this.jobBuilderFactory.get("dbToFileDemoJob")
	                .start(step1Demo3())
	                .build();
	    }

	    @Bean
	    public Step step1Demo3() throws Exception {
	        return this.stepBuilderFactory.get("step1")
	                .<Employee, EmployeeDTO>chunk(10)
	                .reader(employeeDBReader())
	                .processor(dBToFileEmployeeProcessor)
	                .writer(employeeFileWriter())
	                .build();
	    }


	    @Bean
	    public ItemStreamReader<Employee> employeeDBReader() {
	        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
	        reader.setDataSource(dataSource);
	        reader.setSql("select * from employee");
	        reader.setRowMapper(new EmployeeDBRowMapper());
	        return reader;
	    }

	    @Bean
	    public ItemWriter<EmployeeDTO> employeeFileWriter() throws Exception {
	        FlatFileItemWriter<EmployeeDTO> writer = new FlatFileItemWriter<>();
	        writer.setResource(outputResource);
	        writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
	            {
	                setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
	                    {
	                        setNames(new String[]{"employeeId", "firstName", "lastName", "email", "age"});
	                    }
	                });
	            }
	        });
	        writer.setShouldDeleteIfExists(true);
	        return writer;
	    }
}
*/