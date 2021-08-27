package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import java.time.LocalDateTime;

public final class ProtectedSystemTrustInteroperabilityProfileResponse {
    private final long id;
    private final String uri;
    private final String name;
    private final String description;
    private final LocalDateTime publicationLocalDateTime;
    private final String issuerName;
    private final String issuerIdentifier;
    private final LocalDateTime requestLocalDateTime;
    private final LocalDateTime successLocalDateTime;
    private final LocalDateTime failureLocalDateTime;
    private final String failureMessage;
    private final boolean mandatory;

    public ProtectedSystemTrustInteroperabilityProfileResponse(
            final long id,
            final String uri,
            final String name,
            final String description,
            final LocalDateTime publicationLocalDateTime,
            final String issuerName,
            final String issuerIdentifier,
            final LocalDateTime requestLocalDateTime,
            final LocalDateTime successLocalDateTime,
            final LocalDateTime failureLocalDateTime,
            final String failureMessage,
            final boolean mandatory) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.publicationLocalDateTime = publicationLocalDateTime;
        this.issuerName = issuerName;
        this.issuerIdentifier = issuerIdentifier;
        this.requestLocalDateTime = requestLocalDateTime;
        this.successLocalDateTime = successLocalDateTime;
        this.failureLocalDateTime = failureLocalDateTime;
        this.failureMessage = failureMessage;
        this.mandatory = mandatory;
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

    public boolean isMandatory() {
        return mandatory;
    }
}
