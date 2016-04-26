package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import org.junit.Test;

import static com.github.slamdev.microci.business.gateway.entity.Status.SUCCESS;
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
        JobInfo job = new JobInfoConverter().convert(build);
        assertEquals(SUCCESS, job.getStatus());
        assertEquals("some-job", job.getName());
        assertEquals(1, job.getBuildNumber());
        assertEquals(10, job.getDurationInMillis());
        assertEquals(ofEpochMilli(11), job.getFinishedDate());
    }
}
