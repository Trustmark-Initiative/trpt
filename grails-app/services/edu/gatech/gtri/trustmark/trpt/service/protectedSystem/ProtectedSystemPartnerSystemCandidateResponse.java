package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateTrustInteroperabilityProfileResponse;

import java.util.List;

public class ProtectedSystemPartnerSystemCandidateResponse {

    private final PartnerSystemCandidateResponse partnerCandidate;
    private final boolean trustable;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> partnerCandidateTrustInteroperabilityProfileList;

    public ProtectedSystemPartnerSystemCandidateResponse(
            final PartnerSystemCandidateResponse partnerCandidate,
            final boolean trustable,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> partnerCandidateTrustInteroperabilityProfileList) {
        this.partnerCandidate = partnerCandidate;
        this.trustable = trustable;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.partnerCandidateTrustInteroperabilityProfileList = partnerCandidateTrustInteroperabilityProfileList;
    }

    public PartnerSystemCandidateResponse getPartnerCandidate() {
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

    public List<PartnerSystemCandidateTrustInteroperabilityProfileResponse> getPartnerCandidateTrustInteroperabilityProfileList() {
        return partnerCandidateTrustInteroperabilityProfileList;
    }
}
