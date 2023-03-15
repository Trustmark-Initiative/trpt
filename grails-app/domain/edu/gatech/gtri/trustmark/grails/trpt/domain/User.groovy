package edu.gatech.gtri.trustmark.grails.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class User {

    String username
    String nameFamily
    String nameGiven
    String contactEmail
    String roleArrayJson

    static constraints = {
        username nullable: true, length: 1000
        nameFamily nullable: true, length: 1000
        nameGiven nullable: true, length: 1000
        contactEmail nullable: true, length: 1000
        roleArrayJson nullable: true, length: 1000
        organization nullable: true
    }

    static mapping = {
        table 'user'
    }

    static hasOne = [
            organization: Organization
    ]

    Option<Organization> organizationHelper() { fromNull(organization) }

    void organizationHelper(final Option<Organization> organization) { setOrganization(organization.toNull()) }

    static final org.gtri.fj.data.List<User> findAllByOrderByNameFamilyAscNameGivenAscHelper(final org.gtri.fj.data.List<Organization> organizationList) {

        fromNull(User.executeQuery("SELECT DISTINCT user FROM User user " +
                "WHERE user.organization IN (:organizationList)",
                [organizationList: organizationList.toJavaList()]))
                .map({ list -> iterableList(list).map({ Object[] array -> (User) array[0] }) })
                .orSome(org.gtri.fj.data.List.<User> nil())
    }

    static final org.gtri.fj.data.List<User> findAllByOrganizationIsNullOrderByNameFamilyAscNameGivenAscHelper() {

        fromNull(User.findAllByOrganizationIsNullOrderByNameFamilyAscNameGivenAsc())
                .map({ list -> iterableList(list).map({ Object[] array -> (User) array[0] }) })
                .orSome(org.gtri.fj.data.List.<User> nil())
    }

    static final Option<User> findByUsernameHelper(final String username) { fromNull(findByUsername(username)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    User saveHelper() {
        save(failOnError: true)
    }

    User saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<User> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<User> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<User> nil());
    }
}
