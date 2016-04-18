package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.job.boundary.JobController;
import com.github.slamdev.microci.business.job.boundary.JobNotFound;
import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.project.boundary.ProjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExecutorControllerTest {

    private static final Job JOB_STUB = new Job("job-name", emptyList());

    private static final Build BUILD_STUB = new Build(emptyList(), false);

    @InjectMocks
    private ExecutorController controller;

    @Mock
    private JobController jobController;

    @Mock
    private JobExecutor jobExecutor;

    @Mock
    private BuildRepository repository;

    @Test
    public void should_execute_job_by_name() {
        long projectId = 1L;
        when(jobController.get(projectId)).thenReturn(singletonList(JOB_STUB));
        when(jobExecutor.execute(JOB_STUB)).thenReturn(BUILD_STUB);
        Build result = controller.execute(projectId, JOB_STUB.getName());
        verify(repository, times(1)).save(BUILD_STUB);
        assertNotNull(result);
    }

    @Test
    public void should_save_resulting_build() {
        long projectId = 1L;
        when(jobController.get(projectId)).thenReturn(singletonList(JOB_STUB));
        when(jobExecutor.execute(JOB_STUB)).thenReturn(BUILD_STUB);
        Build result = controller.execute(projectId, JOB_STUB.getName());
        assertNotNull(result);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ProjectNotFoundException.class)
    public void should_error_if_project_not_exists() {
        long projectId = 0L;
        when(jobController.get(projectId)).thenThrow(ProjectNotFoundException.class);
        controller.execute(projectId, "job-name");
    }

    @Test(expected = JobNotFound.class)
    public void should_error_is_job_not_exists() {
        controller.execute(1L, "job-name");
    }
}
