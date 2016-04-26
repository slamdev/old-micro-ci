package com.github.slamdev.microci.business.gateway.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class TaskInfo {
    private final String name;
    private final boolean allowFailure;
    private final Instant finishedDate;
    private final long logId;
    private final long durationInMillis;
    private final Status status;
}
