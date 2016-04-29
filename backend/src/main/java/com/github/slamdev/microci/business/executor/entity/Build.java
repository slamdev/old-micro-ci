package com.github.slamdev.microci.business.executor.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class Build {
    private final List<TaskExecutionResult> taskResults;
    private final Status status;
    private final int number;
    private final Instant finishedDate;
    private final String jobName;
    private final Instant startedDate;
    private final String commitSHA;
    private final String authorEmail;
    private final String authorName;
    private final String branch;
    private final String commitMessage;
}
