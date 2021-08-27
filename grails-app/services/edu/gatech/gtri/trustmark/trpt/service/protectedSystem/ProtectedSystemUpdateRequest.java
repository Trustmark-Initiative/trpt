package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;

import java.util.List;

public final class ProtectedSystemUpdateRequest {
    private long id;
    private long organization;
    private String name;
    private ProtectedSystemType type;
    private List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList;
    private List<Long> partnerSystemCandidateList;

    public ProtectedSystemUpdateRequest() {
    }

    public ProtectedSystemUpdateRequest(
            final long id,
            final long organization,
            final String name,
            final ProtectedSystemType type,
            final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList,
            final List<Long> partnerSystemCandidateList) {

        this.id = id;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.protectedSystemTrustInteroperabilityProfileList = protectedSystemTrustInteroperabilityProfileList;
        this.partnerSystemCandidateList = partnerSystemCandidateList;
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

    public List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> getProtectedSystemTrustInteroperabilityProfileList() {
        return protectedSystemTrustInteroperabilityProfileList;
    }

    public void setProtectedSystemTrustInteroperabilityProfileList(final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList) {
        this.protectedSystemTrustInteroperabilityProfileList = protectedSystemTrustInteroperabilityProfileList;
    }

    public List<Long> getPartnerSystemCandidateList() {
        return partnerSystemCandidateList;
    }

    public void setPartnerSystemCandidateList(final List<Long> partnerSystemCandidateList) {
        this.partnerSystemCandidateList = partnerSystemCandidateList;
    }
}
