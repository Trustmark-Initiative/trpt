package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class PartnerOrganizationCandidateTrustmarkUri {

    static mapping = {
        table 'partner_organization_candidate_trustmark_uri'
    }


    static belongsTo = [
            trustmarkUri                : TrustmarkUri,
            partnerOrganizationCandidate: PartnerOrganizationCandidate
    ]

    TrustmarkUri trustmarkUriHelper() { getTrustmarkUri() }

    void trustmarkUriHelper(final TrustmarkUri trustmarkUri) { setTrustmarkUri(trustmarkUri) }

    PartnerOrganizationCandidate partnerOrganizationCandidateHelper() { getPartnerOrganizationCandidate() }

    void partnerOrganizationCandidateHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate) { setPartnerOrganizationCandidate(partnerOrganizationCandidate) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerOrganizationCandidateTrustmarkUri saveHelper() {
        save(failOnError: true)
    }

    PartnerOrganizationCandidateTrustmarkUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerOrganizationCandidateTrustmarkUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidateTrustmarkUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustmarkUri> nil());
    }

}
