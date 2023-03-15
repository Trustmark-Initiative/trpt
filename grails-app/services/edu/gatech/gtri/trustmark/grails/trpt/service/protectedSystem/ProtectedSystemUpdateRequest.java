package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemType;

import java.util.List;

public final class ProtectedSystemUpdateRequest {

    private long id;
    private long organization;
    private String name;
    private ProtectedSystemType type;
    private List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList;
    private List<Long> partnerCandidateList;

    public ProtectedSystemUpdateRequest() {
    }

    public ProtectedSystemUpdateRequest(
            final long id,
            final long organization,
            final String name,
            final ProtectedSystemType type,
            final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> entityTrustInteroperabilityProfileList,
            final List<Long> partnerCandidateList) {

        this.id = id;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.partnerCandidateList = partnerCandidateList;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getOrganization() {
        return organization;
    }

    public void setOrganization(final long organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ProtectedSystemType getType() {
        return type;
    }

    public void setType(final ProtectedSystemType type) {
        this.type = type;
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
