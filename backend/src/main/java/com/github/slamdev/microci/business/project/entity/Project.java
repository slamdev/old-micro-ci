package com.github.slamdev.microci.business.project.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Project {
    private final String name;
    private final String owner;
    private final String host;
    private final Long id;
}
