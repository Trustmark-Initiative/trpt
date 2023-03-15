package edu.gatech.gtri.trustmark.grails.trpt.domain;

public interface ProtectedEntityPartnerCandidate<T1 extends ProtectedEntity, T2 extends PartnerCandidate> {

    T2 partnerCandidateHelper();
}
