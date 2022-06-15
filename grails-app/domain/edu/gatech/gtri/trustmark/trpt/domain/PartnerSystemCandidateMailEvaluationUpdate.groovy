package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class PartnerSystemCandidateMailEvaluationUpdate {

    LocalDateTime requestDateTime
    LocalDateTime mailDateTime

    static constraints = {
        requestDateTime nullable: true
        mailDateTime nullable: true
    }

    static mapping = {
        table 'partner_system_candidate_mail_evaluation_update'
    }

    static belongsTo = [
            partnerSystemCandidateTrustInteroperabilityProfileUri: PartnerSystemCandidateTrustInteroperabilityProfileUri
    ]

    PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUriHelper() { getPartnerSystemCandidateTrustInteroperabilityProfileUri() }

    void partnerSystemCandidateTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) { setPartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri) }

    static final org.gtri.fj.data.List<PartnerSystemCandidateMailEvaluationUpdate> findAllByMailDateTimeIsNullHelper() {

        fromNull(findAllByMailDateTimeIsNull())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateMailEvaluationUpdate> nil())
    }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true)
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerSystemCandidateMailEvaluationUpdate saveHelper() {
        save(failOnError: true)
    }

    PartnerSystemCandidateMailEvaluationUpdate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerSystemCandidateMailEvaluationUpdate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerSystemCandidateMailEvaluationUpdate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateMailEvaluationUpdate> nil());
    }
}
