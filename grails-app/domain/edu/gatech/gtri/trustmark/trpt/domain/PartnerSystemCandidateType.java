package edu.gatech.gtri.trustmark.trpt.domain;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.arrayList;

public enum PartnerSystemCandidateType {
    SAML_IDP("SAML Identity Provider", "/public/systems/SAML_IDP", "SAML IDP"),
    SAML_SP("SAML Service Provider", "/public/systems/SAML_SP", "SAML SP");

    private final String name;
    private final String uriRelative;
    private final String nameForTrustmarkBindingRegistry;

    PartnerSystemCandidateType(
            final String name,
            final String uriRelative,
            final String nameForTrustmarkBindingRegistry) {

        this.name = name;
        this.uriRelative = uriRelative;
        this.nameForTrustmarkBindingRegistry = nameForTrustmarkBindingRegistry;
    }

    public String getName() {
        return name;
    }

    public String getUriRelative() {
        return uriRelative;
    }

    public String getNameForTrustmarkBindingRegistry() {
        return nameForTrustmarkBindingRegistry;
    }

    public static PartnerSystemCandidateType fromNameForTrustmarkBindingRegistry(final String nameForTrustmarkBindingRegistry) {

        requireNonNull(nameForTrustmarkBindingRegistry);

        return arrayList(PartnerSystemCandidateType.values())
                .find(partnerSystemCandidateType -> partnerSystemCandidateType.getNameForTrustmarkBindingRegistry().equals(nameForTrustmarkBindingRegistry))
                .valueE(format("The system did not recognize '%s'", nameForTrustmarkBindingRegistry));
    }
}
