package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.project.entity.Project;
import com.github.slamdev.microci.business.repository.boundary.RepositoryFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.security.util.InMemoryResource;

import static com.github.slamdev.microci.business.job.boundary.JobDescriptorFetcher.DESCRIPTOR_NAME;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobDescriptorFetcherTest {

    private static final Project PROJECT_STUB = new Project(1L, "host", "owner", "name");

    private static final Resource RESOURCE_STUB = new InMemoryResource("");

    @Mock
    private RepositoryFetcher repositoryFetcher;

    @InjectMocks
    private JobDescriptorFetcher fetcher;

    @Test
    public void should_return_descriptor() {
        when(repositoryFetcher.getResource(PROJECT_STUB, DESCRIPTOR_NAME)).thenReturn(RESOURCE_STUB);
        Resource descriptor = fetcher.get(PROJECT_STUB);
        assertNotNull(descriptor);
    }

    @Test(expected = NullPointerException.class)
    public void should_error_if_project_null() {
        fetcher.get(null);
    }

    @Test
    public void should_return_null_if_no_descriptor() {
        when(repositoryFetcher.getResource(PROJECT_STUB, eq(anyString()))).thenReturn(null);
        Resource descriptor = fetcher.get(PROJECT_STUB);
        assertNull(descriptor);
    }
}
