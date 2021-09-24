package edu.gatech.gtri.trustmark.trpt.service.organization;

public final class OrganizationInsertRequest {
    private String name;
    private String uri;
    private String description;

    OrganizationInsertRequest() {
    }

    OrganizationInsertRequest(final String name, final String uri, final String description) {
        this.name = name;
        this.uri = uri;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
