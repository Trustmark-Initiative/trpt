package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class PartnerSystemCandidateTrustmarkUri {

    static mapping = {
        table 'partner_system_candidate_trustmark_uri'
    }

    static belongsTo = [
            trustmarkUri          : TrustmarkUri,
            partnerSystemCandidate: PartnerSystemCandidate
    ]

    TrustmarkUri trustmarkUriHelper() { getTrustmarkUri() }

    void trustmarkUriHelper(final TrustmarkUri trustmarkUri) { setTrustmarkUri(trustmarkUri) }

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerSystemCandidateTrustmarkUri saveHelper() {
        save(failOnError: true)
    }

    PartnerSystemCandidateTrustmarkUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerSystemCandidateTrustmarkUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustmarkUri> nil());
    }

}
