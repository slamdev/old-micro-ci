package com.github.slamdev.microci.business.job.entity;

import java.util.List;

public class Job {

    private final String name;
    private final List<Task> tasks;

    public Job(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
