package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;

import java.util.List;

public final class ProtectedSystemResponse {

    private final OrganizationResponse organization;
    private final long id;
    private final ProtectedSystemTypeResponse type;
    private final String name;
    private final List<ProtectedSystemTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<ProtectedSystemPartnerSystemCandidateResponse> entityPartnerCandidateList;

    public ProtectedSystemResponse(
            final OrganizationResponse organization,
            final long id,
            final ProtectedSystemTypeResponse type,
            final String name,
            final List<ProtectedSystemTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<ProtectedSystemPartnerSystemCandidateResponse> entityPartnerCandidateList) {
        this.organization = organization;
        this.id = id;
        this.type = type;
        this.name = name;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.entityPartnerCandidateList = entityPartnerCandidateList;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }

    public long getId() {
        return id;
    }

    public ProtectedSystemTypeResponse getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<ProtectedSystemTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<ProtectedSystemPartnerSystemCandidateResponse> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
