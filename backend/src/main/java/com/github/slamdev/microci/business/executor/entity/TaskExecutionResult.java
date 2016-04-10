package com.github.slamdev.microci.business.executor.entity;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.SKIPPED;

public class TaskExecutionResult {

    private final Status status;

    public TaskExecutionResult(Status status) {
        this.status = status;
    }

    public TaskExecutionResult() {
        status = SKIPPED;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        SUCCESS, FAILED, SKIPPED;
    }
}
