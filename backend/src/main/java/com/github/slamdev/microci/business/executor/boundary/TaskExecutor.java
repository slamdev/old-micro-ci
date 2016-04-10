package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Task;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TaskExecutor {

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public TaskExecutionResult execute(Task task) {
        try {
            new ProcessExecutor().command("ping", "-n", "1", "127.0.0.1").execute();
        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
        return null;
    }
}
