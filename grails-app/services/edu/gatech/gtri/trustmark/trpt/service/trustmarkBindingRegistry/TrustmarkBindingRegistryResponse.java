package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;

import java.time.LocalDateTime;

public final class TrustmarkBindingRegistryResponse {

    private final long id;
    private final String name;
    private final String description;
    private final String uri;
    private final LocalDateTime requestLocalDateTime;
    private final LocalDateTime successLocalDateTime;
    private final LocalDateTime failureLocalDateTime;
    private final String failureMessage;
    private final int partnerSystemCandidateCount;
    private final OrganizationResponse organization;

    public TrustmarkBindingRegistryResponse(
            final long id,
            final String name,
            final String description,
            final String uri,
            final LocalDateTime requestLocalDateTime,
            final LocalDateTime successLocalDateTime,
            final LocalDateTime failureLocalDateTime,
            final String failureMessage,
            final int partnerSystemCandidateCount,
            final OrganizationResponse organization) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.requestLocalDateTime = requestLocalDateTime;
        this.successLocalDateTime = successLocalDateTime;
        this.failureLocalDateTime = failureLocalDateTime;
        this.failureMessage = failureMessage;
        this.partnerSystemCandidateCount = partnerSystemCandidateCount;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public LocalDateTime getRequestLocalDateTime() {
        return requestLocalDateTime;
    }

    public LocalDateTime getSuccessLocalDateTime() {
        return successLocalDateTime;
    }

    public LocalDateTime getFailureLocalDateTime() {
        return failureLocalDateTime;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public int getPartnerSystemCandidateCount() {
        return partnerSystemCandidateCount;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }
}
