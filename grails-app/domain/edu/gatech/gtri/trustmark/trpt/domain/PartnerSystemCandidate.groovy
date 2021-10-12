package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class PartnerSystemCandidate {

    String name
    String uri
    String uriEntityDescriptor
    PartnerSystemCandidateType type
    String hash
    String json
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    LocalDateTime changeLocalDateTime
    String failureMessage

    static constraints = {
        name nullable: true
        uri nullable: true
        uriEntityDescriptor nullable: true
        type nullable: true
        hash nullable: true
        json nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        changeLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static mapping = {
        table 'partner_system_candidate'
        name length: 1000
        uri length: 1000
        uriEntityDescriptor length: 1000
        type length: 1000
        hash type: 'text'
        json type: 'text'
        failureMessage length: 1000
    }

    static hasMany = [
            partnerSystemCandidateTrustInteroperabilityProfileUriSet: PartnerSystemCandidateTrustInteroperabilityProfileUri,
            partnerSystemCandidateTrustmarkUriSet                   : PartnerSystemCandidateTrustmarkUri,
            protectedSystemPartnerSystemCandidateSet                : ProtectedSystemPartnerSystemCandidate,
    ]

    static hasOne = [
            trustmarkBindingRegistryUriType: TrustmarkBindingRegistryUriType
    ]

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSetHelper() { fromNull(getPartnerSystemCandidateTrustmarkUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustmarkUri> nil()) }

    void partnerSystemCandidateTrustmarkUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSet) { setPartnerSystemCandidateTrustmarkUriSet(new HashSet<>(partnerSystemCandidateTrustmarkUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSetHelper() { fromNull(getProtectedSystemPartnerSystemCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil()) }

    void protectedSystemPartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSet) { setProtectedSystemPartnerSystemCandidateSet(new HashSet<>(protectedSystemSet.toJavaList())) }

    TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriTypeHelper() { this.getTrustmarkBindingRegistryUriType() }

    void trustmarkBindingRegistryUriTypeHelper(final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType) { this.setTrustmarkBindingRegistryUriType(trustmarkBindingRegistryUriType) }

    static final Option<PartnerSystemCandidate> findByTrustmarkBindingRegistryUriTypeAndUriHelper(final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType, final String uri) { fromNull(findByTrustmarkBindingRegistryUriTypeAndUri(trustmarkBindingRegistryUriType, uri)) }

    static final org.gtri.fj.data.List<PartnerSystemCandidate> findAllByTypeInHelper(final org.gtri.fj.data.List<Organization> organizationList, final org.gtri.fj.data.List<PartnerSystemCandidateType> typeList) {

        fromNull(ProtectedSystem.executeQuery("SELECT DISTINCT partnerSystemCandidate " +
                "FROM PartnerSystemCandidate partnerSystemCandidate " +
                "JOIN partnerSystemCandidate.trustmarkBindingRegistryUriType trustmarkBindingRegistryUriType " +
                "JOIN trustmarkBindingRegistryUriType.trustmarkBindingRegistryUri trustmarkBindingRegistryUri " +
                "JOIN trustmarkBindingRegistryUri.trustmarkBindingRegistrySet trustmarkBindingRegistry " +
                "WHERE trustmarkBindingRegistry.organization IN (:organizationList) " +
                "AND partnerSystemCandidate.type IN (:typeList)",
                [organizationList: organizationList.toJavaList(), typeList: typeList.toJavaList()]))
                .map({ list -> iterableList(list).map({ Object[] array -> array[0] }) })
                .orSome(org.gtri.fj.data.List.nil())
    }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerSystemCandidate saveHelper() {
        save(failOnError: true)
    }

    PartnerSystemCandidate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerSystemCandidate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerSystemCandidate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidate> nil());
    }
}
