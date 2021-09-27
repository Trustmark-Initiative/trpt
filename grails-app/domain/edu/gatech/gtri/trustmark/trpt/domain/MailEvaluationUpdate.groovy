package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class MailEvaluationUpdate {

    LocalDateTime requestDateTime
    LocalDateTime mailDateTime

    static constraints = {
        requestDateTime nullable: true
        mailDateTime nullable: true
    }

    static belongsTo = [
            partnerSystemCandidateTrustInteroperabilityProfileUri: PartnerSystemCandidateTrustInteroperabilityProfileUri
    ]

    PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUriHelper() { getPartnerSystemCandidateTrustInteroperabilityProfileUri() }

    void partnerSystemCandidateTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) { setPartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri) }

    static final org.gtri.fj.data.List<MailEvaluationUpdate> findAllByMailDateTimeIsNullHelper() {

        fromNull(findAllByMailDateTimeIsNull())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<MailEvaluationUpdate> nil())
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

    MailEvaluationUpdate saveHelper() {
        save(failOnError: true)
    }

    MailEvaluationUpdate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<MailEvaluationUpdate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<MailEvaluationUpdate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<MailEvaluationUpdate> nil());
    }
}
