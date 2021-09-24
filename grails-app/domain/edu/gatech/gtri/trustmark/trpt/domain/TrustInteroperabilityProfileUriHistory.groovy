package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustInteroperabilityProfileUriHistory {

    String uri
    String name
    String description
    LocalDateTime publicationLocalDateTime
    String issuerName
    String issuerIdentifier
    String hash
    String json
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    LocalDateTime changeLocalDateTime
    String failureMessage

    static constraints = {
        uri nullable: true
        name nullable: true
        description nullable: true
        publicationLocalDateTime nullable: true
        issuerName nullable: true
        issuerIdentifier nullable: true
        hash nullable: true
        json nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        changeLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static mapping = {
        table 'trust_interoperability_profile_uri_history'
        uri length: 1000
        name length: 1000
        description length: 1000
        issuerName length: 1000
        issuerIdentifier length: 1000
        hash length: 1000
        json type: 'text'
        failureMessage length: 1000
    }

    long idHelper() { id }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustInteroperabilityProfileUriHistory saveHelper() { save(failOnError: true) }

    TrustInteroperabilityProfileUriHistory saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<TrustInteroperabilityProfileUriHistory> findAllHelper() { fromNull(findAll()).map({ List<TrustInteroperabilityProfileUriHistory> list -> iterableList(list) }) orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUriHistory> nil()) }

    static final Option<TrustInteroperabilityProfileUriHistory> findByIdHelper(long id) { fromNull(findById(id)) }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }
}
