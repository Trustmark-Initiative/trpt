package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

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

    static hasMany = [
            partnerSystemCandidateTrustInteroperabilityProfileUriSet: PartnerSystemCandidateTrustInteroperabilityProfileUri,
            protectedSystemPartnerSystemCandidateSet                : ProtectedSystemPartnerSystemCandidate,
            partnerSystemCandidateTrustmarkUriSet                   : PartnerSystemCandidateTrustmarkUri
    ]

    static hasOne = [
            trustmarkBindingRegistryUriType: TrustmarkBindingRegistryUriType
    ]

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

    long idHelper() { id }

    void trustmarkBindingRegistryUriTypeHelper(final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType) { this.setTrustmarkBindingRegistryUriType(trustmarkBindingRegistryUriType) }

    TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriTypeHelper() { this.getTrustmarkBindingRegistryUriType() }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSetHelper() { fromNull(getPartnerSystemCandidateTrustmarkUriSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustmarkUri> nil()) }

    void partnerSystemCandidateTrustmarkUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSet) { setPartnerSystemCandidateTrustmarkUriSet(new HashSet<>(partnerSystemCandidateTrustmarkUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSetHelper() { fromNull(getProtectedSystemPartnerSystemCandidateSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil()) }

    void protectedSystemPartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSet) { setProtectedSystemPartnerSystemCandidateSet(new HashSet<>(protectedSystemSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    PartnerSystemCandidate saveHelper() { save(failOnError: true) }

    PartnerSystemCandidate saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final Option<PartnerSystemCandidate> findByTrustmarkBindingRegistryUriTypeAndUriHelper(final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType, final String uri) { fromNull(findByTrustmarkBindingRegistryUriTypeAndUri(trustmarkBindingRegistryUriType, uri)) }

    static final org.gtri.fj.data.List<PartnerSystemCandidate> findAllHelper() {

        fromNull(findAll())
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidate> nil())
    }

    static final org.gtri.fj.data.List<PartnerSystemCandidate> findAllByTypeInHelper(final org.gtri.fj.data.List<PartnerSystemCandidateType> typeList) {
        fromNull(PartnerSystemCandidate.withCriteria {
            'in' 'type', typeList.toJavaList()
        })
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidate> nil());
    }

    static final Option<PartnerSystemCandidate> findByIdHelper(long id) { fromNull(findById(id)) }
}
