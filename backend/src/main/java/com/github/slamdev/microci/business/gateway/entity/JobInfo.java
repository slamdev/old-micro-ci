package com.github.slamdev.microci.business.gateway.entity;

import java.time.Instant;

public class JobInfo {
    public final String name;
    public final long durationInMillis;
    public final Instant finishedDate;
    public final int buildNumber;
    public final Status status;

    private JobInfo(String name, long durationInMillis, Instant finishedDate, int buildNumber, Status status) {
        this.name = name;
        this.durationInMillis = durationInMillis;
        this.finishedDate = finishedDate;
        this.buildNumber = buildNumber;
        this.status = status;
    }

    public static class Builder {
        private String name;
        private long durationInMillis;
        private Instant finishedDate;
        private int buildNumber;
        private Status status;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDurationInMillis(long durationInMillis) {
            this.durationInMillis = durationInMillis;
            return this;
        }

        public Builder setFinishedDate(Instant finishedDate) {
            this.finishedDate = finishedDate;
            return this;
        }

        public Builder setBuildNumber(int buildNumber) {
            this.buildNumber = buildNumber;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public JobInfo createJobInfo() {
            return new JobInfo(name, durationInMillis, finishedDate, buildNumber, status);
        }
    }
}
