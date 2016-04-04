package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectControllerTest {

    private static final String VALID_URL = "git@github.com:Nitka/lighthouse.git";

    private ProjectController controller;

    @Before
    public void setUp() {
        controller = new ProjectController();
    }

    @Test
    public void should_create_project_by_git_ssh_url() {
        Project project = controller.create(VALID_URL);
        assertNotNull(project);
    }

    @Test(expected = NotSshUrlException.class)
    public void should_error_if_url_is_not_ssh() {
        String url = "https://github.com/slamdev/micro-ci.git";
        controller.create(url);
    }

    @Test
    public void should_parse_project_name_owner_and_host_from_ssh_url() {
        Project project = controller.create(VALID_URL);
        assertEquals("lighthouse", project.getName());
        assertEquals("Nitka", project.getOwner());
        assertEquals("github.com", project.getHost());
    }

    @Test
    public void should_assign_id_to_created_project() {
        Project project = controller.create(VALID_URL);
        assertNotNull(project.getId());
    }

    @Test
    public void should_return_created_project_by_id() {
        Project expected = controller.create(VALID_URL);
        Project actual = controller.get(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_null_if_no_project_with_passed_id() {
        Project project = controller.get(0L);
        assertNull(project);
    }

    @Test(expected = ProjectAlreadyExistsException.class)
    public void should_error_if_project_already_exists() {
        controller.create(VALID_URL);
        controller.create(VALID_URL);
    }
}
