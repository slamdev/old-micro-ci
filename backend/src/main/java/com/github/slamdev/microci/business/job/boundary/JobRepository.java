package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;

import java.util.List;

public interface JobRepository {

    String CACHE_NAME = "job";

    List<Job> findAll();
}
