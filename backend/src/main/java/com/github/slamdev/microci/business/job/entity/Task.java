package com.github.slamdev.microci.business.job.entity;

public class Task {

    private final String command;

    public Task(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
