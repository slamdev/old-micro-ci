package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
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
    private BuildInfoConverter converter;

    @Test
    public void should_return_last_build_info() {
        Build build = Build.builder().build();
        when(repository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(of(build));
        provider.getLast(JOB_NAME);
        verify(converter, times(1)).convert(build);
    }

    @Test
    public void should_return_null_if_no_last_build() {
        when(repository.findTopByJobNameOrderByFinishedDate(JOB_NAME)).thenReturn(empty());
        BuildInfo info = provider.getLast(JOB_NAME);
        assertNull(info);
    }

    @Test
    public void should_return_all_build_infos() {
        Build build = Build.builder().build();
        when(repository.findAll(JOB_NAME)).thenReturn(singletonList(build));
        provider.getAll(JOB_NAME);
        verify(converter, times(1)).convert(build);
    }

    @Test
    public void should_return_empty_list_if_no_builds() {
        when(repository.findAll(JOB_NAME)).thenReturn(emptyList());
        List<BuildInfo> infos = provider.getAll(JOB_NAME);
        assertEquals(0, infos.size());
    }
}
