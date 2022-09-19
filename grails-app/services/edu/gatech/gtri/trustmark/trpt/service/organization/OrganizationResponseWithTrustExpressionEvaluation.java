package edu.gatech.gtri.trustmark.trpt.service.organization;

import java.util.List;

public final class OrganizationResponseWithTrustExpressionEvaluation {

    private final OrganizationResponse organization;
    private final long id;
    private final String name;
    private final List<OrganizationTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> entityPartnerCandidateList;

    public OrganizationResponseWithTrustExpressionEvaluation(
            final OrganizationResponse organization,
            final long id,
            final String name,
            final List<OrganizationTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> entityPartnerCandidateList) {
        this.organization = organization;
        this.id = id;
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

    public String getName() {
        return name;
    }

    public List<OrganizationTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
