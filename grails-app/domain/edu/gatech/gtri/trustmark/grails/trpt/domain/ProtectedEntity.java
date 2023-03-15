package edu.gatech.gtri.trustmark.grails.trpt.domain;

public interface ProtectedEntity {

    org.gtri.fj.data.List<ProtectedEntityPartnerCandidate> protectedEntityPartnerCandidateSetHelper();

    org.gtri.fj.data.List<ProtectedEntityTrustInteroperabilityProfileUri> protectedEntityTrustInteroperabilityProfileUriSetHelper();
}
