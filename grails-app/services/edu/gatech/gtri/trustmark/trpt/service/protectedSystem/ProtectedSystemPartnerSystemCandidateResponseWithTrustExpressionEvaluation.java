package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;

import java.time.LocalDateTime;
import java.util.List;

public class ProtectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation {

    private final PartnerSystemCandidateResponse partnerSystemCandidate;
    private final boolean trustable;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerSystemCandidateTrustInteroperabilityProfileList;

    public ProtectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation(
            final PartnerSystemCandidateResponse partnerSystemCandidate,
            final boolean trustable,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerSystemCandidateTrustInteroperabilityProfileList) {
        this.partnerSystemCandidate = partnerSystemCandidate;
        this.trustable = trustable;
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

    public List<PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> getPartnerSystemCandidateTrustInteroperabilityProfileList() {
        return partnerSystemCandidateTrustInteroperabilityProfileList;
    }
}
