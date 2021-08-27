package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistryUri {

    String uri
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    String failureMessage

    static constraints = {
        uri nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static hasMany = [
            trustmarkBindingRegistrySet: TrustmarkBindingRegistry,
            partnerSystemCandidateSet  : PartnerSystemCandidate
    ]

    static mapping = {
        uri length: 200
        failureMessage length: 200
    }

    long idHelper() { id }

    org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySetHelper() { fromNull(getTrustmarkBindingRegistrySet()).map({ set -> iterableList(set) }).orSome(nil()) }

    void trustmarkBindingRegistrySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySet) { setTrustmarkBindingRegistrySet(new HashSet<>(trustmarkBindingRegistrySet.toJavaList())) }

    org.gtri.fj.data.List<PartnerSystemCandidate> partnerSystemCandidateSetHelper() { fromNull(getPartnerSystemCandidateSet()).map({ set -> iterableList(set) }).orSome(nil()) }

    void PartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidate> partnerSystemCandidateSet) { setPartnerSystemCandidateSet(new HashSet<>(partnerSystemCandidateSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustmarkBindingRegistryUri saveHelper() { save(failOnError: true) }

    TrustmarkBindingRegistryUri saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryUri> findAllByOrderByUriAscHelper() {

        fromNull(findAll(sort: 'uri', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUri> nil());
    }

    static final Option<TrustmarkBindingRegistryUri> findByIdHelper(long id) { fromNull(findById(id)) }

    static final Option<TrustmarkBindingRegistryUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

}
