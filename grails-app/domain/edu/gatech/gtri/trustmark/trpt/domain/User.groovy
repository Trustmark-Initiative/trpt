package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class User {

    String username
    String password
    String nameFamily
    String nameGiven
    String telephone
    boolean userEnabled
    boolean userLocked
    boolean userExpired
    boolean passwordExpired

    static constraints = {
        username nullable: true, length: 200
        password nullable: true, length: 200
        nameFamily nullable: true, length: 200
        nameGiven nullable: true, length: 200
        telephone nullable: true, length: 200
        userEnabled nullable: true
        userLocked nullable: true
        userExpired nullable: true
        passwordExpired nullable: true
    }

    static hasOne = [
            organization: Organization
    ]

    static hasMany = [
            userRoleSet         : UserRole,
            mailPasswordResetSet: MailPasswordReset
    ]

    long idHelper() { id }

    Organization organizationHelper() { organization }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    org.gtri.fj.data.List<UserRole> userRoleHelper() { fromNull(userRoleSet).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<UserRole> nil()) }

    void userRoleHelper(org.gtri.fj.data.List<UserRole> userRole) { setUserRoleSet(new HashSet<UserRole>(userRole.toJavaList())) }

    org.gtri.fj.data.List<MailPasswordReset> mailPasswordResetSetHelper() { fromNull(mailPasswordResetSet).map({ list -> iterableList(list) }).orSome(org.gtri.fj.data.List.<MailPasswordReset> nil()) }

    void mailPasswordResetHelper(final org.gtri.fj.data.List<MailPasswordReset> mailPasswordResetSet) { setMailPasswordResetSet(mailPasswordResetSet.toJavaList()) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    User saveHelper() { save(failOnError: true) }

    User saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<User> findAllByOrderByNameFamilyAscNameGivenAscHelper() {

        fromNull(findAll(sort: 'nameFamily', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<User> nil());
    }

    static final Option<User> findByIdHelper(final long id) { fromNull(findById(id)) }

    static final Option<User> findByUsernameHelper(final String username) { fromNull(findByUsername(username)) }

    Set<Role> getAuthorities() { new HashSet<>(userRoleHelper().map({ UserRole userRole -> userRole.roleHelper() }).toJavaList()) }
}
