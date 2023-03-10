package edu.gatech.gtri.trustmark.grails.trpt.service.user;

public class UserInsertRequest {

    private String username;
    private long organization;

    public UserInsertRequest() {
    }

    public UserInsertRequest(
            final String username,
            final long organization) {

        this.username = username;
        this.organization = organization;
    }

    public String getUsername() {
        return username;
    }

    public long getOrganization() {
        return organization;
    }
}
