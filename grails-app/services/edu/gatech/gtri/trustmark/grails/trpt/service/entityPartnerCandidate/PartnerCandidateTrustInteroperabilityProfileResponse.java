package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import org.gtri.fj.Ord;

public class PartnerCandidateTrustInteroperabilityProfileResponse {

    public static final Ord<PartnerCandidateTrustInteroperabilityProfileResponse> partnerCandidateTrustInteroperabilityProfileResponseOrd = Ord.contramap(PartnerCandidateTrustInteroperabilityProfileResponse::getTrustInteroperabilityProfile, TrustInteroperabilityProfileResponse.trustInteroperabilityProfileResponseOrd);

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final EvaluationResponse evaluation;

    public PartnerCandidateTrustInteroperabilityProfileResponse(
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
