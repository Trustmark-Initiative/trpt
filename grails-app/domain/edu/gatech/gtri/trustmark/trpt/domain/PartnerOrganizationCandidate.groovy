package edu.gatech.gtri.trustmark.trpt.domain


import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class PartnerOrganizationCandidate {

    String identifier
    String name
    String nameLong
    String description
    String trustmarkRecipientIdentifierArrayJson
    String hash
    String json
    LocalDateTime requestLocalDateTime
    LocalDateTime successLocalDateTime
    LocalDateTime failureLocalDateTime
    LocalDateTime changeLocalDateTime
    String failureMessage


    static constraints = {
        identifier nullable: true
        name nullable: true
        nameLong nullable: true
        description nullable: true
        trustmarkRecipientIdentifierArrayJson nullable: true
        hash nullable: true
        json nullable: true
        requestLocalDateTime nullable: true
        successLocalDateTime nullable: true
        failureLocalDateTime nullable: true
        changeLocalDateTime nullable: true
        failureMessage nullable: true
    }

    static mapping = {
        table 'partner_organization_candidate'
        identifier length: 1000
        name length: 1000
        nameLong length: 1000
        description length: 1000
        trustmarkRecipientIdentifierArrayJson type: 'text'
        hash type: 'text'
        json type: 'text'
        failureMessage length: 1000
    }

    static hasMany = [
            partnerOrganizationCandidateTrustInteroperabilityProfileUriSet: PartnerOrganizationCandidateTrustInteroperabilityProfileUri,
            partnerOrganizationCandidateTrustmarkUriSet                   : PartnerOrganizationCandidateTrustmarkUri,
            organizationPartnerOrganizationCandidateSet                   : OrganizationPartnerOrganizationCandidate,
    ]

    static hasOne = [
            trustmarkBindingRegistryOrganizationMapUri: TrustmarkBindingRegistryOrganizationMapUri
    ]

    org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSet) { setPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerOrganizationCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerOrganizationCandidateTrustmarkUri> partnerOrganizationCandidateTrustmarkUriSetHelper() { fromNull(getPartnerOrganizationCandidateTrustmarkUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustmarkUri> nil()) }

    void partnerOrganizationCandidateTrustmarkUriSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateTrustmarkUri> partnerOrganizationCandidateTrustmarkUriSet) { setPartnerOrganizationCandidateTrustmarkUriSet(new HashSet<>(partnerOrganizationCandidateTrustmarkUriSet.toJavaList())) }

    org.gtri.fj.data.List<OrganizationPartnerOrganizationCandidate> protectedSystemPartnerOrganizationCandidateSetHelper() { fromNull(getProtectedSystemPartnerOrganizationCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<OrganizationPartnerOrganizationCandidate> nil()) }

    void protectedSystemPartnerOrganizationCandidateSetHelper(final org.gtri.fj.data.List<OrganizationPartnerOrganizationCandidate> protectedSystemPartnerOrganizationCandidateSet) { this.setOrganizationPartnerOrganizationCandidateSet(new HashSet<>(protectedSystemPartnerOrganizationCandidateSet.toJavaList())) }

    TrustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUriHelper() { this.getTrustmarkBindingRegistryOrganizationMapUri() }

    void trustmarkBindingRegistryOrganizationMapUriHelper(final TrustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri) { this.setTrustmarkBindingRegistryOrganizationMapUri(trustmarkBindingRegistryOrganizationMapUri) }

    static final Option<PartnerOrganizationCandidate> findByTrustmarkBindingRegistryOrganizationMapUriAndUriHelper(final TrustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri, final String uri) { fromNull(findByTrustmarkBindingRegistryOrganizationMapUriAndUri(trustmarkBindingRegistryOrganizationMapUri, uri)) }

    static final org.gtri.fj.data.List<PartnerOrganizationCandidate> findAllByOrganizationInHelper(final org.gtri.fj.data.List<Organization> organizationList) {

        fromNull(ProtectedSystem.executeQuery("SELECT DISTINCT partnerOrganizationCandidate " +
                "FROM PartnerOrganizationCandidate partnerOrganizationCandidate " +
                "JOIN partnerOrganizationCandidate.trustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri " +
                "JOIN trustmarkBindingRegistryOrganizationMapUri.trustmarkBindingRegistryUri trustmarkBindingRegistryUri " +
                "JOIN trustmarkBindingRegistryUri.trustmarkBindingRegistrySet trustmarkBindingRegistry " +
                "WHERE trustmarkBindingRegistry.organization IN (:organizationList) ",
                [organizationList: organizationList.toJavaList()]))
                .map({ list -> iterableList(list).map({ Object[] array -> array[0] }) })
                .orSome(org.gtri.fj.data.List.nil())
    }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerOrganizationCandidate saveHelper() {
        save(failOnError: true)
    }

    PartnerOrganizationCandidate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerOrganizationCandidate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidate> nil());
    }
}
