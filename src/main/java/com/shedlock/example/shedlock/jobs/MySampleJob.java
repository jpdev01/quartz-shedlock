package com.shedlock.example.shedlock.jobs;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MySampleJob extends QuartzJobBean {

	private String name;

    public MySampleJob()  {
    }

	// Inject the "name" job data property
	public void setName(String name) {
		this.name = name;
	}

	@Override
    @SchedulerLock(name = "my_sample_job_lock", lockAtLeastFor = "PT5S", lockAtMostFor = "PT30S")
    protected void executeInternal(JobExecutionContext context) {
        LockAssert.assertLocked(); // Garante que o lock foi adquirido

        System.out.println("Hello World!");
	}

}