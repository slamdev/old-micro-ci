package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildInfoProvider {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private JobInfoConverter jobInfoConverter;

    @Autowired
    private CommitInfoConverter commitInfoConverter;

    @Autowired
    private TasksInfoConverter tasksInfoConverter;

    @Cacheable(BuildRepository.CACHE_NAME)
    public BuildInfo getLast(String jobName) {
        Build build = buildRepository.findTopByJobNameOrderByFinishedDate(jobName);
        return convert(build);
    }

    private BuildInfo convert(Build build) {
        return BuildInfo.builder()
                .status(build.getStatus())
                .job(jobInfoConverter.convert(build))
                .commit(commitInfoConverter.convert(build))
                .tasks(tasksInfoConverter.convert(build))
                .build();
    }

    public List<BuildInfo> getAll(String jobName) {
        return null;
    }
}
