package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JobInfoBuilder {

    @Autowired
    private BuildRepository buildRepository;

    @Cacheable(BuildRepository.CACHE_NAME)
    public List<JobInfo> buildAll() {
        List<Build> builds = buildRepository.findDistinctByJobOrderByFinishedDate();
        return builds.stream().map(this::convert).collect(toList());
    }

    private JobInfo convert(Build build) {
        return JobInfo.builder()
                .status(build.getStatus())
                .buildNumber(build.getNumber())
                .finishedDate(build.getFinishedDate())
                .durationInMillis(calculateDuration(build))
                .name(build.getJobName())
                .build();
    }

    private long calculateDuration(Build build) {
        return Duration.between(build.getStartDate(), build.getFinishedDate()).toMillis();
    }
}
