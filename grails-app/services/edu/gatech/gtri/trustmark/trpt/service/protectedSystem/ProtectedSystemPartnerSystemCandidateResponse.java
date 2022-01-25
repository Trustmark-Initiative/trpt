package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateTrustInteroperabilityProfileResponse;

import java.util.List;

public class ProtectedSystemPartnerSystemCandidateResponse {

    private final PartnerSystemCandidateResponse partnerSystemCandidate;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> partnerSystemCandidateTrustInteroperabilityProfileList;

    public ProtectedSystemPartnerSystemCandidateResponse(
            final PartnerSystemCandidateResponse partnerSystemCandidate,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> partnerSystemCandidateTrustInteroperabilityProfileList) {
        this.partnerSystemCandidate = partnerSystemCandidate;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.partnerSystemCandidateTrustInteroperabilityProfileList = partnerSystemCandidateTrustInteroperabilityProfileList;
    }

    public PartnerSystemCandidateResponse getPartnerSystemCandidate() {
        return partnerSystemCandidate;
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

    public List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> getPartnerSystemCandidateTrustInteroperabilityProfileList() {
        return partnerSystemCandidateTrustInteroperabilityProfileList;
    }
}
