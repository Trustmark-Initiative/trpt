package edu.gatech.gtri.trustmark.trpt.domain

class ProtectedSystemPartnerSystemCandidate {

    boolean trust

    static belongsTo = [
            protectedSystem: ProtectedSystem,
            partnerSystemCandidate: PartnerSystemCandidate
    ]

    static constraints = {
        trust nullable: true
    }

    static mapping = {
        table 'protected_system_partner_system_candidate'
    }

    long idHelper() { getId() }

    ProtectedSystem protectedSystemHelper() { getProtectedSystem() }

    void protectedSystemHelper(final ProtectedSystem protectedSystem) { setProtectedSystem(protectedSystem) }

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    ProtectedSystemPartnerSystemCandidate saveHelper() { save(failOnError: true) }

    ProtectedSystemPartnerSystemCandidate saveAndFlushHelper() { save(flush: true, failOnError: true) }

}
