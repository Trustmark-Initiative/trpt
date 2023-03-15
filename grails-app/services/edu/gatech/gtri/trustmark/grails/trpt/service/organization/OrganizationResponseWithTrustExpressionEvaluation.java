package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;

import java.util.List;

public final class OrganizationResponseWithTrustExpressionEvaluation {

    private final OrganizationResponse organization;
    private final long id;
    private final String name;
    private final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerOrganizationCandidateResponse>> entityPartnerCandidateList;

    public OrganizationResponseWithTrustExpressionEvaluation(
            final OrganizationResponse organization,
            final long id,
            final String name,
            final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerOrganizationCandidateResponse>> entityPartnerCandidateList) {
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

    public List<ProtectedEntityTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerOrganizationCandidateResponse>> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
