package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class Role {

    String name

    static hasMany = [
            userRoleSet: UserRole
    ]

    long getIdHelper() { id }

    String getNameHelper() { name }

    org.gtri.fj.data.List<UserRole> getUserRoleSetHelper() { fromNull(userRoleSet).map({ Set<UserRole> set -> iterableList(set) }).orSome(nil()) }

    void deleteHelper() {

        delete(flush: true, failOnError: true)
    }

    Role saveHelper() {

        save(flush: true, failOnError: true)
    }

    static final org.gtri.fj.data.List<Role> findAllHelper() {

        fromNull(findAll())
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<Role> nil());
    }

    static final org.gtri.fj.data.List<Role> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<Role> nil());
    }

    static final Option<Role> findByIdHelper(final long id) {

        fromNull(findById(id))
    }

    static final Option<Role> findByNameHelper(final String name) {

        fromNull(findByName(name))
    }
}
