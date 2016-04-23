package com.github.slamdev.microci.business.gateway.boundary;

import com.github.slamdev.microci.Application;
import com.github.slamdev.microci.business.gateway.control.BranchInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.BuildInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.JobInfoBuilder;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import com.github.slamdev.microci.business.gateway.entity.CommitInfo;
import com.github.slamdev.microci.business.gateway.entity.JobInfo;
import com.github.slamdev.microci.business.gateway.entity.TaskInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.github.slamdev.microci.business.gateway.entity.Status.SUCCESS;
import static java.time.Instant.ofEpochSecond;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@ActiveProfiles("ApiGatewayTest")
public class ApiGatewayTest {

    private static final JobInfo JOB_STUB = new JobInfo.Builder()
            .setName("my-job")
            .setDurationInMillis(2)
            .setFinishedDate(ofEpochSecond(1))
            .setBuildNumber(3)
            .setStatus(SUCCESS)
            .createJobInfo();

    private static final CommitInfo COMMIT_STUB = new CommitInfo.Builder()
            .setCommitSHA("sha")
            .setAuthorEmail("email")
            .setAuthorName("author")
            .setBranch("branch")
            .setMessage("msg")
            .createCommitInfo();

    private static final TaskInfo TASK_STUB = new TaskInfo.Builder()
            .setAllowFailure(true)
            .setDurationInMillis(1)
            .setFinishedDate(ofEpochSecond(2))
            .setLogId(3)
            .setName("some name")
            .setStatus(SUCCESS)
            .createTaskInfo();

    private static final BuildInfo BUILD_STUB = new BuildInfo.Builder()
            .setCommit(COMMIT_STUB)
            .setJob(JOB_STUB)
            .setStatus(SUCCESS)
            .setTasks(singletonList(TASK_STUB))
            .createBuildInfo();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JobInfoBuilder jobBuilder;

    @Autowired
    private BranchInfoBuilder branchBuilder;

    @Autowired
    private BuildInfoBuilder buildBuilder;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = webAppContextSetup(context).build();
    }

    @Test
    public void should_return_correct_json_when_requesting_all_jobs() throws Exception {
        when(jobBuilder.buildAll()).thenReturn(singletonList(JOB_STUB));
        JSONObject build = new JSONObject()
                .put("name", "my-job")
                .put("durationInMillis", 2)
                .put("finishedDate", 1)
                .put("buildNumber", 3)
                .put("status", "SUCCESS");
        String expected = new JSONArray(singletonList(build)).toString();
        mvc.perform(get("/api/job"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(jobBuilder, times(1)).buildAll();
        verifyNoMoreInteractions(jobBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_last_build() throws Exception {
        String jobName = "some-name";
        when(buildBuilder.buildLast(jobName)).thenReturn(BUILD_STUB);
        String expected = "{}";
        mvc.perform(get("/api/job/{name}/build/last", jobName))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(buildBuilder, times(1)).buildLast(jobName);
        verifyNoMoreInteractions(buildBuilder);
    }

    @Configuration
    @Profile("ApiGatewayTest")
    public static class Config {

        @Bean
        @Primary
        JobInfoBuilder jobInfoBuilder() {
            return mock(JobInfoBuilder.class);
        }

        @Bean
        @Primary
        BranchInfoBuilder branchInfoBuilder() {
            return mock(BranchInfoBuilder.class);
        }

        @Bean
        @Primary
        BuildInfoBuilder buildInfoBuilder() {
            return mock(BuildInfoBuilder.class);
        }
    }
}
