package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JobInfoBuilderTest {

    @InjectMocks
    private JobInfoBuilder jobInfoBuilder;

    @Test
    public void should_build_list_of_all_jobs() {
        List<JobInfo> jobs = jobInfoBuilder.buildAll();
        assertFalse(jobs.isEmpty());
    }

}