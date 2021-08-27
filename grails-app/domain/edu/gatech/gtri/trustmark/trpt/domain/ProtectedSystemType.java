package edu.gatech.gtri.trustmark.trpt.domain;

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

    public static HashMap<ProtectedSystemType, List<PartnerSystemCandidateType>> getPartnerSystemCandidateTypeMap() {
        return partnerSystemCandidateTypeMap;
    }

    public List<PartnerSystemCandidateType> getPartnerSystemCandidateTypeList() {
        return partnerSystemCandidateTypeMap.get(this).orSome(nil());
    }

    private static final HashMap<ProtectedSystemType, List<PartnerSystemCandidateType>> partnerSystemCandidateTypeMap = HashMap.arrayHashMap(
            p(SAML_IDENTITY_PROVIDER, arrayList(PartnerSystemCandidateType.SAML_SP)),
            p(SAML_SERVICE_PROVIDER, arrayList(PartnerSystemCandidateType.SAML_IDP)),
            p(OPENID_CONNECT_PROVIDER, nil()),
            p(OPENID_CONNECT_CLIENT, nil()),
            p(WEB_SERVICE_PROVIDER, nil()),
            p(WEB_SERVICE_CONSUMER, nil()));
}
