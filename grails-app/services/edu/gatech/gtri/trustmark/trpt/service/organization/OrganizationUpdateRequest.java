package edu.gatech.gtri.trustmark.trpt.service.organization;

public final class OrganizationUpdateRequest {
    private long id;
    private String name;
    private String uri;
    private String description;

    OrganizationUpdateRequest() {
    }

    OrganizationUpdateRequest(final long id, final String name, final String uri, final String description) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
