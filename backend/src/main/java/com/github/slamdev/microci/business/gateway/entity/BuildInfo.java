package com.github.slamdev.microci.business.gateway.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BuildInfo {
    private final JobInfo job;
    private final Status status;
    private final CommitInfo commit;
    private final List<TaskInfo> tasks;
}
