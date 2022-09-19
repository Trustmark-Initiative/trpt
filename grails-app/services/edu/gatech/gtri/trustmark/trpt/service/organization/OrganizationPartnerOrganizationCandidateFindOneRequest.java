package edu.gatech.gtri.trustmark.trpt.service.organization;

public final class OrganizationPartnerOrganizationCandidateFindOneRequest {

    private long id;
    private long partnerCandidate;

    public OrganizationPartnerOrganizationCandidateFindOneRequest() {
    }

    public OrganizationPartnerOrganizationCandidateFindOneRequest(
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
