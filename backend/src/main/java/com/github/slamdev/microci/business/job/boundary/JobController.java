package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.project.boundary.ProjectNotFoundException;
import com.github.slamdev.microci.business.project.boundary.ProjectRepository;
import com.github.slamdev.microci.business.project.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;

public class JobController {

    private final Map<Project, List<Job>> projectJobs = new HashMap<>();

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JobDescriptorFetcher descriptorFetcher;

    @Autowired
    private JobsBuilder jobsBuilder;

    public List<Job> create(long projectId) {
        Resource descriptor = getDescriptor(projectId);
        List<Job> jobs = jobsBuilder.build(descriptor);
        Project project = acquireProject(projectId);
        projectJobs.put(project, jobs);
        return jobs;
    }

    public Resource getDescriptor(long projectId) {
        Project project = acquireProject(projectId);
        return descriptorFetcher.get(project);
    }

    private Project acquireProject(long projectId) {
        Project project = projectRepository.get(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(valueOf(projectId));
        }
        return project;
    }

    public List<Job> get(long projectId) {
        Project project = acquireProject(projectId);
        return projectJobs.containsKey(project) ? projectJobs.get(project) : emptyList();
    }
}
