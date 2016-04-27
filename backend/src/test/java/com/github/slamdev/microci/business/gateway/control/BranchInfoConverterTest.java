package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.BranchInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BranchInfoConverterTest {

    @InjectMocks
    private BranchInfoConverter branchInfoConverter;

    @Mock
    private BuildInfoConverter buildInfoConverter;

    @Test
    public void should_convert_builds_to_branch_info() {
        Build build = Build.builder().branch("some-branch").build();
        BranchInfo info = branchInfoConverter.convert(singletonList(build), 1);
        assertEquals("some-branch", info.getName());
        assertEquals(1, info.getAllBuildsCount());
        assertEquals(1, info.getLastBuilds().size());
        verify(buildInfoConverter, times(1)).convert(build);
    }

    @Test
    public void should_return_null_if_no_builds() {
        BranchInfo info = branchInfoConverter.convert(emptyList(), 0);
        assertNull(info);
    }
}