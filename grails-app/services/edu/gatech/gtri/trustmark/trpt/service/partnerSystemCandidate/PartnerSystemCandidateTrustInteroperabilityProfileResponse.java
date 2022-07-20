package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

public class PartnerSystemCandidateTrustInteroperabilityProfileResponse {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final EvaluationResponse evaluation;

    public PartnerSystemCandidateTrustInteroperabilityProfileResponse(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final EvaluationResponse evaluation) {
        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.evaluation = evaluation;
    }

    public TrustInteroperabilityProfileResponse getTrustInteroperabilityProfile() {
        return trustInteroperabilityProfile;
    }

    public EvaluationResponse getEvaluation() {
        return evaluation;
    }
}
