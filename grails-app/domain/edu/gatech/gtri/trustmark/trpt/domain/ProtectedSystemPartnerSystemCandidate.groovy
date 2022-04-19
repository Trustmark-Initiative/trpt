package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class ProtectedSystemPartnerSystemCandidate {

    boolean trust

    static constraints = {
        trust nullable: true
    }

    static mapping = {
        table 'protected_system_partner_system_candidate'
    }

    static belongsTo = [
            partnerSystemCandidate: PartnerSystemCandidate,
            protectedSystem       : ProtectedSystem,
    ]

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    ProtectedSystem protectedSystemHelper() { getProtectedSystem() }

    void protectedSystemHelper(final ProtectedSystem protectedSystem) { setProtectedSystem(protectedSystem) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    ProtectedSystemPartnerSystemCandidate saveHelper() {
        save(failOnError: true)
    }

    ProtectedSystemPartnerSystemCandidate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<ProtectedSystemPartnerSystemCandidate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil());
    }

    static Integer executeUpdateHelper(final String query) {
        executeUpdate(query);
    }
}
