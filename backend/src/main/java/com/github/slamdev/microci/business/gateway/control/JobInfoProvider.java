package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import com.github.slamdev.microci.business.job.boundary.JobRepository;
import com.github.slamdev.microci.business.job.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class JobInfoProvider {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobInfoConverter converter;

    @Cacheable({JobRepository.CACHE_NAME, BuildRepository.CACHE_NAME})
    public List<JobInfo> get() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convert).collect(toList());
    }

    private JobInfo convert(Job job) {
        Optional<Build> build = buildRepository.findTopByJobNameOrderByFinishedDate(job.getName());
        return build.map(converter::convert).orElse(JobInfo.builder().name(job.getName()).build());
    }
}
