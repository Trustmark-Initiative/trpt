package edu.gatech.gtri.trustmark.grails.trpt.service.uri;

public class UriSetResponse {

    private final UriSetResponsePage trustInteroperabilityProfileUriList;
    private final UriSetResponsePage trustmarkBindingRegistryUriTypeList;
    private final UriSetResponsePage trustmarkDefinitionUriList;
    private final UriSetResponsePage trustmarkStatusReportUriList;
    private final UriSetResponsePage trustmarkUriList;
    private final UriSetResponsePage trustmarkBindingRegistryOrganizationMapUriList;
    private final UriSetResponsePage trustmarkBindingRegistryOrganizationTrustmarkMapUriList;

    public UriSetResponse(
            final UriSetResponsePage trustInteroperabilityProfileUriList,
            final UriSetResponsePage trustmarkBindingRegistryUriTypeList,
            final UriSetResponsePage trustmarkDefinitionUriList,
            final UriSetResponsePage trustmarkStatusReportUriList,
            final UriSetResponsePage trustmarkUriList,
            final UriSetResponsePage trustmarkBindingRegistryOrganizationMapUriList,
            final UriSetResponsePage trustmarkBindingRegistryOrganizationTrustmarkMapUriList) {
        this.trustInteroperabilityProfileUriList = trustInteroperabilityProfileUriList;
        this.trustmarkBindingRegistryUriTypeList = trustmarkBindingRegistryUriTypeList;
        this.trustmarkDefinitionUriList = trustmarkDefinitionUriList;
        this.trustmarkStatusReportUriList = trustmarkStatusReportUriList;
        this.trustmarkUriList = trustmarkUriList;
        this.trustmarkBindingRegistryOrganizationMapUriList = trustmarkBindingRegistryOrganizationMapUriList;
        this.trustmarkBindingRegistryOrganizationTrustmarkMapUriList = trustmarkBindingRegistryOrganizationTrustmarkMapUriList;
    }

    public UriSetResponsePage getTrustInteroperabilityProfileUriList() {
        return trustInteroperabilityProfileUriList;
    }

    public UriSetResponsePage getTrustmarkBindingRegistryUriTypeList() {
        return trustmarkBindingRegistryUriTypeList;
    }

    public UriSetResponsePage getTrustmarkDefinitionUriList() {
        return trustmarkDefinitionUriList;
    }

    public UriSetResponsePage getTrustmarkStatusReportUriList() {
        return trustmarkStatusReportUriList;
    }

    public UriSetResponsePage getTrustmarkUriList() {
        return trustmarkUriList;
    }

    public UriSetResponsePage getTrustmarkBindingRegistryOrganizationMapUriList() {
        return trustmarkBindingRegistryOrganizationMapUriList;
    }

    public UriSetResponsePage getTrustmarkBindingRegistryOrganizationTrustmarkMapUriList() {
        return trustmarkBindingRegistryOrganizationTrustmarkMapUriList;
    }
}
