package com.github.slamdev.microci.business.executor.entity;

import com.github.slamdev.microci.business.gateway.entity.Status;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskExecutionResult {
    private final Status status;
}
