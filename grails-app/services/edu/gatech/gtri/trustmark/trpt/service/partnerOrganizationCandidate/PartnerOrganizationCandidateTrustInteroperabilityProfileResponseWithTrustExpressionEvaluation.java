package edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponse;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

import java.util.List;

public class PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final List<EvaluationResponse> evaluationList;

    public PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final List<EvaluationResponse> evaluationList) {
        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.evaluationList = evaluationList;
    }

    public TrustInteroperabilityProfileResponse getTrustInteroperabilityProfile() {
        return trustInteroperabilityProfile;
    }

    public List<EvaluationResponse> getEvaluationList() {
        return evaluationList;
    }
}
