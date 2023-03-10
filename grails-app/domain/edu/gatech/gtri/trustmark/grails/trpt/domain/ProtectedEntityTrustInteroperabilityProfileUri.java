package edu.gatech.gtri.trustmark.grails.trpt.domain;

public interface ProtectedEntityTrustInteroperabilityProfileUri<T1 extends ProtectedEntity> {

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper();

    boolean isMandatory();
}
