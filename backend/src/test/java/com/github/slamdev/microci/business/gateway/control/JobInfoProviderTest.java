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

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
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

    @Mock
    private JobInfoConverter jobInfoConverter;

    @Test
    public void should_build_list_of_all_jobs() {
        when(jobRepository.findAll()).thenReturn(singletonList(JOB_STUB));
        Build build = Build.builder().build();
        when(buildRepository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(of(build));
        List<JobInfo> infos = provider.get();
        assertEquals(1, infos.size());
    }

    @Test
    public void should_return_empty_job_if_no_build() {
        when(jobRepository.findAll()).thenReturn(singletonList(JOB_STUB));
        when(buildRepository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(empty());
        List<JobInfo> infos = provider.get();
        assertEquals(1, infos.size());
        assertEquals(JOB_NAME, infos.get(0).getName());
        assertEquals(null, infos.get(0).getStatus());
        assertEquals(0, infos.get(0).getBuildNumber());
        assertEquals(0, infos.get(0).getDurationInMillis());
        assertEquals(null, infos.get(0).getFinishedDate());
    }
}
