package edu.gatech.gtri.trustmark.trpt.domain


import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistry {

    String name
    String description

    static constraints = {
        name nullable: true
        description nullable: true
    }

    static mapping = {
        table 'trustmark_binding_registry'
        name length: 1000
        description length: 1000
    }

    static belongsTo = [
            organization: Organization
    ]

    static hasOne = [
            trustmarkBindingRegistryUri: TrustmarkBindingRegistryUri
    ]

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    TrustmarkBindingRegistryUri trustmarkBindingRegistryUriHelper() { getTrustmarkBindingRegistryUri() }

    void trustmarkBindingRegistryUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { setTrustmarkBindingRegistryUri(trustmarkBindingRegistryUri) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistry> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistry> nil());
    }

    static final Option<TrustmarkBindingRegistry> findByOrganizationAndNameHelper(final Organization organization, final String name) { fromNull(findByOrganizationAndName(organization, name)) }

    static final Option<TrustmarkBindingRegistry> findByOrganizationAndTrustmarkBindingRegistryUriHelper(final Organization organization, final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { fromNull(findByOrganizationAndTrustmarkBindingRegistryUri(organization, trustmarkBindingRegistryUri)) }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustmarkBindingRegistry saveHelper() {
        save(failOnError: true)
    }

    TrustmarkBindingRegistry saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustmarkBindingRegistry> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustmarkBindingRegistry> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistry> nil());
    }
}
