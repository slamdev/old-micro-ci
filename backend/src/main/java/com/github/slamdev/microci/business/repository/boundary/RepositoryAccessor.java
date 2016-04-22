package com.github.slamdev.microci.business.repository.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import org.springframework.core.io.Resource;

public interface RepositoryAccessor {

    Resource getResource(Project project, String name);
}
