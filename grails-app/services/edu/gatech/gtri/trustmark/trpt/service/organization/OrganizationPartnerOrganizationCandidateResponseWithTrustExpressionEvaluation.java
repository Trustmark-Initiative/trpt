package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;

import java.util.List;

public class OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation {

    private final PartnerOrganizationCandidateResponse partnerCandidate;
    private final boolean trustable;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerCandidateTrustInteroperabilityProfileList;

    public OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateResponse partnerCandidate,
            final boolean trustable,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerCandidateTrustInteroperabilityProfileList) {
        this.partnerCandidate = partnerCandidate;
        this.trustable = trustable;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.partnerCandidateTrustInteroperabilityProfileList = partnerCandidateTrustInteroperabilityProfileList;
    }

    public PartnerOrganizationCandidateResponse getPartnerCandidate() {
        return partnerCandidate;
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

    public List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> getPartnerCandidateTrustInteroperabilityProfileList() {
        return partnerCandidateTrustInteroperabilityProfileList;
    }
}
