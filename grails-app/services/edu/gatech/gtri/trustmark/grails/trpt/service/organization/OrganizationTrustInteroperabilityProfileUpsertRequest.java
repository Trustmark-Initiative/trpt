package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

public final class OrganizationTrustInteroperabilityProfileUpsertRequest {

    private String uri;
    private boolean mandatory;

    public OrganizationTrustInteroperabilityProfileUpsertRequest() {
    }

    public OrganizationTrustInteroperabilityProfileUpsertRequest(
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
        return "OrganizationTrustInteroperabilityProfileUpsertRequest{" +
                "uri='" + uri + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }
}


