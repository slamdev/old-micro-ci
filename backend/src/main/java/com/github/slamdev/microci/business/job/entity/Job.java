package com.github.slamdev.microci.business.job.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Job {
    private final String name;
    private final List<Task> tasks;
}
