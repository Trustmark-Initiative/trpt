package edu.gatech.gtri.trustmark.trpt.domain

class ProtectedSystemTrustInteroperabilityProfileUri {

    boolean mandatory

    static belongsTo = [
            protectedSystem: ProtectedSystem,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    static constraints = {
        mandatory nullable: true
    }

    static mapping = {
        table 'protected_system_trust_interoperability_profile_uri'
    }

    long idHelper() { getId() }

    ProtectedSystem protectedSystemHelper() { getProtectedSystem() }

    void protectedSystemHelper(final ProtectedSystem protectedSystem) { setProtectedSystem(protectedSystem) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    ProtectedSystemTrustInteroperabilityProfileUri saveHelper() { save(failOnError: true) }

    ProtectedSystemTrustInteroperabilityProfileUri saveAndFlushHelper() { save(flush: true, failOnError: true) }

}
