package com.github.slamdev.microci.business.project.boundary;

public class ProjectAlreadyExistsException extends IllegalArgumentException {
    public ProjectAlreadyExistsException(String message) {
        super(message);
    }
}
