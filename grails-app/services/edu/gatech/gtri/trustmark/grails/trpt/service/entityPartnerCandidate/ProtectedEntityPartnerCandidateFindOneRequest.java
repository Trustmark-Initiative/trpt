package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

public final class ProtectedEntityPartnerCandidateFindOneRequest {

    private long id;
    private long partnerCandidate;

    public ProtectedEntityPartnerCandidateFindOneRequest() {
    }

    public ProtectedEntityPartnerCandidateFindOneRequest(
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
