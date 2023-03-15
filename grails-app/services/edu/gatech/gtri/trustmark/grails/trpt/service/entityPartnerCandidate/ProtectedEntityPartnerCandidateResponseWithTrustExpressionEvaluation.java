package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import java.util.List;

public class ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<T1 extends PartnerCandidateResponse> {

    private final T1 partnerCandidate;
    private final boolean trustable;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerCandidateTrustInteroperabilityProfileList;

    public ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation(
            final T1 partnerCandidate,
            final boolean trustable,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerCandidateTrustInteroperabilityProfileList) {
        this.partnerCandidate = partnerCandidate;
        this.trustable = trustable;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.partnerCandidateTrustInteroperabilityProfileList = partnerCandidateTrustInteroperabilityProfileList;
    }

    public T1 getPartnerCandidate() {
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

    public List<PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> getPartnerCandidateTrustInteroperabilityProfileList() {
        return partnerCandidateTrustInteroperabilityProfileList;
    }
}
