package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BuildInfoConverterTest {

    @InjectMocks
    private BuildInfoConverter buildInfoConverter;

    @Mock
    private JobInfoConverter jobInfoConverter;

    @Mock
    private CommitInfoConverter commitInfoConverter;

    @Mock
    private TasksInfoConverter tasksInfoConverter;

    @Test
    public void should_return_last_build_info() {
        Build build = Build.builder().build();
        BuildInfo info = buildInfoConverter.convert(build);
        assertNotNull(info);
        verify(jobInfoConverter, times(1)).convert(build);
        verify(commitInfoConverter, times(1)).convert(build);
        verify(tasksInfoConverter, times(1)).convert(build);
    }


}