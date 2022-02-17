
/*package com.govi.batch.springbatchlearning.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.govi.batch.springbatchlearning.dto.EmployeeDTO;
import com.govi.batch.springbatchlearning.mapper.EmployeeFileRowMapper;
import com.govi.batch.springbatchlearning.model.Employee;
import com.govi.batch.springbatchlearning.processor.EmployeeProcessor;
import com.govi.batch.springbatchlearning.skip.CustomSkipPolicy;
import com.govi.batch.springbatchlearning.writer.EmployeeDBWriter;

@Configuration
public class FileToDbDemoJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EmployeeProcessor employeeProcessor;
	
	@Autowired
	private EmployeeDBWriter employeeWriter;


	@Qualifier(value = "fileToDbDemoJob")
	@Bean
	public Job fileToDbJob() throws Exception {
		return this.jobBuilderFactory.get("fileToDbDemoJob")
				                     .start(fileToDbStep())
				                     .build();
	}

	@Bean
	public Step fileToDbStep() throws Exception {
		return this.stepBuilderFactory.get("fileToDb")
				.<EmployeeDTO, Employee>chunk(5)
				.reader(employeeReader())
				.processor(employeeProcessor)
				.writer(employeeWriter)
				.faultTolerant().skipPolicy(skipPolicy())
				.taskExecutor(taskExecutor())
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
						setDelimiter(",");
					}
				});
				setFieldSetMapper(new EmployeeFileRowMapper());
			}
		});
		return reader;
	}
	*/

	/*
	 * @Bean public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
	 * JdbcBatchItemWriter<Employee> itemWriter = new
	 * JdbcBatchItemWriter<Employee>(); itemWriter.setDataSource(dataSource);
	 * itemWriter.setSql(
	 * "insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)"
	 * ); itemWriter.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<Employee>()); return itemWriter; }
	 */

	/*@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(5);
		return simpleAsyncTaskExecutor;
	}
	
	@Bean
	public CustomSkipPolicy skipPolicy() {
		return new CustomSkipPolicy();
	}

}
*/