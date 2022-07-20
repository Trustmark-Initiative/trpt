package edu.gatech.gtri.trustmark.trpt.domain


import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory {

    LocalDateTime evaluationAttemptLocalDateTime
    LocalDateTime evaluationLocalDateTime

    Boolean evaluationTrustExpressionSatisfied
    File evaluationTrustExpression

    Integer evaluationTrustmarkDefinitionRequirementSatisfied
    Integer evaluationTrustmarkDefinitionRequirementUnsatisfied
    File evaluationTrustmarkDefinitionRequirement

    static constraints = {
        evaluationAttemptLocalDateTime nullable: true
        evaluationLocalDateTime nullable: true
        evaluationTrustExpressionSatisfied nullable: true
        evaluationTrustExpression nullable: true
        evaluationTrustmarkDefinitionRequirement nullable: true
        evaluationTrustmarkDefinitionRequirementSatisfied nullable: true
        evaluationTrustmarkDefinitionRequirementUnsatisfied nullable: true
    }

    static mapping = {
        table 'poc_tip_uri_history'
    }

    static belongsTo = [
            partnerOrganizationCandidate   : PartnerOrganizationCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    PartnerOrganizationCandidate partnerOrganizationCandidateHelper() { getPartnerOrganizationCandidate() }

    void partnerOrganizationCandidateHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate) { setPartnerOrganizationCandidate(partnerOrganizationCandidate) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory saveHelper() {
        save(failOnError: true)
    }

    PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory> nil());
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory> findAllByPartnerOrganizationCandidateTrustInteroperabilityProfileUriHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {
        fromNull(findAllByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUri(partnerOrganizationCandidate, trustInteroperabilityProfileUri))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory> nil());
    }
}
