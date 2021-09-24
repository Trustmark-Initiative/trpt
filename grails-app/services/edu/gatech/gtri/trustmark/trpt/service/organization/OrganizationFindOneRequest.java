package edu.gatech.gtri.trustmark.trpt.service.organization;

public final class OrganizationFindOneRequest {
    private long id;

    OrganizationFindOneRequest() {
    }

    OrganizationFindOneRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
