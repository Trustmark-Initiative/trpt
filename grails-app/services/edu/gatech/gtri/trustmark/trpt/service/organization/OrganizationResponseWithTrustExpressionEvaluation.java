package edu.gatech.gtri.trustmark.trpt.service.organization;

import java.util.List;

public final class OrganizationResponseWithTrustExpressionEvaluation {

    private final OrganizationResponse organization;
    private final long id;
    private final String name;
    private final List<OrganizationTrustInteroperabilityProfileResponse> organizationTrustInteroperabilityProfileList;
    private final List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> organizationPartnerOrganizationCandidateList;

    public OrganizationResponseWithTrustExpressionEvaluation(
            final OrganizationResponse organization,
            final long id,
            final String name,
            final List<OrganizationTrustInteroperabilityProfileResponse> organizationTrustInteroperabilityProfileList,
            final List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> organizationPartnerOrganizationCandidateList) {
        this.organization = organization;
        this.id = id;
        this.name = name;
        this.organizationTrustInteroperabilityProfileList = organizationTrustInteroperabilityProfileList;
        this.organizationPartnerOrganizationCandidateList = organizationPartnerOrganizationCandidateList;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<OrganizationTrustInteroperabilityProfileResponse> getOrganizationTrustInteroperabilityProfileList() {
        return organizationTrustInteroperabilityProfileList;
    }

    public List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> getOrganizationPartnerOrganizationCandidateList() {
        return organizationPartnerOrganizationCandidateList;
    }
}
