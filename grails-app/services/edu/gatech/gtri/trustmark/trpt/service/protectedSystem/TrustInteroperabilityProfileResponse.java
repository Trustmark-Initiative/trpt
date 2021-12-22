package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import java.time.LocalDateTime;

public final class TrustInteroperabilityProfileResponse {
    private final long id;
    private final String uri;
    private final String name;
    private final String description;
    private final LocalDateTime publicationLocalDateTime;
    private final String issuerName;
    private final String issuerIdentifier;
    private final LocalDateTime documentRequestLocalDateTime;
    private final LocalDateTime documentSuccessLocalDateTime;
    private final LocalDateTime documentFailureLocalDateTime;
    private final String documentFailureMessage;
    private final LocalDateTime serverRequestLocalDateTime;
    private final LocalDateTime serverSuccessLocalDateTime;
    private final LocalDateTime serverFailureLocalDateTime;
    private final String serverFailureMessage;

    public TrustInteroperabilityProfileResponse(
            final long id,
            final String uri,
            final String name,
            final String description,
            final LocalDateTime publicationLocalDateTime,
            final String issuerName,
            final String issuerIdentifier,
            final LocalDateTime documentRequestLocalDateTime,
            final LocalDateTime documentSuccessLocalDateTime,
            final LocalDateTime documentFailureLocalDateTime,
            final String documentFailureMessage,
            final LocalDateTime serverRequestLocalDateTime,
            final LocalDateTime serverSuccessLocalDateTime,
            final LocalDateTime serverFailureLocalDateTime,
            final String serverFailureMessage) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.publicationLocalDateTime = publicationLocalDateTime;
        this.issuerName = issuerName;
        this.issuerIdentifier = issuerIdentifier;
        this.documentRequestLocalDateTime = documentRequestLocalDateTime;
        this.documentSuccessLocalDateTime = documentSuccessLocalDateTime;
        this.documentFailureLocalDateTime = documentFailureLocalDateTime;
        this.documentFailureMessage = documentFailureMessage;
        this.serverRequestLocalDateTime = serverRequestLocalDateTime;
        this.serverSuccessLocalDateTime = serverSuccessLocalDateTime;
        this.serverFailureLocalDateTime = serverFailureLocalDateTime;
        this.serverFailureMessage = serverFailureMessage;
    }

    public long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPublicationLocalDateTime() {
        return publicationLocalDateTime;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getIssuerIdentifier() {
        return issuerIdentifier;
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
}
