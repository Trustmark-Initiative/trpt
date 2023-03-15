package edu.gatech.gtri.trustmark.grails.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class PartnerOrganizationCandidateMailEvaluationUpdate {

    LocalDateTime requestDateTime
    LocalDateTime mailDateTime

    static constraints = {
        requestDateTime nullable: true
        mailDateTime nullable: true
    }

    static mapping = {
        table 'partner_organization_candidate_mail_evaluation_update'
        partnerOrganizationCandidateTrustInteroperabilityProfileUri column: 'partner_organization_candidate_trust_interoperability_profile_id'
    }

    static belongsTo = [
            partnerOrganizationCandidateTrustInteroperabilityProfileUri: PartnerOrganizationCandidateTrustInteroperabilityProfileUri
    ]

    PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUriHelper() { getPartnerOrganizationCandidateTrustInteroperabilityProfileUri() }

    void partnerOrganizationCandidateTrustInteroperabilityProfileUriHelper(final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) { setPartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri) }

    static final org.gtri.fj.data.List<PartnerOrganizationCandidateMailEvaluationUpdate> findAllByMailDateTimeIsNullHelper() {

        fromNull(findAllByMailDateTimeIsNull())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateMailEvaluationUpdate> nil())
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

    PartnerOrganizationCandidateMailEvaluationUpdate saveHelper() {
        save(failOnError: true)
    }

    PartnerOrganizationCandidateMailEvaluationUpdate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerOrganizationCandidateMailEvaluationUpdate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidateMailEvaluationUpdate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateMailEvaluationUpdate> nil());
    }
}
