package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import com.github.slamdev.microci.business.repository.boundary.RepositoryAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import static java.util.Objects.requireNonNull;

public class JobDescriptorFetcher {

    static final String DESCRIPTOR_NAME = "micro-ci.yml";

    @Autowired
    private RepositoryAccessor repositoryAccessor;

    public Resource get(Project project) {
        requireNonNull(project);
        return repositoryAccessor.getResource(project, DESCRIPTOR_NAME);
    }
}
