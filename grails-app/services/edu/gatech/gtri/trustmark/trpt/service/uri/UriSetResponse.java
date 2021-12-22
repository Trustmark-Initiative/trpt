package edu.gatech.gtri.trustmark.trpt.service.uri;

import java.util.List;

public class UriSetResponse {

    private final List<UriResponse> trustInteroperabilityProfileUriList;
    private final List<UriResponse> trustmarkBindingRegistryUriTypeList;
    private final List<UriResponse> trustmarkDefinitionUriList;
    private final List<UriResponse> trustmarkStatusReportUriList;
    private final List<UriResponse> trustmarkUriList;

    public UriSetResponse(
            final List<UriResponse> trustInteroperabilityProfileUriList,
            final List<UriResponse> trustmarkBindingRegistryUriTypeList,
            final List<UriResponse> trustmarkDefinitionUriList,
            final List<UriResponse> trustmarkStatusReportUriList,
            final List<UriResponse> trustmarkUriList) {
        this.trustInteroperabilityProfileUriList = trustInteroperabilityProfileUriList;
        this.trustmarkBindingRegistryUriTypeList = trustmarkBindingRegistryUriTypeList;
        this.trustmarkDefinitionUriList = trustmarkDefinitionUriList;
        this.trustmarkStatusReportUriList = trustmarkStatusReportUriList;
        this.trustmarkUriList = trustmarkUriList;
    }

    public List<UriResponse> getTrustInteroperabilityProfileUriList() {
        return trustInteroperabilityProfileUriList;
    }

    public List<UriResponse> getTrustmarkBindingRegistryUriTypeList() {
        return trustmarkBindingRegistryUriTypeList;
    }

    public List<UriResponse> getTrustmarkDefinitionUriList() {
        return trustmarkDefinitionUriList;
    }

    public List<UriResponse> getTrustmarkStatusReportUriList() {
        return trustmarkStatusReportUriList;
    }

    public List<UriResponse> getTrustmarkUriList() {
        return trustmarkUriList;
    }
}
