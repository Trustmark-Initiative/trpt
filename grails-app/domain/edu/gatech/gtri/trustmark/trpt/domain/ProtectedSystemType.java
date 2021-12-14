package edu.gatech.gtri.trustmark.trpt.domain;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystemType;
import org.gtri.fj.data.HashMap;
import org.gtri.fj.data.List;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.product.P.p;

public enum ProtectedSystemType {
    SAML_IDENTITY_PROVIDER("SAML Identity Provider"),
    SAML_SERVICE_PROVIDER("SAML Service Provider"),
    OPENID_CONNECT_PROVIDER("OpenID Connect Provider"),
    OPENID_CONNECT_CLIENT("OpenID Connect Client"),
    WEB_SERVICE_PROVIDER("Web Service Provider"),
    WEB_SERVICE_CONSUMER("Web Service Consumer");

    private final String name;

    ProtectedSystemType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HashMap<ProtectedSystemType, List<TrustmarkBindingRegistrySystemType>> getPartnerSystemCandidateTypeMap() {
        return partnerSystemCandidateTypeMap;
    }

    public List<TrustmarkBindingRegistrySystemType> getPartnerSystemCandidateTypeList() {
        return partnerSystemCandidateTypeMap.get(this).orSome(nil());
    }

    private static final HashMap<ProtectedSystemType, List<TrustmarkBindingRegistrySystemType>> partnerSystemCandidateTypeMap = HashMap.arrayHashMap(
            p(SAML_IDENTITY_PROVIDER, arrayList(TrustmarkBindingRegistrySystemType.SAML_SP)),
            p(SAML_SERVICE_PROVIDER, arrayList(TrustmarkBindingRegistrySystemType.SAML_IDP)),
            p(OPENID_CONNECT_PROVIDER, nil()),
            p(OPENID_CONNECT_CLIENT, nil()),
            p(WEB_SERVICE_PROVIDER, nil()),
            p(WEB_SERVICE_CONSUMER, nil()));
}
