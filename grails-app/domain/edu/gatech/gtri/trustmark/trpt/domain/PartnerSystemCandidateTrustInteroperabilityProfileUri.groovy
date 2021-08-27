package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option

import java.time.LocalDateTime

import static org.gtri.fj.data.Option.fromNull

class PartnerSystemCandidateTrustInteroperabilityProfileUri {

    LocalDateTime evaluationLocalDateTime

    Boolean evaluationTrustExpressionSatisfied
    String evaluationTrustExpression

    Integer evaluationTrustmarkDefinitionRequirementSatisfied
    Integer evaluationTrustmarkDefinitionRequirementUnsatisfied
    String evaluationTrustmarkDefinitionRequirement

    static belongsTo = [
            partnerSystemCandidate         : PartnerSystemCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    static constraints = {
        evaluationLocalDateTime nullable: true
        evaluationTrustExpressionSatisfied nullable: true
        evaluationTrustExpression nullable: true
        evaluationTrustmarkDefinitionRequirement nullable: true
        evaluationTrustmarkDefinitionRequirementSatisfied nullable: true
        evaluationTrustmarkDefinitionRequirementUnsatisfied nullable: true
    }

    static mapping = {
        table 'partner_system_candidate_trust_interoperability_profile_uri'
        evaluationTrustExpression type: 'text'
        evaluationTrustmarkDefinitionRequirement type: 'text'
    }

    long idHelper() { getId() }

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    PartnerSystemCandidateTrustInteroperabilityProfileUri saveHelper() { save(failOnError: true) }

    PartnerSystemCandidateTrustInteroperabilityProfileUri saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static Option<PartnerSystemCandidateTrustInteroperabilityProfileUri> findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidate partnerSystemCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

        fromNull(findByPartnerSystemCandidateAndTrustInteroperabilityProfileUri(partnerSystemCandidate, trustInteroperabilityProfileUri))
    }
}
