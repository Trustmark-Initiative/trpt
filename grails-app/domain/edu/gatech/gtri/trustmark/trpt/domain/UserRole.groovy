package edu.gatech.gtri.trustmark.trpt.domain

class UserRole {

    static belongsTo = [
            user: User,
            role: Role
    ]

    long idHelper() { id }

    User userHelper() { user }

    void userHelper(final User user) { this.user = user }

    Role roleHelper() { role }

    void roleHelper(final Role role) { this.role = role }

    void deleteAndFlushHelper() {

        delete(flush: true, failOnError: true)
    }

    void deleteHelper() {

        delete(failOnError: true)
    }

    UserRole saveAndFlushHelper() {

        save(flush: true, failOnError: true)
    }
}
