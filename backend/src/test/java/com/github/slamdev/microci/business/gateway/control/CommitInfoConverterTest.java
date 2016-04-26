package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.CommitInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitInfoConverterTest {

    @Test
    public void should_convert_build_to_commit() {
        Build build = Build.builder()
                .authorEmail("some@email.com")
                .authorName("joe")
                .branch("master")
                .commitSHA("123")
                .commitMessage("fix")
                .build();
        CommitInfo info = new CommitInfoConverter().convert(build);
        assertEquals("some@email.com", info.getAuthorEmail());
        assertEquals("joe", info.getAuthorName());
        assertEquals("master", info.getBranch());
        assertEquals("123", info.getCommitSHA());
        assertEquals("fix", info.getMessage());
    }
}