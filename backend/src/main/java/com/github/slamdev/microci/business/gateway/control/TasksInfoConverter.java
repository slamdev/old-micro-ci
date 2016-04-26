package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.gateway.entity.TaskInfo;

import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TasksInfoConverter {

    public List<TaskInfo> convert(Build build) {
        return build.getTaskResults().stream().map(this::convert).collect(toList());
    }

    private TaskInfo convert(TaskExecutionResult task) {
        return TaskInfo.builder()
                .allowFailure(task.isAllowFailure())
                .finishedDate(task.getFinishedDate())
                .durationInMillis(calculateDuration(task))
                .logId(task.getLogId())
                .status(task.getStatus())
                .name(task.getName())
                .build();
    }

    private long calculateDuration(TaskExecutionResult task) {
        return Duration.between(task.getStartedDate(), task.getFinishedDate()).toMillis();
    }
}
