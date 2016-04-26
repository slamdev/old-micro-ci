package com.github.slamdev.microci.business.executor.entity;

import com.github.slamdev.microci.business.gateway.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class TaskExecutionResult {
    private final String name;
    private final boolean allowFailure;
    private final Instant finishedDate;
    private final long logId;
    private final Instant startedDate;
    private final Status status;
}
