package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import org.gtri.fj.Ord;

import java.util.List;

public class PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation {

    public static final Ord<PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation> partnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluationOrd = Ord.contramap(PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation::getTrustInteroperabilityProfile, TrustInteroperabilityProfileResponse.trustInteroperabilityProfileResponseOrd);

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final List<EvaluationResponseWithTrustExpressionEvaluation> evaluationList;

    public PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final List<EvaluationResponseWithTrustExpressionEvaluation> evaluationList) {
        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.evaluationList = evaluationList;
    }

    public TrustInteroperabilityProfileResponse getTrustInteroperabilityProfile() {
        return trustInteroperabilityProfile;
    }

    public List<EvaluationResponseWithTrustExpressionEvaluation> getEvaluationList() {
        return evaluationList;
    }
}
