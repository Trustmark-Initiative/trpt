package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class ProtectedSystemTrustInteroperabilityProfileUri {

    boolean mandatory

    static constraints = {
        mandatory nullable: true
    }

    static mapping = {
        table 'protected_system_trust_interoperability_profile_uri'
    }

    static belongsTo = [
            protectedSystem                : ProtectedSystem,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    ProtectedSystem protectedSystemHelper() { getProtectedSystem() }

    void protectedSystemHelper(final ProtectedSystem protectedSystem) { setProtectedSystem(protectedSystem) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    ProtectedSystemTrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    ProtectedSystemTrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<ProtectedSystemTrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<ProtectedSystemTrustInteroperabilityProfileUri> nil());
    }
}
