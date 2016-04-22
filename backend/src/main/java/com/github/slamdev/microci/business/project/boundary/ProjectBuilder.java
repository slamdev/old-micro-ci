package com.github.slamdev.microci.business.project.boundary;

import com.github.slamdev.microci.business.project.entity.Project;

import static java.util.Objects.requireNonNull;

public class ProjectBuilder {

    static final String SSH_PREFIX = "ssh://";

    public Project build(String url) {
        requireNonNull(url);
        validateUrl(url);
        return fromUrl(url);
    }

    private Project fromUrl(String url) {
        url = trimSshPrefix(url);
        String[] parts = url.split(":");
        String host = parts[0].split("@")[1];
        String owner = parts[1].split("/")[0];
        String name = parts[1].split("/")[1].replace(".git", "");
        return new Project(1L, host, owner, name);
    }

    private String trimSshPrefix(String url) {
        if (url.startsWith(SSH_PREFIX)) {
            url = url.split(SSH_PREFIX)[1];
        }
        return url;
    }

    private void validateUrl(String url) {
        String sshPrefix = SSH_PREFIX;
        String gitPrefix = "git@";
        if (url.startsWith(sshPrefix + gitPrefix) || url.startsWith(gitPrefix)) {
            return;
        }
        throw new NotSshUrlException(url);
    }
}
