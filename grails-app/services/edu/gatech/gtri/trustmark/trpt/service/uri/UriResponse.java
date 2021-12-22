package edu.gatech.gtri.trustmark.trpt.service.uri;

import java.time.LocalDateTime;

public class UriResponse {

    private String uri;
    private LocalDateTime documentRequestLocalDateTime;
    private LocalDateTime documentSuccessLocalDateTime;
    private LocalDateTime documentFailureLocalDateTime;
    private LocalDateTime documentChangeLocalDateTime;
    private String documentFailureMessage;
    private LocalDateTime serverRequestLocalDateTime;
    private LocalDateTime serverSuccessLocalDateTime;
    private LocalDateTime serverFailureLocalDateTime;
    private LocalDateTime serverChangeLocalDateTime;
    private String serverFailureMessage;

    public UriResponse(
            final String uri,
            final LocalDateTime documentRequestLocalDateTime,
            final LocalDateTime documentSuccessLocalDateTime,
            final LocalDateTime documentFailureLocalDateTime,
            final LocalDateTime documentChangeLocalDateTime,
            final String documentFailureMessage,
            final LocalDateTime serverRequestLocalDateTime,
            final LocalDateTime serverSuccessLocalDateTime,
            final LocalDateTime serverFailureLocalDateTime,
            final LocalDateTime serverChangeLocalDateTime,
            final String serverFailureMessage) {

        this.uri = uri;
        this.documentRequestLocalDateTime = documentRequestLocalDateTime;
        this.documentSuccessLocalDateTime = documentSuccessLocalDateTime;
        this.documentFailureLocalDateTime = documentFailureLocalDateTime;
        this.documentChangeLocalDateTime = documentChangeLocalDateTime;
        this.documentFailureMessage = documentFailureMessage;
        this.serverRequestLocalDateTime = serverRequestLocalDateTime;
        this.serverSuccessLocalDateTime = serverSuccessLocalDateTime;
        this.serverFailureLocalDateTime = serverFailureLocalDateTime;
        this.serverChangeLocalDateTime = serverChangeLocalDateTime;
        this.serverFailureMessage = serverFailureMessage;
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

    public LocalDateTime getDocumentChangeLocalDateTime() {
        return documentChangeLocalDateTime;
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

    public LocalDateTime getServerChangeLocalDateTime() {
        return serverChangeLocalDateTime;
    }

    public String getServerFailureMessage() {
        return serverFailureMessage;
    }
}
