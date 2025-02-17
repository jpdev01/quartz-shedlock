package com.shedlock.example.shedlock.jobs;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MySampleJob extends QuartzJobBean {

    public MySampleJob() {
    }

    @Override
    @SchedulerLock(name = "my_sample_job_lock", lockAtLeastFor = "PT1S", lockAtMostFor = "PT10S")
    protected void executeInternal(JobExecutionContext context) {
        LockAssert.assertLocked();
        System.out.println("Hello World!");
    }

}