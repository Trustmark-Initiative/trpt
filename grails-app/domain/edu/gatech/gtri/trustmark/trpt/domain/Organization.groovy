package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0
import org.gtri.fj.product.P
import org.gtri.fj.product.P2

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class Organization {

    String uri
    String name
    String description

    static constraints = {
        uri nullable: true
        name nullable: true
        description nullable: true
    }

    static mapping = {
        table "organization"
        name length: 1000
        description length: 1000
        uri length: 1000
    }

    static hasMany = [protectedSystemSet                            : ProtectedSystem,
                      trustmarkBindingRegistrySet                   : TrustmarkBindingRegistry,
                      userSet                                       : User,
                      organizationPartnerOrganizationCandidateSet   : OrganizationPartnerOrganizationCandidate,
                      organizationTrustInteroperabilityProfileUriSet: OrganizationTrustInteroperabilityProfileUri,]

    org.gtri.fj.data.List<ProtectedSystem> protectedSystemSetHelper() { fromNull(protectedSystemSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystem> nil()) }

    void protectedSystemSetHelper(final org.gtri.fj.data.List<ProtectedSystem> protectedSystemSet) { setProtectedSystemSet(new HashSet<ProtectedSystem>(protectedSystemSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySetHelper() { fromNull(trustmarkBindingRegistrySet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistry> nil()) }

    void trustmarkBindingRegistrySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySet) { setTrustmarkBindingRegistrySet(new HashSet<TrustmarkBindingRegistry>(trustmarkBindingRegistrySet.toJavaList())) }

    org.gtri.fj.data.List<User> userSetHelper() { fromNull(userSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<User> nil()) }

    void userSetHelper(final org.gtri.fj.data.List<User> userSet) { setUserSet(new HashSet<User>(userSet.toJavaList())) }

    org.gtri.fj.data.List<OrganizationPartnerOrganizationCandidate> organizationPartnerOrganizationCandidateSetHelper() { fromNull(getOrganizationPartnerOrganizationCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<OrganizationPartnerOrganizationCandidate> nil()) }

    void organizationPartnerOrganizationCandidateSetHelper(final org.gtri.fj.data.List<OrganizationPartnerOrganizationCandidate> organizationPartnerOrganizationCandidateSet) { setOrganizationPartnerOrganizationCandidateSet(new HashSet<>(organizationPartnerOrganizationCandidateSet.toJavaList())) }

    org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSetHelper() { fromNull(getOrganizationTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<OrganizationTrustInteroperabilityProfileUri> nil()) }

    void organizationTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSet) { setOrganizationTrustInteroperabilityProfileUriSet(new HashSet<>(organizationTrustInteroperabilityProfileUriSet.toJavaList())) }

    static final org.gtri.fj.data.List<P2<Organization, PartnerOrganizationCandidateMailEvaluationUpdate>> findAllByPartnerOrganizationCandidateMailEvaluationUpdateMailLocalDateTimeIsNullHelper() {

        fromNull(Organization.executeQuery("SELECT DISTINCT organization1, partnerOrganizationCandidateMailEvaluationUpdate FROM PartnerOrganizationCandidateMailEvaluationUpdate partnerOrganizationCandidateMailEvaluationUpdate " +
                "JOIN partnerOrganizationCandidateMailEvaluationUpdate.partnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri " +
                "JOIN partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidate partnerOrganizationCandidate " +
                "JOIN partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri " +
                "JOIN partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSet organizationPartnerOrganizationCandidate " +
                "JOIN trustInteroperabilityProfileUri.organizationTrustInteroperabilityProfileUriSet organizationTrustInteroperabilityProfileUri " +
                "JOIN organizationPartnerOrganizationCandidate.organization organization1 " +
                "JOIN organizationTrustInteroperabilityProfileUri.organization organization2 " +
                "WHERE organization1 = organization2 " +
                "AND partnerOrganizationCandidateMailEvaluationUpdate.mailDateTime IS NULL"))
                .map({ list -> iterableList(list).map({ Object[] array -> P.p((Organization) array[0], (PartnerOrganizationCandidateMailEvaluationUpdate) [1]) }) })
                .orSome(org.gtri.fj.data.List.<P2<Organization, PartnerOrganizationCandidateMailEvaluationUpdate>> nil())
    }

    static final org.gtri.fj.data.List<Organization> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<Organization> nil());
    }

    static final Option<Organization> findByUriHelper(final String uri) {

        fromNull(findByUri(uri))
    }

    static final Option<Organization> findByNameHelper(final String name) {

        fromNull(findByName(name))
    }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    Organization saveHelper() {
        save(failOnError: true);
    }

    Organization saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<Organization> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<Organization> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<Organization> nil());
    }
}
