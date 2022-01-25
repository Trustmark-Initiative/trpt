package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustInteroperabilityProfileUri implements Uri {

    String uri
    String name
    String description
    LocalDateTime publicationLocalDateTime
    String issuerName
    String issuerIdentifier
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
        table 'trust_interoperability_profile_uri'
        uri length: 1000
        name length: 1000
        description length: 1000
        issuerName length: 1000
        issuerIdentifier length: 1000
        hash length: 1000
        document type: 'text'
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    static hasMany = [
            partnerSystemCandidateTrustInteroperabilityProfileUriSet      : PartnerSystemCandidateTrustInteroperabilityProfileUri,
            partnerOrganizationCandidateTrustInteroperabilityProfileUriSet: PartnerOrganizationCandidateTrustInteroperabilityProfileUri,
            protectedSystemTrustInteroperabilityProfileUriSet             : ProtectedSystemTrustInteroperabilityProfileUri,
            organizationTrustInteroperabilityProfileUriSet                : OrganizationTrustInteroperabilityProfileUri
    ]

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSet) { setPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerOrganizationCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSetHelper() { fromNull(getProtectedSystemTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystemTrustInteroperabilityProfileUri> nil()) }

    void protectedSystemTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSet) { setProtectedSystemTrustInteroperabilityProfileUriSet(new HashSet<>(protectedSystemTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSetHelper() { fromNull(getOrganizationTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<OrganizationTrustInteroperabilityProfileUri> nil()) }

    void organizationTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSet) { setOrganizationTrustInteroperabilityProfileUriSet(new HashSet<>(organizationTrustInteroperabilityProfileUriSet.toJavaList())) }

    static final Option<TrustInteroperabilityProfileUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    TrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUri> nil());
    }
}
