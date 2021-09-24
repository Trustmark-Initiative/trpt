package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistryUriType {

    String uri
    PartnerSystemCandidateType type
    String hash
    String json
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    LocalDateTime changeLocalDateTime
    String failureMessage

    static constraints = {
        uri nullable: true
        hash nullable: true
        json nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        changeLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static hasMany = [
            partnerSystemCandidateSet: PartnerSystemCandidate
    ]

    static hasOne = [
            trustmarkBindingRegistryUri: TrustmarkBindingRegistryUri
    ]

    static mapping = {
        table 'trustmark_binding_registry_uri_type'
        uri length: 1000
        hash length: 1000
        json type: 'text'
        failureMessage length: 1000
    }

    long idHelper() { id }

    TrustmarkBindingRegistryUri trustmarkBindingRegistryUriHelper() { getTrustmarkBindingRegistryUri() }

    void trustmarkBindingRegistryUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { setTrustmarkBindingRegistryUri(trustmarkBindingRegistryUri) }

    org.gtri.fj.data.List<PartnerSystemCandidate> partnerSystemCandidateSetHelper() { fromNull(getPartnerSystemCandidateSet()).map({ set -> iterableList(set) }).orSome(nil()) }

    void partnerSystemCandidateSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidate> partnerSystemCandidateSet) { setPartnerSystemCandidateSet(new HashSet<>(partnerSystemCandidateSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustmarkBindingRegistryUriType saveHelper() { save(failOnError: true) }

    TrustmarkBindingRegistryUriType saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryUriType> findAllByOrderByUriAscHelper() {

        fromNull(findAll(sort: 'uri', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUriType> nil());
    }

    static final Option<TrustmarkBindingRegistryUriType> findByIdHelper(long id) { fromNull(findById(id)) }

    static final Option<TrustmarkBindingRegistryUriType> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }
}
