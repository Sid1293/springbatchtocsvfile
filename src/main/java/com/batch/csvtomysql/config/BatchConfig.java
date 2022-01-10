package com.batch.csvtomysql.config;

import javax.annotation.processing.Processor;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.batch.csvtomysql.model.User;
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private DataSource dataSource;
	
	
	@Autowired
	private JobBuilderFactory builderFactory;
	
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	public FlatFileItemReader<User> reader(){
		
		FlatFileItemReader<User> reader=new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("test.csv"));
	    reader.setLineMapper(getLinrMapper());
	    reader.setLinesToSkip(1);
	
	    return reader;
	}


	private LineMapper<User> getLinrMapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<User> lineMapper=new DefaultLineMapper<>();
		
		DelimitedLineTokenizer linetokenizer=new DelimitedLineTokenizer();
		linetokenizer.setNames(new String[] {"first_name","last_name","country"});
		linetokenizer.setIncludedFields(new int[] {0,1,5});
		BeanWrapperFieldSetMapper<User> fieldSetter=new BeanWrapperFieldSetMapper<User>();
		fieldSetter.setTargetType(User.class);
		lineMapper.setLineTokenizer(linetokenizer);
		lineMapper.setFieldSetMapper(fieldSetter);
		return lineMapper;
	}

	
	 @Bean
	  public UserItemProcessor process() {
		  return new UserItemProcessor();
	  }

	 
	 
	 @Bean
	 public JdbcBatchItemWriter<User> writer(){
		 JdbcBatchItemWriter<User> writer=new JdbcBatchItemWriter<User>();
		 writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
         writer.setSql("insert into csv(firstname,lastname,country) values(:firstName,:lastName,:country)");
	     writer.setDataSource(this.dataSource);
         
        /* (`userid`, 
        			`firstname`, 
        			`lastname`, 
        			`country`
        			)*/
	     
	     return writer;
	 }
	 
	 
	 @Bean
	 public Job userJob() {
		 return (Job) this.builderFactory.get("USER-IMPORT-JOB")
				 .incrementer(new RunIdIncrementer())
				 .flow(step1())
				 .end()
				 .build();
	 }

    
	 @Bean
	public Step step1() {
		// TODO Auto-generated method stub
		return this.stepBuilderFactory.get("step1")
				.<User,User>chunk(10)
				.reader(reader())
				.processor(process())
				.writer(writer())
						.build();
	}
}