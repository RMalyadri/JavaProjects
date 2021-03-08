package com.trans.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchTransJobExecutionListener implements JobExecutionListener {


    public void beforeJob(JobExecution jobExecution) {
        log.info("job (name:{}) has been started",jobExecution.getJobInstance().getJobName());
    }

    public void afterJob(JobExecution jobExecution) {
        log.debug("job (name:{}) has been completed and status:{}",jobExecution.getJobInstance().getJobName(),jobExecution.getStatus());
    }
}