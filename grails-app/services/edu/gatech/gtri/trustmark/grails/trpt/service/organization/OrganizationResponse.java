package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

public final class OrganizationResponse {
    private final long id;
    private final String name;
    private final String description;
    private final String uri;

    OrganizationResponse(final long id, final String name, final String description, final String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }
}
