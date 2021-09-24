package edu.gatech.gtri.trustmark.trpt.domain


import org.gtri.fj.data.Option

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class Organization {

    String uri
    String name
    String description

    static constraints = {
        uri nullable: true
        name nullable: true
        description nullable: true
    }

    static hasMany = [
            protectedSystemSet         : ProtectedSystem,
            trustmarkBindingRegistrySet: TrustmarkBindingRegistry,
            userSet                    : User
    ]

    static mapping = {
        name length: 1000
        description length: 1000
        uri length: 1000
    }

    long idHelper() { id }

    org.gtri.fj.data.List<ProtectedSystem> protectedSystemSetHelper() { fromNull(protectedSystemSet).map({ Set<ProtectedSystem> set -> iterableList(set) }).orSome(nil()) }

    org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySetHelper() { fromNull(trustmarkBindingRegistrySet).map({ Set<TrustmarkBindingRegistry> set -> iterableList(set) }).orSome(nil()) }

    org.gtri.fj.data.List<User> userSetHelper() { fromNull(userSet).map({ Set<User> set -> iterableList(set) }).orSome(nil()) }

    void userSetHelperRemove(final User user) { fromNull(userSet).forEach({ Set<User> set -> set.remove(user); }) }

    void userSetHelperAdd(final User user) { fromNull(userSet).forEach({ Set<User> set -> set.add(user); }) }

    void deleteHelper() {

        save(failOnError: true);
    }

    Organization saveHelper() {

        save(failOnError: true);
    }

    void deleteAndFlushHelper() {

        delete(flush: true, failOnError: true)
    }

    Organization saveAndFlushHelper() {

        save(flush: true, failOnError: true)
    }

    static final org.gtri.fj.data.List<Organization> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<Organization> nil());
    }

    static final Option<Organization> findByIdHelper(final long id) {

        fromNull(findById(id))
    }

    static final Option<Organization> findByUriHelper(final String uri) {

        fromNull(findByUri(uri))
    }

    static final Option<Organization> findByNameHelper(final String name) {

        fromNull(findByName(name))
    }
}
