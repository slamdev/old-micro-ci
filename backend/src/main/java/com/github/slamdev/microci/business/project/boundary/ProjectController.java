package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectController {

    @Autowired
    private ProjectBuilder builder;

    @Autowired
    private ProjectRepository repository;

    public Project create(String url) {
        Project project = builder.build(url);
        if (repository.contains(project)) {
            throw new ProjectAlreadyExistsException(url);
        }
        repository.save(project);
        return project;
    }
}
