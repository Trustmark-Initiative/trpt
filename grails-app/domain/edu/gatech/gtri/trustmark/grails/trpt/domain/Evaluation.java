package edu.gatech.gtri.trustmark.grails.trpt.domain;

import java.time.LocalDateTime;

public interface Evaluation<T1 extends PartnerCandidate> {

    Long idHelper();

    T1 partnerCandidateHelper();

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper();

    LocalDateTime getEvaluationAttemptLocalDateTime();

    LocalDateTime getEvaluationLocalDateTime();

    Boolean getEvaluationTrustExpressionSatisfied();

    File getEvaluationTrustExpression();

    Integer getEvaluationTrustmarkDefinitionRequirementSatisfied();

    Integer getEvaluationTrustmarkDefinitionRequirementUnsatisfied();

    File getEvaluationTrustmarkDefinitionRequirement();

    org.gtri.fj.data.List<Evaluation<T1>> findAllByPartnerCandidateTrustInteroperabilityProfileUriHelper();
}
