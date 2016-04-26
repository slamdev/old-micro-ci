package com.github.slamdev.microci.business.gateway.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommitInfo {
    private final String commitSHA;
    private final String authorEmail;
    private final String authorName;
    private final String branch;
    private final String message;
}
