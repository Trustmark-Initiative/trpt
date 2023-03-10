package edu.gatech.gtri.trustmark.grails.trpt.service.profile;

import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserResponse;

import java.time.LocalDateTime;

public class ProfileResponse {

    private final UserResponse user;
    private final String applicationVersion;
    private final String gitCommitIdAbbrev;
    private final String gitCommitId;
    private final LocalDateTime gitCommitTime;

    public ProfileResponse(
            final UserResponse user,
            final String applicationVersion,
            final String gitCommitIdAbbrev,
            final String gitCommitId,
            final LocalDateTime gitCommitTime) {
        this.user = user;
        this.applicationVersion = applicationVersion;
        this.gitCommitIdAbbrev = gitCommitIdAbbrev;
        this.gitCommitId = gitCommitId;
        this.gitCommitTime = gitCommitTime;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public String getGitCommitIdAbbrev() {
        return gitCommitIdAbbrev;
    }

    public String getGitCommitId() {
        return gitCommitId;
    }

    public LocalDateTime getGitCommitTime() {
        return gitCommitTime;
    }
}
