package edu.gatech.gtri.trustmark.grails.trpt.domain


import grails.gorm.PagedResultList
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistryOrganizationTrustmarkMapUri implements Uri {

    String uri
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
        table 'trustmark_binding_registry_organization_trustmark_map_uri'
        uri length: 1000
        hash length: 1000
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    File fileHelper() { getDocument() }

    void fileHelper(final File file) { setDocument(file) }

    static final Option<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustmarkBindingRegistryOrganizationTrustmarkMapUri saveHelper() {
        save(failOnError: true)
    }

    TrustmarkBindingRegistryOrganizationTrustmarkMapUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> nil());
    }

    static PagedResultList<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> findAllHelper(final long offset, final long max) {
        fromNull((PagedResultList<TrustmarkBindingRegistryOrganizationTrustmarkMapUri>) TrustmarkBindingRegistryOrganizationTrustmarkMapUri.createCriteria().list(offset: offset, max: max) {})
                .orSome(new PagedResultList<TrustmarkBindingRegistryOrganizationTrustmarkMapUri>(null));
    }
}
