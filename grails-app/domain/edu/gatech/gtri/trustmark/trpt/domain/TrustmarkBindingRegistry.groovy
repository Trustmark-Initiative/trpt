package edu.gatech.gtri.trustmark.trpt.domain


import org.gtri.fj.data.Option

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistry {

    String name
    String description

    static belongsTo = [
            organization: Organization
    ]

    static constraints = {
        name nullable: true
        description nullable: true
    }

    static hasOne = [
            trustmarkBindingRegistryUri: TrustmarkBindingRegistryUri
    ]

    static mapping = {
        name length: 200
        description length: 200
    }

    long idHelper() { id }

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    TrustmarkBindingRegistryUri trustmarkBindingRegistryUriHelper() { getTrustmarkBindingRegistryUri() }

    void trustmarkBindingRegistryUriHelper(final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { setTrustmarkBindingRegistryUri(trustmarkBindingRegistryUri) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustmarkBindingRegistry saveHelper() { save(failOnError: true) }

    TrustmarkBindingRegistry saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistry> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistry> nil());
    }

    static final Option<TrustmarkBindingRegistry> findByIdHelper(long id) { fromNull(findById(id)) }

    static final Option<TrustmarkBindingRegistry> findByOrganizationAndNameHelper(final Organization organization, final String name) { fromNull(findByOrganizationAndName(organization, name)) }

    static final Option<TrustmarkBindingRegistry> findByOrganizationAndTrustmarkBindingRegistryUriHelper(final Organization organization, final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri) { fromNull(findByOrganizationAndTrustmarkBindingRegistryUri(organization, trustmarkBindingRegistryUri)) }
}
