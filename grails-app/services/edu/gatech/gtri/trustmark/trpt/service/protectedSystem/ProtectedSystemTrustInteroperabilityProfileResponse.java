package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

public final class ProtectedSystemTrustInteroperabilityProfileResponse {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final boolean mandatory;

    public ProtectedSystemTrustInteroperabilityProfileResponse(
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
