package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
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
    private BuildInfoConverter converter;

    @Cacheable(BuildRepository.CACHE_NAME)
    public BuildInfo getLast(String jobName) {
        return repository.findTopByJobNameOrderByFinishedDate(jobName).map(converter::convert).orElse(null);
    }

    @Cacheable(BuildRepository.CACHE_NAME)
    public List<BuildInfo> getAll(String jobName) {
        return repository.findAll(jobName).stream().map(converter::convert).collect(toList());
    }
}
