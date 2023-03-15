package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

public final class ProtectedSystemFindOneRequest {

    private long id;

    public ProtectedSystemFindOneRequest() {
    }

    public ProtectedSystemFindOneRequest(
            final long id) {

        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }
}
