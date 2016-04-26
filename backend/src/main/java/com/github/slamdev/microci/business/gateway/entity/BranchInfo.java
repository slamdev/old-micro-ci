package com.github.slamdev.microci.business.gateway.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BranchInfo {
    private final String name;
    private final long allBuildsCount;
    private final List<BuildInfo> lastBuilds;
}
