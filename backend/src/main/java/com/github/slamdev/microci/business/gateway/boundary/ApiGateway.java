package com.github.slamdev.microci.business.gateway.boundary;

import com.github.slamdev.microci.business.gateway.control.BranchInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.BuildInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.JobInfoBuilder;
import com.github.slamdev.microci.business.gateway.entity.BranchInfo;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class ApiGateway {

    @Autowired
    private JobInfoBuilder jobBuilder;

    @Autowired
    private BuildInfoBuilder buildBuilder;

    @Autowired
    private BranchInfoBuilder branchBuilder;

    @RequestMapping(path = "/job", method = GET)
    public List<JobInfo> getJobs() {
        return jobBuilder.buildAll();
    }

    @RequestMapping(path = "/job/{name}/build/last", method = GET)
    public BuildInfo getLastBuild(@PathVariable("name") String jobName) {
        return buildBuilder.buildLast(jobName);
    }

    @RequestMapping(path = "/job/{name}/build", method = GET)
    public List<BuildInfo> getBuilds(@PathVariable("name") String jobName) {
        return buildBuilder.buildAll(jobName);
    }

    @RequestMapping(path = "/job/{name}/branch", method = GET)
    public List<BranchInfo> getBranches(@PathVariable("name") String jobName) {
        return branchBuilder.buildAll(jobName);
    }
}
