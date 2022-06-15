package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class TrustmarkBindingRegistryOrganizationMapUri implements Uri {

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
        table 'trustmark_binding_registry_organization_map_uri'
        uri length: 1000
        hash length: 1000
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    static hasMany = [
            partnerOrganizationCandidateSet: PartnerOrganizationCandidate
    ]

    static hasOne = [
            trustmarkBindingRegistryUri: TrustmarkBindingRegistryUri
    ]

    File fileHelper() { getDocument() }

    void fileHelper(final File file) { setDocument(file) }

    org.gtri.fj.data.List<PartnerOrganizationCandidate> partnerOrganizationCandidateSetHelper() { fromNull(getPartnerOrganizationCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidate> nil()) }

    void partnerOrganizationCandidateSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidate> partnerOrganizationCandidateSet) { setPartnerOrganizationCandidateSet(new HashSet<>(partnerOrganizationCandidateSet.toJavaList())) }

    TrustmarkBindingRegistryUri trustmarkBindingRegistryUriHelper() { getTrustmarkBindingRegistryUri() }

    void trustmarkBindingRegistryUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { setTrustmarkBindingRegistryUri(trustmarkBindingRegistryUri) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> findAllByOrderByUriAscHelper() {

        fromNull(findAll(sort: 'uri', order: 'asc'))
                .map({ collection -> iterableList((Iterable<TrustmarkBindingRegistryOrganizationMapUri>) collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationMapUri> nil());
    }

    static final Option<TrustmarkBindingRegistryOrganizationMapUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustmarkBindingRegistryOrganizationMapUri saveHelper() {
        save(failOnError: true)
    }

    TrustmarkBindingRegistryOrganizationMapUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustmarkBindingRegistryOrganizationMapUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationMapUri> nil());
    }
}
