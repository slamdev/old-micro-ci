package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Task;
import com.github.slamdev.microci.business.log.boundary.LogWriter;
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

import static com.github.slamdev.microci.business.executor.entity.Status.FAILURE;
import static com.github.slamdev.microci.business.executor.entity.Status.SUCCESS;

public class TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    @Autowired
    private ProcessExecutor processExecutor;

    @Autowired
    private LogWriter logWriter;

    public TaskExecutionResult execute(Task task) {
        OutputStream stream = new ByteArrayOutputStream();
        try {
            processExecutor.command(task.getCommand()).redirectOutput(stream).exitValueNormal().execute();
        } catch (IOException | InterruptedException | TimeoutException | InvalidExitValueException e) {
            LOGGER.error("", e);
            return TaskExecutionResult.builder().status(FAILURE).build();
        }
        logWriter.write(stream, Paths.get(""));
        return TaskExecutionResult.builder().status(SUCCESS).build();
    }
}
