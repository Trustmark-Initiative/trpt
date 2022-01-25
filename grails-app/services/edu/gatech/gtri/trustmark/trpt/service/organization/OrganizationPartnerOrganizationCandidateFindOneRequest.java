package edu.gatech.gtri.trustmark.trpt.service.organization;

public final class OrganizationPartnerOrganizationCandidateFindOneRequest {

    private long id;
    private long partnerOrganizationCandidate;

    public OrganizationPartnerOrganizationCandidateFindOneRequest() {
    }

    public OrganizationPartnerOrganizationCandidateFindOneRequest(
            final long id,
            final long partnerOrganizationCandidate) {

        this.id = id;
        this.partnerOrganizationCandidate = partnerOrganizationCandidate;
    }

    public long getId() {
        return id;
    }

    public long getPartnerOrganizationCandidate() {
        return partnerOrganizationCandidate;
    }
}
