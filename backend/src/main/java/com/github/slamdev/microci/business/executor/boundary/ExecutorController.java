package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.job.boundary.JobController;
import com.github.slamdev.microci.business.job.boundary.JobNotFound;
import com.github.slamdev.microci.business.job.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExecutorController {

    @Autowired
    private JobController jobController;

    @Autowired
    private JobExecutor jobExecutor;

    @Autowired
    private BuildRepository buildRepository;

    public Build execute(long projectId, String jobName) {
        List<Job> jobs = jobController.get(projectId);
        Job job = jobs.stream().filter(j -> j.getName().equals(jobName)).findAny().orElseThrow(JobNotFound::new);
        Build build = jobExecutor.execute(job);
        buildRepository.save(build);
        return build;
    }
}
