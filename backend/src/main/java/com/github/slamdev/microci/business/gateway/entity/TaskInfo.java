package com.github.slamdev.microci.business.gateway.entity;

import java.time.Instant;

public class TaskInfo {
    public final String name;
    public final boolean allowFailure;
    public final Instant finishedDate;
    public final long logId;
    public final long durationInMillis;
    public final Status status;

    TaskInfo(String name, boolean allowFailure, Instant finishedDate, long logId, long durationInMillis, Status status) {
        this.name = name;
        this.allowFailure = allowFailure;
        this.finishedDate = finishedDate;
        this.logId = logId;
        this.durationInMillis = durationInMillis;
        this.status = status;
    }

    public static class Builder {
        private String name;
        private boolean allowFailure;
        private Instant finishedDate;
        private long logId;
        private long durationInMillis;
        private Status status;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAllowFailure(boolean allowFailure) {
            this.allowFailure = allowFailure;
            return this;
        }

        public Builder setFinishedDate(Instant finishedDate) {
            this.finishedDate = finishedDate;
            return this;
        }

        public Builder setLogId(long logId) {
            this.logId = logId;
            return this;
        }

        public Builder setDurationInMillis(long durationInMillis) {
            this.durationInMillis = durationInMillis;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public TaskInfo createTaskInfo() {
            return new TaskInfo(name, allowFailure, finishedDate, logId, durationInMillis, status);
        }
    }
}
