package com.github.slamdev.microci.business.gateway.entity;

public class CommitInfo {
    public final String commitSHA;
    public final String authorEmail;
    public final String authorName;
    public final String branch;
    public final String message;

    private CommitInfo(String commitSHA, String authorEmail, String authorName, String branch, String message) {
        this.commitSHA = commitSHA;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.branch = branch;
        this.message = message;
    }

    public static class Builder {
        private String commitSHA;
        private String authorEmail;
        private String authorName;
        private String branch;
        private String message;

        public Builder setCommitSHA(String commitSHA) {
            this.commitSHA = commitSHA;
            return this;
        }

        public Builder setAuthorEmail(String authorEmail) {
            this.authorEmail = authorEmail;
            return this;
        }

        public Builder setAuthorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder setBranch(String branch) {
            this.branch = branch;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public CommitInfo createCommitInfo() {
            return new CommitInfo(commitSHA, authorEmail, authorName, branch, message);
        }
    }
}
