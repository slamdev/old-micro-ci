package com.github.slamdev.microci.business.project.entity;

import java.util.Objects;

public class Project {
    private final String name;
    private final String owner;
    private final String host;
    private final Long id;

    public Project(Long id, String host, String owner, String name) {
        this.id = id;
        this.host = host;
        this.owner = owner;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getHost() {
        return host;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
