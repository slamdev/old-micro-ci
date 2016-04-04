package com.github.slamdev.microci.business.project.boundary;

public class NotSshUrlException extends IllegalArgumentException {
    public NotSshUrlException(String message) {
        super(message);
    }
}
