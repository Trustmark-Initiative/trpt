package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import java.time.LocalDateTime;

public class EvaluationResponse {

    private final long id;
    private final LocalDateTime evaluationAttemptLocalDateTime;
    private final LocalDateTime evaluationLocalDateTime;
    private final Boolean evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final boolean evaluationCurrent;

    public EvaluationResponse(
            final long id,
            final LocalDateTime evaluationAttemptLocalDateTime,
            final LocalDateTime evaluationLocalDateTime,
            final Boolean evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final boolean evaluationCurrent) {
        this.id = id;
        this.evaluationAttemptLocalDateTime = evaluationAttemptLocalDateTime;
        this.evaluationLocalDateTime = evaluationLocalDateTime;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationCurrent = evaluationCurrent;
    }

    public long getId() {
        return id;
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

    public boolean isEvaluationCurrent() {
        return evaluationCurrent;
    }
}
