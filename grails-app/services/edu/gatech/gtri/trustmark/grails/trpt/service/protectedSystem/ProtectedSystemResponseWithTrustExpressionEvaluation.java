package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationResponse;

import java.util.List;

public final class ProtectedSystemResponseWithTrustExpressionEvaluation {

    private final OrganizationResponse organization;
    private final long id;
    private final ProtectedSystemTypeResponse type;
    private final String name;
    private final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerSystemCandidateResponse>> entityPartnerCandidateList;

    public ProtectedSystemResponseWithTrustExpressionEvaluation(
            final OrganizationResponse organization,
            final long id,
            final ProtectedSystemTypeResponse type,
            final String name,
            final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerSystemCandidateResponse>> entityPartnerCandidateList) {
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

    public List<ProtectedEntityTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<PartnerSystemCandidateResponse>> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
