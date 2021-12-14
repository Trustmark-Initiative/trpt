package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final LocalDateTime evaluationAttemptLocalDateTime;
    private final LocalDateTime evaluationLocalDateTime;
    private final Boolean evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final JSONObject trustExpressionEvaluation;

    public PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final LocalDateTime evaluationAttemptLocalDateTime,
            final LocalDateTime evaluationLocalDateTime,
            final Boolean evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final JSONObject trustExpressionEvaluation) {

        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.evaluationAttemptLocalDateTime = evaluationAttemptLocalDateTime;
        this.evaluationLocalDateTime = evaluationLocalDateTime;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.trustExpressionEvaluation = trustExpressionEvaluation;
    }

    public TrustInteroperabilityProfileResponse getTrustInteroperabilityProfile() {
        return trustInteroperabilityProfile;
    }

    public LocalDateTime getEvaluationAttemptLocalDateTime() {
        return evaluationAttemptLocalDateTime;
    }

    public LocalDateTime getEvaluationLocalDateTime() {
        return evaluationLocalDateTime;
    }

    public Boolean getEvaluationTrustExpressionSatisfied() {
        return evaluationTrustExpressionSatisfied;
    }

    public Integer getEvaluationTrustmarkDefinitionRequirementSatisfied() {
        return evaluationTrustmarkDefinitionRequirementSatisfied;
    }

    public Integer getEvaluationTrustmarkDefinitionRequirementUnsatisfied() {
        return evaluationTrustmarkDefinitionRequirementUnsatisfied;
    }

    public JSONObject getTrustExpressionEvaluation() {
        return trustExpressionEvaluation;
    }
}
