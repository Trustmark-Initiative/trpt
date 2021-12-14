package edu.gatech.gtri.trustmark.trpt.domain;

import java.time.LocalDateTime;

public interface Uri {

    String getUri();

    void setUri(String uri);

    String getHash();

    void setHash(String hash);

    String getDocument();

    void setDocument(String document);

    LocalDateTime getDocumentRequestLocalDateTime();

    void setDocumentRequestLocalDateTime(LocalDateTime requestLocalDateTime);

    LocalDateTime getDocumentSuccessLocalDateTime();

    void setDocumentSuccessLocalDateTime(LocalDateTime successLocalDateTime);

    LocalDateTime getDocumentFailureLocalDateTime();

    void setDocumentFailureLocalDateTime(LocalDateTime failureLocalDateTime);

    LocalDateTime getDocumentChangeLocalDateTime();

    void setDocumentChangeLocalDateTime(LocalDateTime changeLocalDateTime);

    String getDocumentFailureMessage();

    void setDocumentFailureMessage(String failureMessage);

    LocalDateTime getServerRequestLocalDateTime();

    void setServerRequestLocalDateTime(LocalDateTime requestLocalDateTime);

    LocalDateTime getServerSuccessLocalDateTime();

    void setServerSuccessLocalDateTime(LocalDateTime successLocalDateTime);

    LocalDateTime getServerFailureLocalDateTime();

    void setServerFailureLocalDateTime(LocalDateTime failureLocalDateTime);

    LocalDateTime getServerChangeLocalDateTime();

    void setServerChangeLocalDateTime(LocalDateTime changeLocalDateTime);

    String getServerFailureMessage();

    void setServerFailureMessage(String failureMessage);
}
