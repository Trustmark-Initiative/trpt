package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;

import java.time.LocalDateTime;

public final class TrustmarkBindingRegistryResponse {

    private final long id;
    private final String name;
    private final String description;
    private final String uri;
    private final LocalDateTime documentRequestLocalDateTime;
    private final LocalDateTime documentSuccessLocalDateTime;
    private final LocalDateTime documentFailureLocalDateTime;
    private final String documentFailureMessage;
    private final LocalDateTime serverRequestLocalDateTime;
    private final LocalDateTime serverSuccessLocalDateTime;
    private final LocalDateTime serverFailureLocalDateTime;
    private final String serverFailureMessage;
    private final int partnerSystemCandidateCount;
    private final OrganizationResponse organization;

    public TrustmarkBindingRegistryResponse(
            final long id,
            final String name,
            final String description,
            final String uri,
            final LocalDateTime documentRequestLocalDateTime,
            final LocalDateTime documentSuccessLocalDateTime,
            final LocalDateTime documentFailureLocalDateTime,
            final String documentFailureMessage,
            final LocalDateTime serverRequestLocalDateTime,
            final LocalDateTime serverSuccessLocalDateTime,
            final LocalDateTime serverFailureLocalDateTime,
            final String serverFailureMessage,
            final int partnerSystemCandidateCount,
            final OrganizationResponse organization) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.documentRequestLocalDateTime = documentRequestLocalDateTime;
        this.documentSuccessLocalDateTime = documentSuccessLocalDateTime;
        this.documentFailureLocalDateTime = documentFailureLocalDateTime;
        this.documentFailureMessage = documentFailureMessage;
        this.serverRequestLocalDateTime = serverRequestLocalDateTime;
        this.serverSuccessLocalDateTime = serverSuccessLocalDateTime;
        this.serverFailureLocalDateTime = serverFailureLocalDateTime;
        this.serverFailureMessage = serverFailureMessage;
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

    public LocalDateTime getDocumentRequestLocalDateTime() {
        return documentRequestLocalDateTime;
    }

    public LocalDateTime getDocumentSuccessLocalDateTime() {
        return documentSuccessLocalDateTime;
    }

    public LocalDateTime getDocumentFailureLocalDateTime() {
        return documentFailureLocalDateTime;
    }

    public String getDocumentFailureMessage() {
        return documentFailureMessage;
    }

    public LocalDateTime getServerRequestLocalDateTime() {
        return serverRequestLocalDateTime;
    }

    public LocalDateTime getServerSuccessLocalDateTime() {
        return serverSuccessLocalDateTime;
    }

    public LocalDateTime getServerFailureLocalDateTime() {
        return serverFailureLocalDateTime;
    }

    public String getServerFailureMessage() {
        return serverFailureMessage;
    }

    public int getPartnerSystemCandidateCount() {
        return partnerSystemCandidateCount;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }
}
