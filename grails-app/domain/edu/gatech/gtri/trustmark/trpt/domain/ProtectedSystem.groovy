package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class ProtectedSystem {

    String name
    ProtectedSystemType type

    static belongsTo = [
            organization: Organization
    ]

    static constraints = {
        name nullable: true
        type nullable: true
    }

    static hasMany = [
            protectedSystemTrustInteroperabilityProfileUriSet: ProtectedSystemTrustInteroperabilityProfileUri,
            protectedSystemPartnerSystemCandidateSet         : ProtectedSystemPartnerSystemCandidate
    ]

    static mapping = {
        table 'protected_system'
        name length: 1000
        type length: 1000
    }

    long idHelper() { getId() }

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSetHelper() { fromNull(getProtectedSystemTrustInteroperabilityProfileUriSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<ProtectedSystemTrustInteroperabilityProfileUri> nil()) }

    void protectedSystemTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSet) { setProtectedSystemTrustInteroperabilityProfileUriSet(new HashSet<>(protectedSystemTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSetHelper() { fromNull(getProtectedSystemPartnerSystemCandidateSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil()) }

    void protectedSystemPartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSet) { setProtectedSystemPartnerSystemCandidateSet(new HashSet<>(protectedSystemPartnerSystemCandidateSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    ProtectedSystem saveHelper() { save(failOnError: true) }

    ProtectedSystem saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<ProtectedSystem> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<ProtectedSystem> nil());
    }

    static final Option<ProtectedSystem> findByIdHelper(long id) { fromNull(findById(id)) }

    static final Option<ProtectedSystem> findByOrganizationAndNameHelper(final Organization organization, final String name) { fromNull(findByOrganizationAndName(organization, name)) }

}
