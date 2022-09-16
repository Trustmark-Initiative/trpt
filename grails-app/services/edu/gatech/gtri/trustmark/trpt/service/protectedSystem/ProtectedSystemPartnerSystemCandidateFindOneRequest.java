package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

public final class ProtectedSystemPartnerSystemCandidateFindOneRequest {

    private long id;
    private long partnerCandidate;

    public ProtectedSystemPartnerSystemCandidateFindOneRequest() {
    }

    public ProtectedSystemPartnerSystemCandidateFindOneRequest(
            final long id,
            final long partnerCandidate) {

        this.id = id;
        this.partnerCandidate = partnerCandidate;
    }

    public long getId() {
        return id;
    }

    public long getPartnerCandidate() {
        return partnerCandidate;
    }
}
