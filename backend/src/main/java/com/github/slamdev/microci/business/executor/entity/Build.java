package com.github.slamdev.microci.business.executor.entity;

import java.util.List;

public class Build {

    private final List<TaskExecutionResult> taskResults;

    private final boolean jobFailed;

    public Build(List<TaskExecutionResult> taskResults, boolean jobFailed) {
        this.taskResults = taskResults;
        this.jobFailed = jobFailed;
    }

    public List<TaskExecutionResult> getTaskResults() {
        return taskResults;
    }

    public boolean isJobFailed() {
        return jobFailed;
    }
}
