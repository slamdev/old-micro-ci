package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JobInfoBuilder {

    public List<JobInfo> buildAll() {
        return Collections.emptyList();
    }
}
