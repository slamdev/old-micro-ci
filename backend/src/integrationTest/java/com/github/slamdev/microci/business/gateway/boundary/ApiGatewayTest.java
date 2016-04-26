package com.github.slamdev.microci.business.gateway.boundary;

import com.github.slamdev.microci.Application;
import com.github.slamdev.microci.business.gateway.control.BranchInfoProvider;
import com.github.slamdev.microci.business.gateway.control.BuildInfoProvider;
import com.github.slamdev.microci.business.gateway.control.JobInfoProvider;
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

    private static final JobInfo JOB_STUB = JobInfo.builder()
            .name("some-job")
            .durationInMillis(2)
            .finishedDate(ofEpochSecond(1))
            .buildNumber(3)
            .status(SUCCESS)
            .build();

    private static final JSONObject JOB_JSON = new JSONObject()
            .put("name", "some-job")
            .put("durationInMillis", 2)
            .put("finishedDate", 1)
            .put("buildNumber", 3)
            .put("status", "SUCCESS");

    private static final CommitInfo COMMIT_STUB = CommitInfo.builder()
            .commitSHA("sha")
            .authorEmail("email")
            .authorName("author")
            .branch("branch")
            .message("msg")
            .build();

    private static final JSONObject COMMIT_JSON = new JSONObject()
            .put("commitSHA", "sha")
            .put("authorEmail", "email")
            .put("authorName", "author")
            .put("branch", "branch")
            .put("message", "msg");

    private static final TaskInfo TASK_STUB = TaskInfo.builder()
            .allowFailure(true)
            .durationInMillis(1)
            .finishedDate(ofEpochSecond(2))
            .logId(3)
            .name("some name")
            .status(SUCCESS)
            .build();

    private static final JSONObject TASK_JSON = new JSONObject()
            .put("name", "some name")
            .put("allowFailure", true)
            .put("finishedDate", 2)
            .put("logId", 3)
            .put("durationInMillis", 1)
            .put("status", "SUCCESS");

    private static final BuildInfo BUILD_STUB = BuildInfo.builder()
            .commit(COMMIT_STUB)
            .job(JOB_STUB)
            .status(SUCCESS)
            .tasks(singletonList(TASK_STUB))
            .build();

    private static final JSONObject BUILD_JSON = new JSONObject()
            .put("commit", COMMIT_JSON)
            .put("job", JOB_JSON)
            .put("status", "SUCCESS")
            .put("tasks", new JSONArray(singletonList(TASK_JSON)));

    private static final BranchInfo BRANCH_STUB = BranchInfo.builder()
            .allBuildsCount(1)
            .lastBuilds(singletonList(BUILD_STUB))
            .name("some-name")
            .build();

    private static final JSONObject BRANCH_JSON = new JSONObject()
            .put("allBuildsCount", 1)
            .put("lastBuilds", new JSONArray(singletonList(BUILD_JSON)))
            .put("name", "some-name");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JobInfoProvider jobBuilder;

    @Autowired
    private BranchInfoProvider branchBuilder;

    @Autowired
    private BuildInfoProvider buildBuilder;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = webAppContextSetup(context).build();
    }

    @Test
    public void should_return_correct_json_when_requesting_all_jobs() throws Exception {
        when(jobBuilder.get()).thenReturn(singletonList(JOB_STUB));
        String expected = new JSONArray(singletonList(JOB_JSON)).toString();
        mvc.perform(get("/api/job"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(jobBuilder, times(1)).get();
        verifyNoMoreInteractions(jobBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_last_build() throws Exception {
        when(buildBuilder.getLast(STUB_JOB_NAME)).thenReturn(BUILD_STUB);
        String expected = BUILD_JSON.toString();
        mvc.perform(get("/api/job/{name}/build/last", STUB_JOB_NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(buildBuilder, times(1)).getLast(STUB_JOB_NAME);
        verifyNoMoreInteractions(buildBuilder);
    }

    @Test
    public void should_return_correct_json_when_requesting_builds() throws Exception {
        when(buildBuilder.getAll(STUB_JOB_NAME)).thenReturn(singletonList(BUILD_STUB));
        String expected = new JSONArray(singletonList(BUILD_JSON)).toString();
        mvc.perform(get("/api/job/{name}/build", STUB_JOB_NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(buildBuilder, times(1)).getAll(STUB_JOB_NAME);
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
        JobInfoProvider jobInfoBuilder() {
            return mock(JobInfoProvider.class);
        }

        @Bean
        @Primary
        BranchInfoProvider branchInfoBuilder() {
            return mock(BranchInfoProvider.class);
        }

        @Bean
        @Primary
        BuildInfoProvider buildInfoBuilder() {
            return mock(BuildInfoProvider.class);
        }
    }
}
