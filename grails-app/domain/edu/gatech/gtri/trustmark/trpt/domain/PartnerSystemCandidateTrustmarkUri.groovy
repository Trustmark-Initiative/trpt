package edu.gatech.gtri.trustmark.trpt.domain

class PartnerSystemCandidateTrustmarkUri {

    static belongsTo = [
            trustmarkUri: TrustmarkUri,
            partnerSystemCandidate: PartnerSystemCandidate
    ]

    static mapping = {
        table 'partner_system_candidate_trustmark_uri'
    }

    long idHelper() { getId() }

    TrustmarkUri trustmarkUriHelper() { getTrustmarkUri() }

    void trustmarkUriHelper(final TrustmarkUri trustmarkUri) { setTrustmarkUri(trustmarkUri) }

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    PartnerSystemCandidateTrustmarkUri saveHelper() { save(failOnError: true) }

    PartnerSystemCandidateTrustmarkUri saveAndFlushHelper() { save(flush: true, failOnError: true) }

}
