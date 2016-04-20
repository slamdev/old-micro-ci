package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Task;
import com.github.slamdev.microci.business.log.boundary.LogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.FAILED;
import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.SUCCESS;

public class TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    @Autowired
    private ProcessExecutor processExecutor;

    @Autowired
    private LogController logController;

    public TaskExecutionResult execute(Task task) {
        OutputStream stream = new ByteArrayOutputStream();
        try {
            processExecutor.command(task.getCommand()).redirectOutput(stream).exitValueNormal().execute();
        } catch (IOException | InterruptedException | TimeoutException | InvalidExitValueException e) {
            LOGGER.error("", e);
            return new TaskExecutionResult(FAILED);
        }
        logController.write(stream, Paths.get(""));
        return new TaskExecutionResult(SUCCESS);
    }
}
