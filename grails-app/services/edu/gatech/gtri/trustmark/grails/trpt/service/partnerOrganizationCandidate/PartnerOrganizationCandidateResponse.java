package edu.gatech.gtri.trustmark.grails.trpt.service.partnerOrganizationCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.PartnerCandidateResponse;

import java.time.LocalDateTime;

public class PartnerOrganizationCandidateResponse implements PartnerCandidateResponse {

    private long id;
    private String identifier;
    private String name;
    private String nameLong;
    private String description;
    private LocalDateTime requestLocalDateTime;
    private LocalDateTime successLocalDateTime;
    private LocalDateTime failureLocalDateTime;
    private String failureMessage;
    private TrustmarkBindingRegistryResponse trustmarkBindingRegistry;

    public PartnerOrganizationCandidateResponse(
            final long id,
            final String identifier,
            final String name,
            final String nameLong,
            final String description,
            final LocalDateTime requestLocalDateTime,
            final LocalDateTime successLocalDateTime,
            final LocalDateTime failureLocalDateTime,
            final String failureMessage,
            final TrustmarkBindingRegistryResponse trustmarkBindingRegistry) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.nameLong = nameLong;
        this.description = description;
        this.requestLocalDateTime = requestLocalDateTime;
        this.successLocalDateTime = successLocalDateTime;
        this.failureLocalDateTime = failureLocalDateTime;
        this.failureMessage = failureMessage;
        this.trustmarkBindingRegistry = trustmarkBindingRegistry;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(final String nameLong) {
        this.nameLong = nameLong;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getRequestLocalDateTime() {
        return requestLocalDateTime;
    }

    public void setRequestLocalDateTime(final LocalDateTime requestLocalDateTime) {
        this.requestLocalDateTime = requestLocalDateTime;
    }

    public LocalDateTime getSuccessLocalDateTime() {
        return successLocalDateTime;
    }

    public void setSuccessLocalDateTime(final LocalDateTime successLocalDateTime) {
        this.successLocalDateTime = successLocalDateTime;
    }

    public LocalDateTime getFailureLocalDateTime() {
        return failureLocalDateTime;
    }

    public void setFailureLocalDateTime(final LocalDateTime failureLocalDateTime) {
        this.failureLocalDateTime = failureLocalDateTime;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(final String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public TrustmarkBindingRegistryResponse getTrustmarkBindingRegistry() {
        return trustmarkBindingRegistry;
    }

    public void setTrustmarkBindingRegistry(final TrustmarkBindingRegistryResponse trustmarkBindingRegistry) {
        this.trustmarkBindingRegistry = trustmarkBindingRegistry;
    }
}
