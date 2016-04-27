package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BranchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BranchInfoConverter {

    @Autowired
    private BuildInfoConverter buildInfoConverter;

    public BranchInfo convert(List<Build> lastBuilds, long allBuildsCount) {
        if (lastBuilds.isEmpty()) {
            return null;
        }
        return BranchInfo.builder()
                .allBuildsCount(allBuildsCount)
                .name(lastBuilds.get(0).getBranch())
                .lastBuilds(lastBuilds.stream().map(buildInfoConverter::convert).collect(toList()))
                .build();
    }
}
