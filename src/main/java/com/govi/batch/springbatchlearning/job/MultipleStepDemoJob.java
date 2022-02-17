package com.govi.batch.springbatchlearning.job;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;
import com.govi.batch.springbatchlearning.mapper.EmployeeDBRowMapper;
import com.govi.batch.springbatchlearning.mapper.EmployeeFileRowMapper;
import com.govi.batch.springbatchlearning.model.Employee;
import com.govi.batch.springbatchlearning.processor.EmployeeProcessor;
import com.govi.batch.springbatchlearning.skip.CustomSkipPolicy;
import com.govi.batch.springbatchlearning.writer.EmailSenderWriter;

import javax.sql.DataSource;

@Configuration
public class MultipleStepDemoJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EmployeeProcessor employeeProcessor;
	
	@Autowired
	private DataSource dataSource;

	private Resource outputResource = new FileSystemResource("output/employee_output.csv");

	@Qualifier(value = "multipleStepDemoJob")
	@Bean
	public Job multipleStepDemo() throws Exception {
		return this.jobBuilderFactory.get("multipleStepDemoJob")
				.start(fileToDbStep())
				.next(dbToEmailStep())
				.build();
	}

	@Bean
	public Step fileToDbStep() throws Exception {
		return this.stepBuilderFactory.get("fileToDbStep")
				.<EmployeeDTO, Employee>chunk(10)
				.reader(employeeReader())
				.writer(employeeDBWriterDefault())
				.faultTolerant().skipPolicy(skipPolicy())
				.processor(employeeProcessor)
				.build();
	}

	@Bean
	public Step dbToEmailStep() throws Exception {
		return this.stepBuilderFactory.get("dbToEmailStep")
				.<Employee, EmployeeDTO>chunk(10)
				.reader(employeeDBReader())
				.writer(emailSenderWriter())
				.build();
	}

	@Bean
	@StepScope
	Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
		return new ClassPathResource(fileName);
	}

	@Bean

	@StepScope
	public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
		FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
		reader.setResource(inputFileResource(null));
		reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "firstName", "lastName", "email", "age");
					}
				});
				setFieldSetMapper(new EmployeeFileRowMapper());
			}
		});
		return reader;
	}

	@Bean
	public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
		JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return itemWriter;
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
	public ItemWriter<? super EmployeeDTO> employeeFileWriter() throws Exception {
		FlatFileItemWriter<EmployeeDTO> writer = new FlatFileItemWriter<>();
		writer.setResource(outputResource);
		writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
			{
				setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
					{
						setNames(new String[] { "employeeId", "firstName", "lastName", "email", "age" });
					}
				});
			}
		});
		writer.setShouldDeleteIfExists(true);
		return writer;
	}

	@Bean
	EmailSenderWriter emailSenderWriter() {
		return new EmailSenderWriter();
	}
	
	@Bean
	public CustomSkipPolicy skipPolicy() {
		return new CustomSkipPolicy();
	}
}
