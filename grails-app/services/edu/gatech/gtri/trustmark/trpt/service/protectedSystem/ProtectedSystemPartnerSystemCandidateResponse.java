package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import org.json.JSONObject;

import java.util.Map;

public class ProtectedSystemPartnerSystemCandidateResponse {

    private final String organizationName;
    private final String protectedSystemName;
    private final String partnerSystemCandidateName;
    private final PartnerSystemCandidateResponse partnerSystemCandidate;
    private final boolean trust;
    private final Integer evaluationTrustmarkDefinitionRequirementSatisfied;
    private final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied;
    private final Integer evaluationTrustExpressionSatisfied;
    private final Integer evaluationTrustExpressionUnsatisfied;
    private final Map<String, JSONObject> trustExpressionEvaluationMap;

    public ProtectedSystemPartnerSystemCandidateResponse(
            final String organizationName,
            final String protectedSystemName,
            final String partnerSystemCandidateName,
            final PartnerSystemCandidateResponse partnerSystemCandidate,
            final boolean trust,
            final Integer evaluationTrustmarkDefinitionRequirementSatisfied,
            final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied,
            final Integer evaluationTrustExpressionSatisfied,
            final Integer evaluationTrustExpressionUnsatisfied,
            final Map<String, JSONObject> trustExpressionEvaluationMap) {
        this.organizationName = organizationName;
        this.protectedSystemName = protectedSystemName;
        this.partnerSystemCandidateName = partnerSystemCandidateName;
        this.partnerSystemCandidate = partnerSystemCandidate;
        this.trust = trust;
        this.evaluationTrustmarkDefinitionRequirementSatisfied = evaluationTrustmarkDefinitionRequirementSatisfied;
        this.evaluationTrustmarkDefinitionRequirementUnsatisfied = evaluationTrustmarkDefinitionRequirementUnsatisfied;
        this.evaluationTrustExpressionSatisfied = evaluationTrustExpressionSatisfied;
        this.evaluationTrustExpressionUnsatisfied = evaluationTrustExpressionUnsatisfied;
        this.trustExpressionEvaluationMap = trustExpressionEvaluationMap;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getProtectedSystemName() {
        return protectedSystemName;
    }

    public String getPartnerSystemCandidateName() {
        return partnerSystemCandidateName;
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

    public Map<String, JSONObject> getTrustExpressionEvaluationMap() {
        return trustExpressionEvaluationMap;
    }
}
