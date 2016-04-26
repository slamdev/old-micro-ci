package com.github.slamdev.microci.business.gateway.control;

import com.github.slamdev.microci.business.executor.entity.Build;
import com.github.slamdev.microci.business.gateway.entity.CommitInfo;
import org.springframework.stereotype.Component;

@Component
public class CommitInfoConverter {

    public CommitInfo convert(Build build) {
        return CommitInfo.builder()
                .message(build.getCommitMessage())
                .commitSHA(build.getCommitSHA())
                .branch(build.getBranch())
                .authorEmail(build.getAuthorEmail())
                .authorName(build.getAuthorName())
                .build();
    }
}
