package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BranchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BranchInfoProvider {

    @Autowired
    private BuildRepository repository;

    @Autowired
    private BranchInfoConverter converter;

    public List<BranchInfo> getAll(String jobName) {
        List<Build> builds = repository.findAllByJobNameDistinctByBranchOrderByFinishedDate(jobName);
        List<String> branches = builds.stream().map(Build::getBranch).collect(toList());
        return branches.stream().map(this::toBranchInfo).collect(toList());
    }

    private BranchInfo toBranchInfo(String branch) {
        long count = repository.getCountByBranch(branch);
        List<Build> builds = repository.findAllByBranchOrderByFinishedDateWithLimit(branch, 5);
        return converter.convert(builds, count);
    }
}
