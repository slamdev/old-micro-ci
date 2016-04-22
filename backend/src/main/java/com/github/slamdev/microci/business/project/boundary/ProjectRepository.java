package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;

public interface ProjectRepository {

    boolean contains(Project project);

    void save(Project project);

    Project get(long id);

    void delete(long id);
}
