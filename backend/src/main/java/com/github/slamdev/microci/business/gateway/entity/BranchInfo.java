package com.github.slamdev.microci.business.gateway.entity;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BranchInfo {
    public final String name;
    public final long allBuildsCount;
    public final List<BuildInfo> lastBuilds;

    BranchInfo(String name, long allBuildsCount, List<BuildInfo> lastBuilds) {
        this.name = name;
        this.allBuildsCount = allBuildsCount;
        this.lastBuilds = unmodifiableList(lastBuilds);
    }

    public static class Builder {
        private String name;
        private long allBuildsCount;
        private List<BuildInfo> lastBuilds;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAllBuildsCount(long allBuildsCount) {
            this.allBuildsCount = allBuildsCount;
            return this;
        }

        public Builder setLastBuilds(List<BuildInfo> lastBuilds) {
            this.lastBuilds = lastBuilds;
            return this;
        }

        public BranchInfo createBranchInfo() {
            return new BranchInfo(name, allBuildsCount, lastBuilds);
        }
    }
}
