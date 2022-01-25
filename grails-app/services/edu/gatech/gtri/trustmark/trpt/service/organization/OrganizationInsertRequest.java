package edu.gatech.gtri.trustmark.trpt.service.organization;

import java.util.List;

public final class OrganizationInsertRequest {
    private String name;
    private String uri;
    private String description;
    private List<OrganizationTrustInteroperabilityProfileUpsertRequest> organizationTrustInteroperabilityProfileList;
    private List<Long> partnerOrganizationCandidateList;

    OrganizationInsertRequest() {
    }

    public OrganizationInsertRequest(
            final String name,
            final String uri,
            final String description,
            final List<OrganizationTrustInteroperabilityProfileUpsertRequest> organizationTrustInteroperabilityProfileList,
            final List<Long> partnerOrganizationCandidateList) {
        this.name = name;
        this.uri = uri;
        this.description = description;
        this.organizationTrustInteroperabilityProfileList = organizationTrustInteroperabilityProfileList;
        this.partnerOrganizationCandidateList = partnerOrganizationCandidateList;
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

    public List<OrganizationTrustInteroperabilityProfileUpsertRequest> getOrganizationTrustInteroperabilityProfileList() {
        return organizationTrustInteroperabilityProfileList;
    }

    public List<Long> getPartnerOrganizationCandidateList() {
        return partnerOrganizationCandidateList;
    }
}
