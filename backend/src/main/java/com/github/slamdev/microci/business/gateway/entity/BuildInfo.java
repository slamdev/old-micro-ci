package com.github.slamdev.microci.business.gateway.entity;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BuildInfo {
    public final JobInfo job;
    public final Status status;
    public final CommitInfo commit;
    public final List<TaskInfo> tasks;

    private BuildInfo(JobInfo job, Status status, CommitInfo commit, List<TaskInfo> tasks) {
        this.job = job;
        this.status = status;
        this.commit = commit;
        this.tasks = unmodifiableList(tasks);
    }

    public static class Builder {
        private JobInfo job;
        private Status status;
        private CommitInfo commit;
        private List<TaskInfo> tasks;

        public Builder setJob(JobInfo job) {
            this.job = job;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setCommit(CommitInfo commit) {
            this.commit = commit;
            return this;
        }

        public Builder setTasks(List<TaskInfo> tasks) {
            this.tasks = tasks;
            return this;
        }

        public BuildInfo createBuildInfo() {
            return new BuildInfo(job, status, commit, tasks);
        }
    }
}
