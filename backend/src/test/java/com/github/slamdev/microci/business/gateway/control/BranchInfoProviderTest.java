package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.boundary.BuildRepository;
import com.github.slamdev.microci.business.executor.entity.Build;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BranchInfoProviderTest {

    private static final String JOB_NAME = "some-job";

    private static final String BRANCH_NAME = "some-branch";

    @InjectMocks
    private BranchInfoProvider provider;

    @Mock
    private BranchInfoConverter converter;

    @Mock
    private BuildRepository repository;

    @Test
    public void should_return_branch_info_by_builds() {
        Build build = Build.builder().branch(BRANCH_NAME).build();
        when(repository.findAllByJobNameDistinctByBranchOrderByFinishedDate(JOB_NAME)).thenReturn(singletonList(build));
        when(repository.getCountByBranch(BRANCH_NAME)).thenReturn(2L);
        when(repository.findAllByBranchOrderByFinishedDateWithLimit(BRANCH_NAME, 5)).thenReturn(singletonList(build));
        provider.getAll(JOB_NAME, 5);
        verify(converter, times(1)).convert(singletonList(build), 2);
    }
}
