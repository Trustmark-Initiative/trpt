package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

public final class ProtectedSystemTrustInteroperabilityProfileUpsertRequest {

    private String uri;
    private boolean mandatory;

    public ProtectedSystemTrustInteroperabilityProfileUpsertRequest() {
    }

    public ProtectedSystemTrustInteroperabilityProfileUpsertRequest(
            final String uri,
            final boolean mandatory) {

        this.uri = uri;
        this.mandatory = mandatory;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(final boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public String toString() {
        return "ProtectedSystemTrustInteroperabilityProfileUpsertRequest{" +
                "uri='" + uri + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }
}


