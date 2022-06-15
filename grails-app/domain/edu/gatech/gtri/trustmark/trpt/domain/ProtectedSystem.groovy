package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0
import org.gtri.fj.product.P
import org.gtri.fj.product.P2

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class ProtectedSystem {

    String name
    ProtectedSystemType type

    static constraints = {
        name nullable: true
        type nullable: true
    }

    static mapping = {
        table 'protected_system'
        name length: 1000
        type length: 1000
    }

    static belongsTo = [
            organization: Organization
    ]

    static hasMany = [
            protectedSystemPartnerSystemCandidateSet         : ProtectedSystemPartnerSystemCandidate,
            protectedSystemTrustInteroperabilityProfileUriSet: ProtectedSystemTrustInteroperabilityProfileUri,
    ]

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSetHelper() { fromNull(getProtectedSystemPartnerSystemCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil()) }

    void protectedSystemPartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSet) { setProtectedSystemPartnerSystemCandidateSet(new HashSet<>(protectedSystemPartnerSystemCandidateSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSetHelper() { fromNull(getProtectedSystemTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystemTrustInteroperabilityProfileUri> nil()) }

    void protectedSystemTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSet) { setProtectedSystemTrustInteroperabilityProfileUriSet(new HashSet<>(protectedSystemTrustInteroperabilityProfileUriSet.toJavaList())) }

    static final org.gtri.fj.data.List<ProtectedSystem> findAllByOrderByNameAscHelper(final org.gtri.fj.data.List<Organization> organizationList) {

        fromNull(ProtectedSystem.createCriteria().list {
            'in'("organization", organizationList.toJavaList())
            order('name', 'asc')
        })
                .map({ collection -> iterableList((Iterable<ProtectedSystem>) collection) })
                .orSome(org.gtri.fj.data.List.<ProtectedSystem> nil());
    }

    static final org.gtri.fj.data.List<P2<ProtectedSystem, PartnerSystemCandidateMailEvaluationUpdate>> findAllByPartnerSystemCandidateMailEvaluationUpdateMailLocalDateTimeIsNullHelper() {

        fromNull(ProtectedSystem.executeQuery("SELECT DISTINCT protectedSystem1, partnerSystemCandidateMailEvaluationUpdate FROM PartnerSystemCandidateMailEvaluationUpdate partnerSystemCandidateMailEvaluationUpdate " +
                "JOIN partnerSystemCandidateMailEvaluationUpdate.partnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri " +
                "JOIN partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidate partnerSystemCandidate " +
                "JOIN partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri " +
                "JOIN partnerSystemCandidate.protectedSystemPartnerSystemCandidateSet protectedSystemPartnerSystemCandidate " +
                "JOIN trustInteroperabilityProfileUri.protectedSystemTrustInteroperabilityProfileUriSet protectedSystemTrustInteroperabilityProfileUri " +
                "JOIN protectedSystemPartnerSystemCandidate.protectedSystem protectedSystem1 " +
                "JOIN protectedSystemTrustInteroperabilityProfileUri.protectedSystem protectedSystem2 " +
                "WHERE protectedSystem1 = protectedSystem2 " +
                "AND partnerSystemCandidateMailEvaluationUpdate.mailDateTime IS NULL"))
                .map({ list -> iterableList(list).map({ Object[] array -> P.p((ProtectedSystem) array[0], (PartnerSystemCandidateMailEvaluationUpdate) array[1]) }) })
                .orSome(org.gtri.fj.data.List.<P2<ProtectedSystem, PartnerSystemCandidateMailEvaluationUpdate>> nil())
    }

    static final Option<ProtectedSystem> findByOrganizationAndNameHelper(final Organization organization, final String name) { fromNull(findByOrganizationAndName(organization, name)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    ProtectedSystem saveHelper() {
        save(failOnError: true)
    }

    ProtectedSystem saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<ProtectedSystem> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<ProtectedSystem> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<ProtectedSystem> nil());
    }
}
