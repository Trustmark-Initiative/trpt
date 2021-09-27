package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class Role {

    String name

    static hasMany = [
            userRoleSet: UserRole
    ]

    org.gtri.fj.data.List<UserRole> userRoleSetHelper() { fromNull(userRoleSet).map({ collection -> iterableList(collection) }).orSome(nil()) }

    void userRoleSetHelper(final org.gtri.fj.data.List<UserRole> userRoleSet) { setUserRoleSet(new HashSet<>(userRoleSet.toJavaList())) }

    static final org.gtri.fj.data.List<Role> findAllByOrderByNameAscHelper() {

        fromNull(findAll(sort: 'name', order: 'asc'))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<Role> nil());
    }

    static final Option<Role> findByNameHelper(final String name) {

        fromNull(findByName(name))
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

    Role saveHelper() {
        save(failOnError: true)
    }

    Role saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<Role> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<Role> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<Role> nil());
    }
}
