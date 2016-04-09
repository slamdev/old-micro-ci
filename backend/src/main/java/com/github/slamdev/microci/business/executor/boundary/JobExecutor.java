package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.ExecutionResult;
import com.github.slamdev.microci.business.job.entity.Job;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class JobExecutor {

    public ExecutionResult execute(Job job) {
        try {
            new ProcessExecutor().command("ping", "-n", "6", "127.0.0.1").execute();
        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
        return null;
    }
}
