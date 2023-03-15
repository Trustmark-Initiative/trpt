package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemType;

import java.util.List;

public final class ProtectedSystemInsertRequest {

    private long organization;
    private ProtectedSystemType type;
    private String name;
    private List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList;
    private List<Long> partnerCandidateList;

    public ProtectedSystemInsertRequest() {
    }

    public ProtectedSystemInsertRequest(
            final long organization,
            final ProtectedSystemType type,
            final String name,
            final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList,
            final List<Long> partnerCandidateList) {

        this.organization = organization;
        this.type = type;
        this.name = name;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.partnerCandidateList = partnerCandidateList;
    }

    public long getOrganization() {
        return organization;
    }

    public void setOrganization(final long organization) {
        this.organization = organization;
    }

    public ProtectedSystemType getType() {
        return type;
    }

    public void setType(final ProtectedSystemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public void setEntityTrustInteroperabilityProfileList(final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList) {
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
    }

    public List<Long> getPartnerCandidateList() {
        return partnerCandidateList;
    }

    public void setPartnerCandidateList(final List<Long> partnerCandidateList) {
        this.partnerCandidateList = partnerCandidateList;
    }
}
