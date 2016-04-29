package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Task;
import com.github.slamdev.microci.business.log.boundary.LogWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import static com.github.slamdev.microci.business.executor.entity.Status.FAILURE;
import static com.github.slamdev.microci.business.executor.entity.Status.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskExecutorTest {

    private static final Task TASK_STUB = Task.builder().build();

    @InjectMocks
    private TaskExecutor executor;

    @Mock
    private ProcessExecutor processExecutor;

    @Mock
    private LogWriter logWriter;

    @Before
    public void setUp() {
        when(processExecutor.command(anyString())).thenReturn(processExecutor);
        when(processExecutor.redirectOutput(any())).thenReturn(processExecutor);
        when(processExecutor.exitValueNormal()).thenReturn(processExecutor);
    }

    @Test
    public void should_return_success_result_if_execution_ok() {
        TaskExecutionResult result = executor.execute(TASK_STUB);
        assertEquals(SUCCESS, result.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void should_return_fail_result_if_execution_not_ok() throws Exception {
        when(processExecutor.execute()).thenThrow(InvalidExitValueException.class);
        TaskExecutionResult result = executor.execute(TASK_STUB);
        assertEquals(FAILURE, result.getStatus());
    }

    @Test
    public void should_write_execution_log() {
        executor.execute(TASK_STUB);
        verify(logWriter, times(1)).write(any(), any());
    }
}
