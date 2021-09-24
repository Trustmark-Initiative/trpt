package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.Option.fromNull

class TrustmarkDefinitionUriHistory {

    String uri
    String hash
    String xml
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    LocalDateTime changeLocalDateTime
    String failureMessage

    static constraints = {
        uri nullable: true
        hash nullable: true
        xml nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        changeLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static mapping = {
        table 'trustmark_definition_uri_history'
        uri length: 1000
        hash length: 1000
        xml type: 'text'
        failureMessage length: 1000
    }

    long idHelper() { id }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustmarkDefinitionUriHistory saveHelper() { save(failOnError: true) }

    TrustmarkDefinitionUriHistory saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final Option<TrustmarkDefinitionUriHistory> findByIdHelper(long id) { fromNull(findById(id)) }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }
}
