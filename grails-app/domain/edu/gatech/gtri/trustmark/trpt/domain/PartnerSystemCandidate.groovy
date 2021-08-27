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
    String trustmarkUriMapJson
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
        trustmarkUriMapJson nullable: true
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
    ]

    static hasOne = [
            trustmarkBindingRegistryUri: TrustmarkBindingRegistryUri
    ]

    static mapping = {
        table 'partner_system_candidate'
        name length: 200
        uri length: 200
        uriEntityDescriptor length: 200
        type length: 200
        trustmarkUriMapJson type: 'text'
        json type: 'text'
        failureMessage length: 200
    }

    long idHelper() { id }

    void trustmarkBindingRegistryUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { setTrustmarkBindingRegistryUri(trustmarkBindingRegistryUri) }

    TrustmarkBindingRegistryUri trustmarkBindingRegistryUriHelper() { getTrustmarkBindingRegistryUri() }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSetHelper() { fromNull(getProtectedSystemPartnerSystemCandidateSet()).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<ProtectedSystemPartnerSystemCandidate> nil()) }

    void protectedSystemPartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<ProtectedSystemPartnerSystemCandidate> protectedSystemPartnerSystemCandidateSet) { setProtectedSystemPartnerSystemCandidateSet(new HashSet<>(protectedSystemSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    PartnerSystemCandidate saveHelper() { save(failOnError: true) }

    PartnerSystemCandidate saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final Option<PartnerSystemCandidate> findByTrustmarkBindingRegistryUriAndUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri, final String uri) { fromNull(findByTrustmarkBindingRegistryUriAndUri(trustmarkBindingRegistryUri, uri)) }

    static final org.gtri.fj.data.List<PartnerSystemCandidate> findAllByTrustmarkBindingRegistryUriAndTypeHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri, final PartnerSystemCandidateType type) {

        fromNull(findAllByTrustmarkBindingRegistryUriAndType(trustmarkBindingRegistryUri, type))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidate> nil());
    }

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
