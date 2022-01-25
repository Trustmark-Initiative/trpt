package edu.gatech.gtri.trustmark.trpt.service.organization;

import java.util.List;

public final class OrganizationResponseWithDetail {
    private final long id;
    private final String name;
    private final String description;
    private final String uri;
    private final List<OrganizationTrustInteroperabilityProfileResponse> organizationTrustInteroperabilityProfileList;
    private final List<OrganizationPartnerOrganizationCandidateResponse> organizationPartnerOrganizationCandidateList;

    public OrganizationResponseWithDetail(
            final long id,
            final String name,
            final String description,
            final String uri,
            final List<OrganizationTrustInteroperabilityProfileResponse> organizationTrustInteroperabilityProfileList,
            final List<OrganizationPartnerOrganizationCandidateResponse> organizationPartnerOrganizationCandidateList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.organizationTrustInteroperabilityProfileList = organizationTrustInteroperabilityProfileList;
        this.organizationPartnerOrganizationCandidateList = organizationPartnerOrganizationCandidateList;
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

    public List<OrganizationTrustInteroperabilityProfileResponse> getOrganizationTrustInteroperabilityProfileList() {
        return organizationTrustInteroperabilityProfileList;
    }

    public List<OrganizationPartnerOrganizationCandidateResponse> getOrganizationPartnerOrganizationCandidateList() {
        return organizationPartnerOrganizationCandidateList;
    }
}
