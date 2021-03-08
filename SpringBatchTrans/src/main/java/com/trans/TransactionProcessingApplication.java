package com.trans;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TransactionProcessingApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job transactionBatchJob;

	public static void main(String[] args) {
		
		SpringApplication.run(TransactionProcessingApplication.class, args);

	}

	@Override
	public void run(String... args)  {
		JobExecution jobExecution = null;
		try{
			JobParameters params = new JobParametersBuilder().addString("statTime", String.valueOf(System.currentTimeMillis()))
					.toJobParameters();
			jobExecution = jobLauncher.run(transactionBatchJob, params);
		} catch(Exception exp) {
			log.error("{} has been failed and status:{}",jobExecution.getJobInstance(),jobExecution.getStatus());
			log.error("job execution expception",exp);
		}
		
	}

}