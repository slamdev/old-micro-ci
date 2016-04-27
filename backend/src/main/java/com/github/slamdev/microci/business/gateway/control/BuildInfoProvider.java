package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BuildInfoProvider {

    @Autowired
    private BuildRepository repository;

    @Autowired
    private JobInfoConverter jobInfoConverter;

    @Autowired
    private CommitInfoConverter commitInfoConverter;

    @Autowired
    private TasksInfoConverter tasksInfoConverter;

    @Cacheable(BuildRepository.CACHE_NAME)
    public BuildInfo getLast(String jobName) {
        return repository.findTopByJobNameOrderByFinishedDate(jobName).map(this::convert).orElse(null);
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
        return repository.findAll(jobName).stream().map(this::convert).collect(toList());
    }
}
