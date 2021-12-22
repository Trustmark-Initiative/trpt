package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class MailPasswordReset {

    String external
    LocalDateTime requestDateTime
    LocalDateTime mailDateTime
    LocalDateTime resetDateTime
    LocalDateTime expireDateTime

    static constraints = {
        external nullable: true, length: 36
        requestDateTime nullable: true
        mailDateTime nullable: true
        resetDateTime nullable: true
        expireDateTime nullable: true
    }

    static belongsTo = [
            user: User
    ]

    User userHelper() { getUser() }

    void userHelper(final User user) { setUser(user) }

    static final org.gtri.fj.data.List<MailPasswordReset> findAllByUserHelper(
            final User user) {

        fromNull(findAllByUser(user))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<MailPasswordReset> nil())
    }

    static final org.gtri.fj.data.List<MailPasswordReset> findAllByMailDateTimeIsNullHelper() {

        fromNull(findAllByMailDateTimeIsNull())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<MailPasswordReset> nil())
    }

    static final Option<MailPasswordReset> findByExternalHelper(
            final String external) {

        fromNull(findByExternal(external))
    }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true)
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    MailPasswordReset saveHelper() {
        save(failOnError: true)
    }

    MailPasswordReset saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<MailPasswordReset> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<MailPasswordReset> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<MailPasswordReset> nil());
    }
}
