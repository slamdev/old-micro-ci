package com.github.slamdev.microci.business.executor.entity;

import com.github.slamdev.microci.business.gateway.entity.Status;
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
    private final Instant startDate;
}
