package edu.gatech.gtri.trustmark.trpt.domain;

public enum PartnerSystemCandidateType {
    SAML_IDP("SAML Identity Provider", "/public/systems/SAML_IDP"),
    SAML_SP("SAML Service Provider", "/public/systems/SAML_SP");

    private final String name;
    private final String uriRelative;

    PartnerSystemCandidateType(
            final String name,
            final String uriRelative) {

        this.name = name;
        this.uriRelative = uriRelative;
    }

    public String getName() {
        return name;
    }

    public String getUriRelative() {
        return uriRelative;
    }
}
