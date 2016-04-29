package com.github.slamdev.microci.business.gateway.boundary;

import com.github.slamdev.microci.business.gateway.control.BranchInfoProvider;
import com.github.slamdev.microci.business.gateway.control.BuildInfoProvider;
import com.github.slamdev.microci.business.gateway.control.JobInfoProvider;
import com.github.slamdev.microci.business.gateway.entity.BranchInfo;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class ApiGateway {

    @Autowired
    private JobInfoProvider jobProvider;

    @Autowired
    private BuildInfoProvider buildProvider;

    @Autowired
    private BranchInfoProvider branchProvider;

    @RequestMapping(path = "/job", method = GET)
    public List<JobInfo> getJobs() {
        return jobProvider.get();
    }

    @RequestMapping(path = "/job/{name}/build/last", method = GET)
    public BuildInfo getLastBuild(@PathVariable("name") String jobName) {
        return buildProvider.getLast(jobName);
    }

    @RequestMapping(path = "/job/{name}/build", method = GET)
    public List<BuildInfo> getBuilds(@PathVariable("name") String jobName) {
        return buildProvider.getAll(jobName);
    }

    @RequestMapping(path = "/job/{name}/branch", method = GET)
    public List<BranchInfo> getBranches(@PathVariable("name") String jobName,
                                        @RequestParam("buildsCount") int buildsCount) {
        return branchProvider.getAll(jobName, buildsCount);
    }
}
