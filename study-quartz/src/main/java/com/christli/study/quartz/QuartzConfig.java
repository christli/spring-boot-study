package com.christli.study.quartz;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class QuartzConfig {

    @Autowired
    private Scheduler scheduler;

    @Bean
    public void config() throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                // 任务标识，及任务分组
                .withIdentity("job" + (new Date()).toString(), "group1" + (new Date()).toString())
                // 链接调用，增加需要的参数
                .usingJobData("name", "christli")
                .usingJobData("age", 18)
                .build();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1" + (new Date()).toString(), "group1" + (new Date()).toString())
                // 立即执行
                .startNow()
                // 10s后停止
                .endAt(new Date(System.currentTimeMillis() + 10 * 1000))
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                // 每秒执行一次
                                .withIntervalInSeconds(1)
                                // 一直执行
                                .repeatForever()
                )
                .build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }
}
