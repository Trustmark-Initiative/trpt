package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import org.gtri.fj.Ord;

public final class ProtectedEntityTrustInteroperabilityProfileResponse {

    public static final Ord<ProtectedEntityTrustInteroperabilityProfileResponse> protectedEntityTrustInteroperabilityProfileResponseOrd = Ord.contramap(ProtectedEntityTrustInteroperabilityProfileResponse::getTrustInteroperabilityProfile, TrustInteroperabilityProfileResponse.trustInteroperabilityProfileResponseOrd);

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final boolean mandatory;

    public ProtectedEntityTrustInteroperabilityProfileResponse(
            final TrustInteroperabilityProfileResponse trustInteroperabilityProfile,
            final boolean mandatory) {
        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
        this.mandatory = mandatory;
    }

    public TrustInteroperabilityProfileResponse getTrustInteroperabilityProfile() {
        return trustInteroperabilityProfile;
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
