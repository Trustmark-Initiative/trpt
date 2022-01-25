package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;

import java.util.List;

public class OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation {

    private final PartnerOrganizationCandidateResponse partnerOrganizationCandidate;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerOrganizationCandidateTrustInteroperabilityProfileList;

    public OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateResponse partnerOrganizationCandidate,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerOrganizationCandidateTrustInteroperabilityProfileList) {
        this.partnerOrganizationCandidate = partnerOrganizationCandidate;
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

    public List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> getPartnerOrganizationCandidateTrustInteroperabilityProfileList() {
        return partnerOrganizationCandidateTrustInteroperabilityProfileList;
    }
}
