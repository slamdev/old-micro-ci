package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

    private static final Project PROJECT_STUB = new Project(1L, "host", "owner", "name");

    private static final String URL_STUB = "git@github.com:slamdev/micro-ci.git";

    @InjectMocks
    private ProjectController controller;

    @Mock
    private ProjectBuilder builder;

    @Mock
    private ProjectRepository repository;

    @Test
    public void should_build_project_by_url() {
        controller.create(URL_STUB);
        verify(builder, times(1)).build(URL_STUB);
    }

    @Test
    public void should_save_project_to_repository() {
        when(builder.build(URL_STUB)).thenReturn(PROJECT_STUB);
        controller.create(URL_STUB);
        verify(repository, times(1)).save(PROJECT_STUB);
    }

    @Test(expected = ProjectAlreadyExistsException.class)
    public void should_error_if_project_exists() {
        when(repository.contains(any())).thenReturn(true);
        controller.create(URL_STUB);
    }
}
