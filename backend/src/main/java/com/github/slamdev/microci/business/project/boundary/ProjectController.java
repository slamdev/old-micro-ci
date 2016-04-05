package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;

import java.util.HashSet;
import java.util.Set;

public class ProjectController {

    private final Set<Project> projects = new HashSet<>();

    public Project create(String url) {
        validateUrl(url);
        Project project = fromUrl(url);
        if (projects.contains(project)) {
            throw new ProjectAlreadyExistsException(url);
        }
        projects.add(project);
        return project;
    }

    private Project fromUrl(String url) {
        String[] parts = url.split(":");
        String host = parts[0].split("@")[1];
        String owner = parts[1].split("/")[0];
        String name = parts[1].split("/")[1].replace(".git", "");
        return new Project(1L, host, owner, name);
    }

    private void validateUrl(String url) {
        String sshPrefix = "ssh://";
        String gitPrefix = "git@";
        if (url.startsWith(sshPrefix + gitPrefix) || url.startsWith(gitPrefix)) {
            return;
        }
        throw new NotSshUrlException(url);
    }

    public Project get(Long id) {
        return projects.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
    }

    public void delete(Long id) {
        Project project = get(id);
        projects.remove(project);
    }
}
