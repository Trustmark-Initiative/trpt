package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponse;

import java.util.List;

public class OrganizationPartnerOrganizationCandidateResponse {

    private final PartnerOrganizationCandidateResponse partnerOrganizationCandidate;
    private final boolean trustable;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponse> partnerOrganizationCandidateTrustInteroperabilityProfileList;

    public OrganizationPartnerOrganizationCandidateResponse(
            final PartnerOrganizationCandidateResponse partnerOrganizationCandidate,
            final boolean trustable,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponse> partnerOrganizationCandidateTrustInteroperabilityProfileList) {
        this.partnerOrganizationCandidate = partnerOrganizationCandidate;
        this.trustable = trustable;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.partnerOrganizationCandidateTrustInteroperabilityProfileList = partnerOrganizationCandidateTrustInteroperabilityProfileList;
    }

    public PartnerOrganizationCandidateResponse getPartnerOrganizationCandidate() {
        return partnerOrganizationCandidate;
    }

    public boolean isTrustable() {
        return trustable;
    }

    public boolean isTrust() {
        return trust;
    }

    public Integer getEvaluationTrustmarkDefinitionRequirementSatisfied() {
        return evaluationTrustmarkDefinitionRequirementSatisfied;
    }

    public Integer getEvaluationTrustmarkDefinitionRequirementUnsatisfied() {
        return evaluationTrustmarkDefinitionRequirementUnsatisfied;
    }

    public Integer getEvaluationTrustExpressionSatisfied() {
        return evaluationTrustExpressionSatisfied;
    }

    public Integer getEvaluationTrustExpressionUnsatisfied() {
        return evaluationTrustExpressionUnsatisfied;
    }

    public List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponse> getPartnerOrganizationCandidateTrustInteroperabilityProfileList() {
        return partnerOrganizationCandidateTrustInteroperabilityProfileList;
    }
}
