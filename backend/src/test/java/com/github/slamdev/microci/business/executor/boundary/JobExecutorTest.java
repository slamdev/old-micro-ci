package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.job.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobExecutorTest {

    private static final Job JOB_STUB = new Job("job-name", asList(new Task(""), new Task("")));

    private static final TaskExecutionResult SUCCESS_STUB = new TaskExecutionResult(SUCCESS);

    private static final TaskExecutionResult FAILED_STUB = new TaskExecutionResult(FAILED);

    @InjectMocks
    private JobExecutor executor;

    @Mock
    private TaskExecutor taskExecutor;

    @Test
    public void should_execute_all_tasks_in_job() {
        when(taskExecutor.execute(any())).thenReturn(SUCCESS_STUB);
        Build result = executor.execute(JOB_STUB);
        result.getTaskResults().forEach(task -> assertEquals(SUCCESS, task.getStatus()));
        assertFalse(result.isJobFailed());
    }

    @Test
    public void should_not_execute_descending_tasks_if_one_failed() {
        when(taskExecutor.execute(any())).thenReturn(FAILED_STUB);
        Build result = executor.execute(JOB_STUB);
        assertEquals(FAILED, result.getTaskResults().get(0).getStatus());
        result.getTaskResults().stream().skip(1).forEach(task -> assertEquals(SKIPPED, task.getStatus()));
        assertTrue(result.isJobFailed());
    }

    @Test(expected = NullPointerException.class)
    public void should_error_if_job_is_null() {
        executor.execute(null);
    }
}
