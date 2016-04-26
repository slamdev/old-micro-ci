package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.gateway.entity.BuildInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BuildInfoProviderTest {

    private static final String JOB_NAME = "some-job";

    @InjectMocks
    private BuildInfoProvider provider;

    @Mock
    private BuildRepository repository;

    @Test
    public void should_return_last_build_info() {
        BuildInfo build = provider.getLast(JOB_NAME);
        assertNotNull(build);
    }
}
