package edu.gatech.gtri.trustmark.trpt.service.organization;

import java.util.List;

public final class OrganizationResponseWithDetail {
    private final long id;
    private final String name;
    private final String description;
    private final String uri;
    private final List<OrganizationTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<OrganizationPartnerOrganizationCandidateResponse> entityPartnerCandidateList;

    public OrganizationResponseWithDetail(
            final long id,
            final String name,
            final String description,
            final String uri,
            final List<OrganizationTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<OrganizationPartnerOrganizationCandidateResponse> entityPartnerCandidateList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.entityPartnerCandidateList = entityPartnerCandidateList;
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

    public List<OrganizationTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<OrganizationPartnerOrganizationCandidateResponse> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
