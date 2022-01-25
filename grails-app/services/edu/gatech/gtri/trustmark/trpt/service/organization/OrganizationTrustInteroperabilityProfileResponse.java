package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;

public final class OrganizationTrustInteroperabilityProfileResponse {

    private final TrustInteroperabilityProfileResponse trustInteroperabilityProfile;
    private final boolean mandatory;

    public OrganizationTrustInteroperabilityProfileResponse(
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
