package com.github.slamdev.microci.business.project.boundary;

public class ProjectNotFoundException extends IllegalArgumentException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
