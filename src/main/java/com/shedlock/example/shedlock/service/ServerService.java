package com.shedlock.example.shedlock.service;

import jakarta.transaction.Transactional;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Transactional
@Service
public class ServerService {

    private final Scheduler scheduler;

    public ServerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void stop() throws Exception {
        scheduler.pauseAll();

        boolean allPaused = awaitForJobsToPauseGracefully();
        if (!allPaused) throw new RuntimeException("Could not pause all jobs gracefully");
    }

    private boolean awaitForJobsToPauseGracefully() throws SchedulerException, InterruptedException {
        final Date startTime = new Date();

        while (checkIfJobsAreRunning()) {
            if (reachedTimeout(startTime)) return false;

            Thread.sleep(Duration.ofSeconds(10).toMillis());
        }

        return !checkIfJobsAreRunning();
    }

    private boolean checkIfJobsAreRunning() throws SchedulerException {
        return !scheduler.getCurrentlyExecutingJobs().isEmpty();
    }

    private boolean reachedTimeout(Date startTime) {
        Duration timeout = Duration.ofMinutes(2);
        Date finalDate = new Date();

        long timeDiffInMilliseconds = Math.abs(finalDate.getTime() - startTime.getTime());
        return Duration.ofMillis(timeDiffInMilliseconds).compareTo(timeout) >= 0;
    }
}
