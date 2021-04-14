package com.christli.study.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class SimpleJob implements Job {
    protected static Logger log = LoggerFactory.getLogger(SimpleJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("job execute---" + new Date());
    }
}
