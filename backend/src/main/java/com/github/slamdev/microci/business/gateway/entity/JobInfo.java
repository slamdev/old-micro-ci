package com.github.slamdev.microci.business.gateway.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class JobInfo {
    private final String name;
    private final long durationInMillis;
    private final Instant finishedDate;
    private final int buildNumber;
    private final Status status;
}
