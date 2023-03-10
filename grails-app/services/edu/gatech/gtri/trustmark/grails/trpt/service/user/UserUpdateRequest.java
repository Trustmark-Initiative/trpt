package edu.gatech.gtri.trustmark.grails.trpt.service.user;

public class UserUpdateRequest {

    private long id;
    private long organization;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(
            final long id,
            final long organization) {

        this.id = id;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public long getOrganization() {
        return organization;
    }
}
