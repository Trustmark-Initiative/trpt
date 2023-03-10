package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import java.util.List;

public final class OrganizationUpdateRequest {
    private long id;
    private String name;
    private String uri;
    private String description;
    private List<OrganizationTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList;
    private List<Long> partnerCandidateList;

    OrganizationUpdateRequest() {
    }

    public OrganizationUpdateRequest(
            final long id,
            final String name,
            final String uri,
            final String description,
            final List<OrganizationTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList,
            final List<Long> partnerCandidateList) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.description = description;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.partnerCandidateList = partnerCandidateList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public List<OrganizationTrustInteroperabilityProfileUpsertRequest> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<Long> getPartnerCandidateList() {
        return partnerCandidateList;
    }
}
