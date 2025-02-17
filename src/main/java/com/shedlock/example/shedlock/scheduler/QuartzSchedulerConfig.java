package com.shedlock.example.shedlock.scheduler;

import com.shedlock.example.shedlock.jobs.MySampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedulerConfig {

    public QuartzSchedulerConfig() {
    }

    @Bean
    public JobDetail mySampleJobDetail() {
        return JobBuilder.newJob(MySampleJob.class)
                .withIdentity("MySampleJob")
                .usingJobData("name", "Minha Tarefa Agendada")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger mySampleJobTrigger(JobDetail mySampleJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(mySampleJobDetail)
                .withIdentity("MySampleJobTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
    }
}
