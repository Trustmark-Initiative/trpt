package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;

import java.util.List;

public final class ProtectedSystemInsertRequest {

    private long organization;
    private ProtectedSystemType type;
    private String name;
    private List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList;
    private List<Long> partnerSystemCandidateList;

    public ProtectedSystemInsertRequest() {
    }

    public ProtectedSystemInsertRequest(
            final long organization,
            final ProtectedSystemType type,
            final String name,
            final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList,
            final List<Long> partnerSystemCandidateList) {

        this.organization = organization;
        this.type = type;
        this.name = name;
        this.protectedSystemTrustInteroperabilityProfileList = protectedSystemTrustInteroperabilityProfileList;
        this.partnerSystemCandidateList = partnerSystemCandidateList;
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
