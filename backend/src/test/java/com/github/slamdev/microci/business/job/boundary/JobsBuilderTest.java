package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.security.util.InMemoryResource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JobsBuilderTest {

    private static final Resource RESOURCE_STUB = new InMemoryResource("");

    private static final String SINGLE_JOB_DESCRIPTOR = "name: single job";

    private static final String MULTIPLY_JOB_DESCRIPTOR = "name: first job\n---\nname: second job";

    @InjectMocks
    private JobsBuilder builder;

    @Test(expected = NullPointerException.class)
    public void should_error_if_descriptor_null() {
        builder.build(null);
    }

    @Test
    public void should_return_empty_list_if_no_jobs_in_descriptor() {
        List<Job> jobs = builder.build(RESOURCE_STUB);
        assertTrue(jobs.isEmpty());
    }

    @Test
    public void should_return_one_job_for_single_descriptor() {
        List<Job> jobs = builder.build(new InMemoryResource(SINGLE_JOB_DESCRIPTOR));
        assertEquals(1, jobs.size());
    }

    @Test
    public void should_return_multiply_jobs_for_multiply_descriptor() {
        List<Job> jobs = builder.build(new InMemoryResource(MULTIPLY_JOB_DESCRIPTOR));
        assertEquals(2, jobs.size());
    }
}