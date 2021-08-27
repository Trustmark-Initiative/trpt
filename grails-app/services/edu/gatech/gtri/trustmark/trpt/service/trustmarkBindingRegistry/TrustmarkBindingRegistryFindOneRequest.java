package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

public final class TrustmarkBindingRegistryFindOneRequest {

    private long id;

    public TrustmarkBindingRegistryFindOneRequest() {
    }

    public TrustmarkBindingRegistryFindOneRequest(
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
