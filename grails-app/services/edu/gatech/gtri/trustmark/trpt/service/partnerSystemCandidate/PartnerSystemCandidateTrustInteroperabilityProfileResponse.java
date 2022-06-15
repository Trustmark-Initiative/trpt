package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

import java.time.LocalDateTime;

public class PartnerSystemCandidateTrustInteroperabilityProfileResponse {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final LocalDateTime evaluationAttemptLocalDateTime;
    private final LocalDateTime evaluationLocalDateTime;
    private final Boolean evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;

    public PartnerSystemCandidateTrustInteroperabilityProfileResponse(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final LocalDateTime evaluationAttemptLocalDateTime,
            final LocalDateTime evaluationLocalDateTime,
            final Boolean evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied) {

        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.evaluationAttemptLocalDateTime = evaluationAttemptLocalDateTime;
        this.evaluationLocalDateTime = evaluationLocalDateTime;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
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
}
