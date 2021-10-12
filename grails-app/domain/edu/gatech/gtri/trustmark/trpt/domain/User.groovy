package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

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
        username nullable: true, length: 1000
        password nullable: true, length: 1000
        nameFamily nullable: true, length: 1000
        nameGiven nullable: true, length: 1000
        telephone nullable: true, length: 1000
        userEnabled nullable: true
        userLocked nullable: true
        userExpired nullable: true
        passwordExpired nullable: true
    }

    static hasMany = [
            userRoleSet         : UserRole,
            mailPasswordResetSet: MailPasswordReset
    ]

    static hasOne = [
            organization: Organization
    ]

    Organization organizationHelper() { organization }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    org.gtri.fj.data.List<UserRole> userRoleSetHelper() { fromNull(userRoleSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<UserRole> nil()) }

    void userRoleSetHelper(org.gtri.fj.data.List<UserRole> userRole) { setUserRoleSet(new HashSet<UserRole>(userRole.toJavaList())) }

    org.gtri.fj.data.List<MailPasswordReset> mailPasswordResetSetHelper() { fromNull(mailPasswordResetSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<MailPasswordReset> nil()) }

    void mailPasswordSetResetHelper(final org.gtri.fj.data.List<MailPasswordReset> mailPasswordResetSet) { setMailPasswordResetSet(mailPasswordResetSet.toJavaList()) }

    static final org.gtri.fj.data.List<User> findAllByOrderByNameFamilyAscNameGivenAscHelper(final org.gtri.fj.data.List<Organization> organizationList, final org.gtri.fj.data.List<Role> roleList) {

        fromNull(ProtectedSystem.executeQuery("SELECT DISTINCT user FROM User user " +
                "JOIN user.userRoleSet userRole " +
                "WHERE userRole.role IN (:roleList) " +
                "AND user.organization IN (:organizationList)",
                [roleList: roleList.toJavaList(), organizationList: organizationList.toJavaList()]))
                .map({ list -> iterableList(list).map({ Object[] array -> array[0] }) })
                .orSome(org.gtri.fj.data.List.nil())
    }

    static final Option<User> findByUsernameHelper(final String username) { fromNull(findByUsername(username)) }

    Set<Role> getAuthorities() { new HashSet<>(userRoleSetHelper().map({ UserRole userRole -> userRole.roleHelper() }).toJavaList()) }

    long idHelper() {
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
