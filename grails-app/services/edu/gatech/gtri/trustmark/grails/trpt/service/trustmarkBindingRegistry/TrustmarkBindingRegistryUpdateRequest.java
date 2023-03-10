package edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry;

public final class TrustmarkBindingRegistryUpdateRequest {

    private long id;
    private String name;
    private String uri;
    private String description;
    private long organization;

    public TrustmarkBindingRegistryUpdateRequest() {
    }

    public TrustmarkBindingRegistryUpdateRequest(
            final long id,
            final String name,
            final String uri,
            final String description,
            final long organization) {

        this.id = id;
        this.name = name;
        this.uri = uri;
        this.description = description;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public long getOrganization() {
        return organization;
    }

    public void setOrganization(final long organization) {
        this.organization = organization;
    }
}
