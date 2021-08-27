package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;

import java.util.List;

public final class ProtectedSystemResponse {

    private final OrganizationResponse organization;
    private final long id;
    private final ProtectedSystemTypeResponse type;
    private final String name;
    private final List<ProtectedSystemTrustInteroperabilityProfileResponse> protectedSystemTrustInteroperabilityProfileList;
    private final List<ProtectedSystemPartnerSystemCandidateSummaryResponse> protectedSystemPartnerSystemCandidateList;

    public ProtectedSystemResponse(
            final OrganizationResponse organization,
            final long id,
            final ProtectedSystemTypeResponse type,
            final String name,
            final List<ProtectedSystemTrustInteroperabilityProfileResponse> protectedSystemTrustInteroperabilityProfileList,
            final List<ProtectedSystemPartnerSystemCandidateSummaryResponse> protectedSystemPartnerSystemCandidateList) {
        this.organization = organization;
        this.id = id;
        this.type = type;
        this.name = name;
        this.protectedSystemTrustInteroperabilityProfileList = protectedSystemTrustInteroperabilityProfileList;
        this.protectedSystemPartnerSystemCandidateList = protectedSystemPartnerSystemCandidateList;
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

    public List<ProtectedSystemTrustInteroperabilityProfileResponse> getProtectedSystemTrustInteroperabilityProfileList() {
        return protectedSystemTrustInteroperabilityProfileList;
    }

    public List<ProtectedSystemPartnerSystemCandidateSummaryResponse> getProtectedSystemPartnerSystemCandidateList() {
        return protectedSystemPartnerSystemCandidateList;
    }
}
