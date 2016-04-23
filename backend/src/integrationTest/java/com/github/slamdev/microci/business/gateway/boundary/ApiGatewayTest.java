package com.github.slamdev.microci.business.gateway.boundary;

import com.github.slamdev.microci.Application;
import com.github.slamdev.microci.business.gateway.control.BranchInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.BuildInfoBuilder;
import com.github.slamdev.microci.business.gateway.control.JobInfoBuilder;
import com.github.slamdev.microci.business.gateway.entity.*;
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

    private static final String STUB_JOB_NAME = "my-job";

    private static final JobInfo JOB_STUB = new JobInfo.Builder()
            .setName("some-job")
            .setDurationInMillis(2)
            .setFinishedDate(ofEpochSecond(1))
            .setBuildNumber(3)
            .setStatus(SUCCESS)
            .createJobInfo();

    private static final JSONObject JOB_JSON = new JSONObject()
            .put("name", "some-job")
            .put("durationInMillis", 2)
            .put("finishedDate", 1)
            .put("buildNumber", 3)
            .put("status", "SUCCESS");

    private static final CommitInfo COMMIT_STUB = new CommitInfo.Builder()
            .setCommitSHA("sha")
            .setAuthorEmail("email")
            .setAuthorName("author")
            .setBranch("branch")
            .setMessage("msg")
            .createCommitInfo();

    private static final JSONObject COMMIT_JSON = new JSONObject()
            .put("commitSHA", "sha")
            .put("authorEmail", "email")
            .put("authorName", "author")
            .put("branch", "branch")
            .put("message", "msg");

    private static final TaskInfo TASK_STUB = new TaskInfo.Builder()
            .setAllowFailure(true)
            .setDurationInMillis(1)
            .setFinishedDate(ofEpochSecond(2))
            .setLogId(3)
            .setName("some name")
            .setStatus(SUCCESS)
            .createTaskInfo();

    private static final JSONObject TASK_JSON = new JSONObject()
            .put("name", "some name")
            .put("allowFailure", true)
            .put("finishedDate", 2)
            .put("logId", 3)
            .put("durationInMillis", 1)
            .put("status", "SUCCESS");

    private static final BuildInfo BUILD_STUB = new BuildInfo.Builder()
            .setCommit(COMMIT_STUB)
            .setJob(JOB_STUB)
            .setStatus(SUCCESS)
            .setTasks(singletonList(TASK_STUB))
            .createBuildInfo();

    private static final JSONObject BUILD_JSON = new JSONObject()
            .put("commit", COMMIT_JSON)
            .put("job", JOB_JSON)
            .put("status", "SUCCESS")
            .put("tasks", new JSONArray(singletonList(TASK_JSON)));


    private static final BranchInfo BRANCH_STUB = new BranchInfo.Builder()
            .setAllBuildsCount(1)
            .setLastBuilds(singletonList(BUILD_STUB))
            .setName("some-name")
            .createBranchInfo();

    private static final JSONObject BRANCH_JSON = new JSONObject()
            .put("allBuildsCount", 1)
            .put("lastBuilds", new JSONArray(singletonList(BUILD_JSON)))
            .put("name", "some-name");

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
        String expected = new JSONArray(singletonList(JOB_JSON)).toString();
        mvc.perform(get("/api/job"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(jobBuilder, times(1)).buildAll();
        verifyNoMoreInteractions(jobBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_last_build() throws Exception {
        when(buildBuilder.buildLast(STUB_JOB_NAME)).thenReturn(BUILD_STUB);
        String expected = BUILD_JSON.toString();
        mvc.perform(get("/api/job/{name}/build/last", STUB_JOB_NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(buildBuilder, times(1)).buildLast(STUB_JOB_NAME);
        verifyNoMoreInteractions(buildBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_builds() throws Exception {
        when(buildBuilder.buildAll(STUB_JOB_NAME)).thenReturn(singletonList(BUILD_STUB));
        String expected = new JSONArray(singletonList(BUILD_JSON)).toString();
        mvc.perform(get("/api/job/{name}/build", STUB_JOB_NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(buildBuilder, times(1)).buildAll(STUB_JOB_NAME);
        verifyNoMoreInteractions(buildBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_branches() throws Exception {
        when(branchBuilder.buildAll(STUB_JOB_NAME)).thenReturn(singletonList(BRANCH_STUB));
        String expected = new JSONArray(singletonList(BRANCH_JSON)).toString();
        mvc.perform(get("/api/job/{name}/branch", STUB_JOB_NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(branchBuilder, times(1)).buildAll(STUB_JOB_NAME);
        verifyNoMoreInteractions(branchBuilder);
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
