package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JobInfoConverter {

    public JobInfo convert(Build build) {
        return JobInfo.builder()
                .name(build.getJobName())
                .status(build.getStatus())
                .buildNumber(build.getNumber())
                .finishedDate(build.getFinishedDate())
                .durationInMillis(calculateDuration(build))
                .build();
    }

    private long calculateDuration(Build build) {
        return Duration.between(build.getStartedDate(), build.getFinishedDate()).toMillis();
    }
}
