package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.FAILED;
import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.SUCCESS;

public class TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    @Autowired
    private ProcessExecutor processExecutor;

    public TaskExecutionResult execute(Task task) {
        try {
            processExecutor.command(task.getCommand()).exitValueNormal().execute();
        } catch (IOException | InterruptedException | TimeoutException | InvalidExitValueException e) {
            LOGGER.error("", e);
            return new TaskExecutionResult(FAILED);
        }
        return new TaskExecutionResult(SUCCESS);
    }
}
