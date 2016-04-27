package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;

import java.util.List;
import java.util.Optional;

public interface BuildRepository {

    String CACHE_NAME = "build";

    void save(Build build);

    Optional<Build> findTopByJobNameOrderByFinishedDate(String jobName);

    List<Build> findAll(String jobName);
}
