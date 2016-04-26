package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;

public interface BuildRepository {

    String CACHE_NAME = "build";

    void save(Build build);

    Build findTopByJobNameOrderByFinishedDate(String jobName);
}
