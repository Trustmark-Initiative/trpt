package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryResponse;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class PartnerSystemCandidateResponse {

    private long id;
    private String name;
    private String uri;
    private String uriEntityDescriptor;
    private PartnerSystemCandidateTypeResponse type;
    private LocalDateTime requestLocalDateTime;
    private LocalDateTime successLocalDateTime;
    private LocalDateTime failureLocalDateTime;
    private String failureMessage;
    private TrustmarkBindingRegistryResponse trustmarkBindingRegistry;

    public PartnerSystemCandidateResponse(
            final long id,
            final String name,
            final String uri,
            final String uriEntityDescriptor,
            final PartnerSystemCandidateTypeResponse type,
            final LocalDateTime requestLocalDateTime,
            final LocalDateTime successLocalDateTime,
            final LocalDateTime failureLocalDateTime,
            final String failureMessage,
            final TrustmarkBindingRegistryResponse trustmarkBindingRegistry) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.uriEntityDescriptor = uriEntityDescriptor;
        this.type = type;
        this.requestLocalDateTime = requestLocalDateTime;
        this.successLocalDateTime = successLocalDateTime;
        this.failureLocalDateTime = failureLocalDateTime;
        this.failureMessage = failureMessage;
        this.trustmarkBindingRegistry = trustmarkBindingRegistry;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getUriEntityDescriptor() {
        return uriEntityDescriptor;
    }

    public PartnerSystemCandidateTypeResponse getType() {
        return type;
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

    public TrustmarkBindingRegistryResponse getTrustmarkBindingRegistry() {
        return trustmarkBindingRegistry;
    }
}
