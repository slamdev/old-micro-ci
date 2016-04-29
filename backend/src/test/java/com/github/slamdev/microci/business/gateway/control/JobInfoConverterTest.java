package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.junit.Test;

import static com.github.slamdev.microci.business.executor.entity.Status.SUCCESS;
import static java.time.Instant.ofEpochMilli;
import static org.junit.Assert.assertEquals;

public class JobInfoConverterTest {

    @Test
    public void should_convert_build_to_job() {
        Build build = Build.builder()
                .status(SUCCESS)
                .number(1)
                .startedDate(ofEpochMilli(1))
                .finishedDate(ofEpochMilli(11))
                .jobName("some-job")
                .build();
        JobInfo info = new JobInfoConverter().convert(build);
        assertEquals(SUCCESS, info.getStatus());
        assertEquals("some-job", info.getName());
        assertEquals(1, info.getBuildNumber());
        assertEquals(10, info.getDurationInMillis());
        assertEquals(ofEpochMilli(11), info.getFinishedDate());
    }
}
