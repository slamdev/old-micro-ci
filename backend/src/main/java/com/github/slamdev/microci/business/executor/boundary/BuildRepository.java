package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;

import java.util.List;

public interface BuildRepository {

    String CACHE_NAME = "build";

    void save(Build build);

    List<Build> findDistinctByJobOrderByFinishedDate();
}
