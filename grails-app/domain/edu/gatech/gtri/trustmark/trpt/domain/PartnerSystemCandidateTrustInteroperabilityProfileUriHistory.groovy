package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0
import org.gtri.fj.lang.StringUtility
import org.gtri.fj.product.P
import org.gtri.fj.product.P3

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class PartnerSystemCandidateTrustInteroperabilityProfileUriHistory {

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
        table 'psc_tip_uri_history'
    }

    static belongsTo = [
            partnerSystemCandidate         : PartnerSystemCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

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

    PartnerSystemCandidateTrustInteroperabilityProfileUriHistory saveHelper() {
        save(failOnError: true)
    }

    PartnerSystemCandidateTrustInteroperabilityProfileUriHistory saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerSystemCandidateTrustInteroperabilityProfileUriHistory> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUriHistory> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUriHistory> nil());
    }

    static org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUriHistory> findAllByPartnerSystemCandidateTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidate partnerSystemCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {
        fromNull(findAllByPartnerSystemCandidateAndTrustInteroperabilityProfileUri(partnerSystemCandidate, trustInteroperabilityProfileUri))
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUriHistory> nil());
    }
}
