package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;

public class PartnerSystemCandidateFindAllRequest {

    private ProtectedSystemType protectedSystemType;

    public PartnerSystemCandidateFindAllRequest() {
    }

    public PartnerSystemCandidateFindAllRequest(final ProtectedSystemType protectedSystemType) {
        this.protectedSystemType = protectedSystemType;
    }

    public ProtectedSystemType getProtectedSystemType() {
        return protectedSystemType;
    }

    public void setProtectedSystemType(final ProtectedSystemType protectedSystemType) {
        this.protectedSystemType = protectedSystemType;
    }
}
