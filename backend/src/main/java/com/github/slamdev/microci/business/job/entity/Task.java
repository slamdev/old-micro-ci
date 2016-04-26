package com.github.slamdev.microci.business.job.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Task {
    private final String command;
}
