package com.github.slamdev.microci.business.executor.entity;

import java.util.List;

public class JobExecutionResult {

    private final List<TaskExecutionResult> taskResults;

    public JobExecutionResult(List<TaskExecutionResult> taskResults) {
        this.taskResults = taskResults;
    }

    public List<TaskExecutionResult> getTaskResults() {
        return taskResults;
    }
}
