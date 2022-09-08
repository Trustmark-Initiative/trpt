package edu.gatech.gtri.trustmark.trpt.service.profile;

import edu.gatech.gtri.trustmark.trpt.service.role.RoleResponse;

import java.time.LocalDateTime;

public class ProfileResponse {

    private final String username;
    private final RoleResponse role;
    private final String applicationVersion;
    private final String gitCommitIdAbbrev;
    private final String gitCommitId;
    private final LocalDateTime gitCommitTime;

    public ProfileResponse(final String username, final RoleResponse role, final String applicationVersion, final String gitCommitIdAbbrev, final String gitCommitId, final LocalDateTime gitCommitTime) {
        this.username = username;
        this.role = role;
        this.applicationVersion = applicationVersion;
        this.gitCommitIdAbbrev = gitCommitIdAbbrev;
        this.gitCommitId = gitCommitId;
        this.gitCommitTime = gitCommitTime;
    }

    public String getUsername() {
        return username;
    }

    public RoleResponse getRole() {
        return role;
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
