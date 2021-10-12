package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class PartnerSystemCandidateTrustInteroperabilityProfileUri {

    LocalDateTime evaluationAttemptLocalDateTime
    LocalDateTime evaluationLocalDateTime

    Boolean evaluationTrustExpressionSatisfied
    byte[] evaluationTrustExpression

    Integer evaluationTrustmarkDefinitionRequirementSatisfied
    Integer evaluationTrustmarkDefinitionRequirementUnsatisfied
    byte[] evaluationTrustmarkDefinitionRequirement

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
        table 'partner_system_candidate_trust_interoperability_profile_uri'
        evaluationTrustExpression sqlType: 'blob'
        evaluationTrustmarkDefinitionRequirement sqlType: 'blob'
    }

    static belongsTo = [
            partnerSystemCandidate         : PartnerSystemCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]
    static hasMany = [
            mailEvaluationUpdateSet: MailEvaluationUpdate
    ]

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    org.gtri.fj.data.List<MailEvaluationUpdate> mailEvaluationUpdateSetHelper() { fromNull(mailEvaluationUpdateSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<MailEvaluationUpdate> nil()) }

    void mailEvaluationUpdateSetHelper(final org.gtri.fj.data.List<MailEvaluationUpdate> mailEvaluationUpdateSet) { setMailEvaluationUpdateSet(new HashSet<>(mailEvaluationUpdateSet.toJavaList())) }

    static Option<PartnerSystemCandidateTrustInteroperabilityProfileUri> findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidate partnerSystemCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

        fromNull(findByPartnerSystemCandidateAndTrustInteroperabilityProfileUri(partnerSystemCandidate, trustInteroperabilityProfileUri))
    }

    long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerSystemCandidateTrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    PartnerSystemCandidateTrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerSystemCandidateTrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil());
    }
}
