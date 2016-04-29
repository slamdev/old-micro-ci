package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.executor.entity.TaskExecutionResult;
import com.github.slamdev.microci.business.gateway.entity.TaskInfo;
import org.junit.Test;

import java.util.List;

import static com.github.slamdev.microci.business.executor.entity.Status.SUCCESS;
import static java.time.Instant.ofEpochMilli;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class TasksInfoConverterTest {

    @Test
    public void should_convert_build_to_tasks() {
        TaskExecutionResult task = TaskExecutionResult.builder()
                .status(SUCCESS)
                .logId(1)
                .allowFailure(true)
                .name("some-name")
                .startedDate(ofEpochMilli(1))
                .finishedDate(ofEpochMilli(11))
                .build();
        Build build = Build.builder().taskResults(singletonList(task)).build();
        List<TaskInfo> infos = new TasksInfoConverter().convert(build);
        assertEquals(1, infos.size());
        assertEquals(10, infos.get(0).getDurationInMillis());
        assertEquals(ofEpochMilli(11), infos.get(0).getFinishedDate());
        assertEquals(1, infos.get(0).getLogId());
        assertEquals("some-name", infos.get(0).getName());
        assertEquals(SUCCESS, infos.get(0).getStatus());
    }
}
