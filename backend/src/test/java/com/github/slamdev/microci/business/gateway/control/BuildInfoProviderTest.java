package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuildInfoProviderTest {

    private static final String JOB_NAME = "some-job";

    @InjectMocks
    private BuildInfoProvider provider;

    @Mock
    private BuildRepository repository;

    @Mock
    private JobInfoConverter jobInfoConverter;

    @Mock
    private CommitInfoConverter commitInfoConverter;

    @Mock
    private TasksInfoConverter tasksInfoConverter;

    @Test
    public void should_return_last_build_info() {
        Build build = Build.builder().build();
        when(repository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(build);
        BuildInfo info = provider.getLast(JOB_NAME);
        assertNotNull(info);
        verify(jobInfoConverter, times(1)).convert(build);
        verify(commitInfoConverter, times(1)).convert(build);
        verify(tasksInfoConverter, times(1)).convert(build);
    }

    @Test
    public void should_return_null_if_no_last_build() {
        when(repository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(null);
        BuildInfo info = provider.getLast(JOB_NAME);
        assertNull(info);
    }
}
