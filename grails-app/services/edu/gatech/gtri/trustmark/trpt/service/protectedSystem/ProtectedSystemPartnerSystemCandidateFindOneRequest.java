package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

public final class ProtectedSystemPartnerSystemCandidateFindOneRequest {

    private long id;
    private long partnerSystemCandidate;

    public ProtectedSystemPartnerSystemCandidateFindOneRequest() {
    }

    public ProtectedSystemPartnerSystemCandidateFindOneRequest(
            final long id,
            final long partnerSystemCandidate) {

        this.id = id;
        this.partnerSystemCandidate = partnerSystemCandidate;
    }

    public long getId() {
        return id;
    }

    public long getPartnerSystemCandidate() {
        return partnerSystemCandidate;
    }
}
