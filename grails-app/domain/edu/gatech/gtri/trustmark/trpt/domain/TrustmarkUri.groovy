package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustmarkUri implements Uri {

    String uri
    String hash
    String document
    LocalDateTime documentRequestLocalDateTime
    LocalDateTime documentSuccessLocalDateTime
    LocalDateTime documentFailureLocalDateTime
    LocalDateTime documentChangeLocalDateTime
    String documentFailureMessage
    LocalDateTime serverRequestLocalDateTime
    LocalDateTime serverSuccessLocalDateTime
    LocalDateTime serverFailureLocalDateTime
    LocalDateTime serverChangeLocalDateTime
    String serverFailureMessage

    static constraints = {
        uri nullable: true
        hash nullable: true
        document nullable: true
        documentRequestLocalDateTime nullable: true
        documentSuccessLocalDateTime nullable: true
        documentFailureLocalDateTime nullable: true
        documentChangeLocalDateTime nullable: true
        documentFailureMessage nullable: true
        serverRequestLocalDateTime nullable: true
        serverSuccessLocalDateTime nullable: true
        serverFailureLocalDateTime nullable: true
        serverChangeLocalDateTime nullable: true
        serverFailureMessage nullable: true
    }

    static mapping = {
        table 'trustmark_uri'
        uri length: 1000
        hash length: 1000
        document type: 'text'
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    static hasMany = [
            partnerSystemCandidateTrustmarkUriSet: PartnerSystemCandidateTrustmarkUri
    ]

    org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSetHelper() { fromNull(getPartnerSystemCandidateTrustmarkUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustmarkUri> nil()) }

    void partnerSystemCandidateTrustmarkUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustmarkUri> partnerSystemCandidateTrustmarkUriSet) { setPartnerSystemCandidateTrustmarkUriSet(new HashSet<>(partnerSystemCandidateTrustmarkUriSet.toJavaList())) }

    static final Option<TrustmarkUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustmarkUri saveHelper() {
        save(failOnError: true)
    }

    TrustmarkUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustmarkUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustmarkUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkUri> nil());
    }
}
