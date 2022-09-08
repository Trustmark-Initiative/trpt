package edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate;

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponse;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

public class PartnerOrganizationCandidateTrustInteroperabilityProfileResponse {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final EvaluationResponse evaluation;

    public PartnerOrganizationCandidateTrustInteroperabilityProfileResponse(
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
