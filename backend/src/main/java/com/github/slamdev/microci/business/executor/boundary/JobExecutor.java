package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.JobExecutionResult;
import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.FAILED;
import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.SKIPPED;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class JobExecutor {

    @Autowired
    private TaskExecutor taskExecutor;

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis" /* does not take into account lambda */)
    public JobExecutionResult execute(Job job) {
        requireNonNull(job);
        final AtomicBoolean failed = new AtomicBoolean();
        List<TaskExecutionResult> results = job.getTasks().stream().map(task -> {
            if (failed.get()) {
                return new TaskExecutionResult(SKIPPED);
            } else {
                TaskExecutionResult result = taskExecutor.execute(task);
                if (result.getStatus() == FAILED) {
                    failed.set(true);
                }
                return result;
            }
        }).collect(toList());
        return new JobExecutionResult(results, failed.get());
    }
}
