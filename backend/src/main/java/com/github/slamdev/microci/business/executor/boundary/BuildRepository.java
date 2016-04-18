package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;

public interface BuildRepository {
    void save(Build build);
}
