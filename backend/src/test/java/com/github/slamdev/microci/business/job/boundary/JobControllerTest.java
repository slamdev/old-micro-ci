package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.project.boundary.ProjectNotFoundException;
import com.github.slamdev.microci.business.project.boundary.ProjectRepository;
import com.github.slamdev.microci.business.project.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.security.util.InMemoryResource;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

@RunWith(MockitoJUnitRunner.class)
public class JobControllerTest {

    private static final Project PROJECT_STUB = new Project(1L, "host", "owner", "name");

    private static final Resource RESOURCE_STUB = new InMemoryResource("");

    private static final Job JOB_STUB = new Job("stub", emptyList());

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private JobsBuilder jobsBuilder;

    @Mock
    private JobDescriptorFetcher jobDescriptorFetcher;

    @InjectMocks
    private JobController controller;

    @Test
    public void should_create_jobs_by_project() {
        long projectId = 1L;
        when(projectRepository.get(projectId)).thenReturn(PROJECT_STUB);
        when(jobsBuilder.build(any())).thenReturn(singletonList(JOB_STUB));
        List<Job> jobs = controller.create(projectId);
        notEmpty(jobs);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void should_error_if_project_not_exists() {
        when(projectRepository.get(anyLong())).thenReturn(null);
        long projectId = 0L;
        controller.create(projectId);
    }

    @Test
    public void should_return_jobs_descriptor() {
        long projectId = 1L;
        when(projectRepository.get(projectId)).thenReturn(PROJECT_STUB);
        when(jobDescriptorFetcher.get(PROJECT_STUB)).thenReturn(RESOURCE_STUB);
        Resource descriptor = controller.getDescriptor(projectId);
        notNull(descriptor);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void should_error_if_project_not_exists_for_descriptor() {
        when(projectRepository.get(anyLong())).thenReturn(null);
        long projectId = 0L;
        controller.getDescriptor(projectId);
    }

    @Test
    public void should_return_null_if_project_without_descriptor() {
        long projectId = 1L;
        when(projectRepository.get(projectId)).thenReturn(PROJECT_STUB);
        when(jobDescriptorFetcher.get(PROJECT_STUB)).thenReturn(null);
        Resource descriptor = controller.getDescriptor(projectId);
        assertNull(descriptor);
    }

    @Test
    public void should_return_created_jobs() {
        long projectId = 1L;
        when(projectRepository.get(projectId)).thenReturn(PROJECT_STUB);
        when(jobsBuilder.build(any())).thenReturn(singletonList(JOB_STUB));
        List<Job> expected = controller.create(projectId);
        List<Job> actual = controller.get(projectId);
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_empty_list_if_no_jobs() {
        long projectId = 1L;
        when(projectRepository.get(projectId)).thenReturn(PROJECT_STUB);
        List<Job> jobs = controller.get(projectId);
        assertTrue(jobs.isEmpty());
    }
}
