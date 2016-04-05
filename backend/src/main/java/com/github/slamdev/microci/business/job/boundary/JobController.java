package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.project.boundary.ProjectController;
import com.github.slamdev.microci.business.project.boundary.ProjectNotFoundException;
import com.github.slamdev.microci.business.project.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.util.List;

import static java.lang.String.valueOf;

public class JobController {

    @Autowired
    private ProjectController projectController;

    @Autowired
    private JobDescriptorFetcher descriptorFetcher;

    @Autowired
    private JobsBuilder jobsBuilder;

    public List<Job> create(long projectId) {
        Resource descriptor = getDescriptor(projectId);
        return jobsBuilder.build(descriptor);
    }

    public Resource getDescriptor(long projectId) {
        Project project = acquireProject(projectId);
        return descriptorFetcher.get(project);
    }

    private Project acquireProject(long projectId) {
        Project project = projectController.get(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(valueOf(projectId));
        }
        return project;
    }
}
