package edu.gatech.gtri.trustmark.grails.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class TrustInteroperabilityProfileUriHistory implements Uri {

    String uri
    String name
    String description
    LocalDateTime publicationLocalDateTime
    String issuerName
    String issuerIdentifier
    String hash
    File document
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
        name nullable: true
        description nullable: true
        publicationLocalDateTime nullable: true
        issuerName nullable: true
        issuerIdentifier nullable: true
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
        table 'trust_interoperability_profile_uri_history'
        uri length: 1000
        name length: 1000
        description length: 1000
        issuerName length: 1000
        issuerIdentifier length: 1000
        hash length: 1000
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    File fileHelper() { getDocument() }

    void fileHelper(final File file) { setDocument(file) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustInteroperabilityProfileUriHistory saveHelper() {
        save(failOnError: true)
    }

    TrustInteroperabilityProfileUriHistory saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustInteroperabilityProfileUriHistory> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustInteroperabilityProfileUriHistory> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUriHistory> nil());
    }
}
