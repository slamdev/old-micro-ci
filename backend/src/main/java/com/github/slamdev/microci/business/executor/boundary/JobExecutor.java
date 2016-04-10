package com.github.slamdev.microci.business.executor.boundary;

import com.github.slamdev.microci.business.executor.entity.JobExecutionResult;
import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.job.entity.Job;
import com.github.slamdev.microci.business.job.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.FAILED;
import static com.github.slamdev.microci.business.executor.entity.TaskExecutionResult.Status.SKIPPED;
import static java.util.Objects.requireNonNull;

public class JobExecutor {

    @Autowired
    private TaskExecutor taskExecutor;

    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.AvoidInstantiatingObjectsInLoops"} /* TODO: refactor*/)
    public JobExecutionResult execute(Job job) {
        requireNonNull(job);
        List<TaskExecutionResult> results = new ArrayList<>();
        boolean failed = false;
        for (Task task : job.getTasks()) {
            if (failed) {
                results.add(new TaskExecutionResult(SKIPPED));
            } else {
                TaskExecutionResult result = taskExecutor.execute(task);
                if (result.getStatus() == FAILED) {
                    failed = true;
                }
                results.add(result);
            }
        }
        return new JobExecutionResult(results);
    }
}
