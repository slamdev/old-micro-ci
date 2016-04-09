package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobExecutorTest {

    @InjectMocks
    private JobExecutor executor;

    @Test
    public void should_execute_job() {
        executor.execute(new Job("asd"));
    }
}