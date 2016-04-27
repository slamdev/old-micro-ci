package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildInfoConverter {

    @Autowired
    private JobInfoConverter jobInfoConverter;

    @Autowired
    private CommitInfoConverter commitInfoConverter;

    @Autowired
    private TasksInfoConverter tasksInfoConverter;

    public BuildInfo convert(Build build) {
        return BuildInfo.builder()
                .status(build.getStatus())
                .job(jobInfoConverter.convert(build))
                .commit(commitInfoConverter.convert(build))
                .tasks(tasksInfoConverter.convert(build))
                .build();
    }
}
