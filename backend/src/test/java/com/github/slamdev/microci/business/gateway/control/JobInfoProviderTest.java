package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import com.github.slamdev.microci.business.job.boundary.JobRepository;
import com.github.slamdev.microci.business.job.entity.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.github.slamdev.microci.business.gateway.entity.Status.SUCCESS;
import static java.time.Instant.ofEpochMilli;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobInfoProviderTest {

    private static final String JOB_NAME = "some-job";

    private static final Job JOB_STUB = Job.builder().name(JOB_NAME).build();

    @InjectMocks
    private JobInfoProvider provider;

    @Mock
    private BuildRepository buildRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    public void should_build_list_of_all_jobs() {
        when(jobRepository.findAll()).thenReturn(singletonList(JOB_STUB));
        Build build = Build.builder()
                .status(SUCCESS)
                .number(1)
                .startDate(ofEpochMilli(1))
                .finishedDate(ofEpochMilli(11))
                .jobName(JOB_NAME)
                .build();
        when(buildRepository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(build);
        List<JobInfo> jobs = provider.get();
        assertEquals(1, jobs.size());
        assertEquals(SUCCESS, jobs.get(0).getStatus());
        assertEquals(JOB_NAME, jobs.get(0).getName());
        assertEquals(1, jobs.get(0).getBuildNumber());
        assertEquals(10, jobs.get(0).getDurationInMillis());
        assertEquals(ofEpochMilli(11), jobs.get(0).getFinishedDate());
    }

    @Test
    public void should_return_empty_job_if_no_build() {
        when(jobRepository.findAll()).thenReturn(singletonList(JOB_STUB));
        when(buildRepository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(null);
        List<JobInfo> jobs = provider.get();
        assertEquals(1, jobs.size());
        assertEquals(JOB_NAME, jobs.get(0).getName());
        assertEquals(null, jobs.get(0).getStatus());
        assertEquals(0, jobs.get(0).getBuildNumber());
        assertEquals(0, jobs.get(0).getDurationInMillis());
        assertEquals(null, jobs.get(0).getFinishedDate());
    }
}
