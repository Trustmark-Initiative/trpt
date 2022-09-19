package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class EvaluationResponseWithTrustExpressionEvaluation {

    private final long id;
    private final LocalDateTime evaluationAttemptLocalDateTime;
    private final LocalDateTime evaluationLocalDateTime;
    private final Boolean evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final boolean evaluationCurrent;
    private final JSONObject evaluation;

    public EvaluationResponseWithTrustExpressionEvaluation(
            final long id,
            final LocalDateTime evaluationAttemptLocalDateTime,
            final LocalDateTime evaluationLocalDateTime,
            final Boolean evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final boolean evaluationCurrent,
            final JSONObject evaluation) {
        this.id = id;
        this.evaluationAttemptLocalDateTime = evaluationAttemptLocalDateTime;
        this.evaluationLocalDateTime = evaluationLocalDateTime;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationCurrent = evaluationCurrent;
        this.evaluation = evaluation;
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

    public JSONObject getEvaluation() {
        return evaluation;
    }
}
