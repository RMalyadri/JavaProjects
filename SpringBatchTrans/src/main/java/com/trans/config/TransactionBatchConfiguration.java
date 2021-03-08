package com.trans.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.trans.batch.BatchTransJobExecutionListener;
import com.trans.batch.PersonItemProcessor;
import com.trans.bean.Person;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class TransactionBatchConfiguration {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  @Autowired
  private DataSource dataSource;
  @Value("${file.input}")
  private String xmlFileName;
  @Autowired
  private BatchTransJobExecutionListener batchTransJobExecutionListener;

  @Bean
  public ItemReader<Person> itemReader() {
      return new JdbcCursorItemReaderBuilder<Person>()
              .name("cursorItemReader")
              .dataSource(dataSource)
              .sql("select * from person where update_status='Y'")
              .rowMapper(new BeanPropertyRowMapper<>(Person.class))
              .build();
  }

  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  @Bean
  public Job transactionBatchJob() {
      return jobBuilderFactory.get("transactionBatchJob")
    	  .listener(batchTransJobExecutionListener)
          .start(step1())
          .next(taskletStep())
          .build();
  }
  
  @Bean
  public Step taskletStep() {
      return stepBuilderFactory.get("taskletStep")
          .tasklet(tasklet())
          .build();
  }

  @Bean
  public Tasklet tasklet() {
      return (contribution, chunkContext) -> {
    	  log.info("first tasklet started"+dataSource);
          return RepeatStatus.FINISHED;
      };
  }
  
  @Bean
  public Step step1() {
      return stepBuilderFactory.get("step1")
        .<Person, Person> chunk(10)
        .reader(itemReader())
        .processor(processor())
        .writer(itemWriter())
        .build();
  }
  
  @Bean(destroyMethod="")
  public ItemWriter<Person> itemWriter() {
      Resource exportFileResource = new FileSystemResource(xmlFileName);
      XStreamMarshaller studentMarshaller = new XStreamMarshaller();
      studentMarshaller.setAliases(Collections.singletonMap(
              "person",
              Person.class
      ));

      return new StaxEventItemWriterBuilder<Person>()
              .name("studentWriter")
              .resource(exportFileResource)
              .marshaller(studentMarshaller)
              .rootTagName("persons")
              .build();
  }
  

}