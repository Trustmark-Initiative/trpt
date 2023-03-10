package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import org.gtri.fj.Ord;

import static org.gtri.fj.lang.StringUtility.stringOrd;

public interface PartnerCandidateResponse {

    Ord<PartnerCandidateResponse> partnerCandidateResponseOrd = Ord.contramap(PartnerCandidateResponse::getName, stringOrd);

    String getName();
}
