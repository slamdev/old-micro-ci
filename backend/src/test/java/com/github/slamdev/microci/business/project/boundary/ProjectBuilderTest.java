package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import org.junit.Before;
import org.junit.Test;

import static com.github.slamdev.microci.business.project.boundary.ProjectBuilder.SSH_PREFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectBuilderTest {

    private static final String VALID_URL = "git@github.com:slamdev/micro-ci.git";

    private ProjectBuilder builder;

    @Before
    public void setUp() {
        builder = new ProjectBuilder();
    }

    @Test
    public void should_create_project_by_git_url() {
        Project project = builder.build(VALID_URL);
        assertNotNull(project);
    }

    @Test
    public void should_create_project_by_git_ssh_url() {
        Project project = builder.build(SSH_PREFIX + VALID_URL);
        assertNotNull(project);
    }

    @Test(expected = NullPointerException.class)
    public void should_error_if_url_is_null() {
        builder.build(null);
    }

    @Test(expected = NotSshUrlException.class)
    public void should_error_if_url_is_not_ssh() {
        String url = "https://github.com/slamdev/micro-ci.git";
        builder.build(url);
    }

    @Test
    public void should_parse_project_name_owner_and_host_from_ssh_url() {
        Project project = builder.build(VALID_URL);
        assertEquals("micro-ci", project.getName());
        assertEquals("slamdev", project.getOwner());
        assertEquals("github.com", project.getHost());
    }
}
