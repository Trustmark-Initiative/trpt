package edu.gatech.gtri.trustmark.trpt.domain


import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0
import org.gtri.fj.lang.StringUtility
import org.gtri.fj.product.P
import org.gtri.fj.product.P3

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
        evaluationTrustExpression sqlType: 'mediumblob'
        evaluationTrustmarkDefinitionRequirement sqlType: 'mediumblob'
    }

    static belongsTo = [
            partnerSystemCandidate         : PartnerSystemCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    static hasMany = [
            partnerSystemCandidateMailEvaluationUpdateSet: PartnerSystemCandidateMailEvaluationUpdate
    ]

    PartnerSystemCandidate partnerSystemCandidateHelper() { getPartnerSystemCandidate() }

    void partnerSystemCandidateHelper(final PartnerSystemCandidate partnerSystemCandidate) { setPartnerSystemCandidate(partnerSystemCandidate) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    org.gtri.fj.data.List<PartnerSystemCandidateMailEvaluationUpdate> partnerSystemCandidateMailEvaluationUpdateSetHelper() { fromNull(partnerSystemCandidateMailEvaluationUpdateSet).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateMailEvaluationUpdate> nil()) }

    void partnerSystemCandidateMailEvaluationUpdateSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateMailEvaluationUpdate> partnerSystemCandidateMailEvaluationUpdateSet) { setPartnerSystemCandidateMailEvaluationUpdateSet(new HashSet<>(partnerSystemCandidateMailEvaluationUpdateSet.toJavaList())) }

    static Option<PartnerSystemCandidateTrustInteroperabilityProfileUri> findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(final PartnerSystemCandidate partnerSystemCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

        fromNull(findByPartnerSystemCandidateAndTrustInteroperabilityProfileUri(partnerSystemCandidate, trustInteroperabilityProfileUri))
    }

    static org.gtri.fj.data.List<P3<PartnerSystemCandidate, TrustInteroperabilityProfileUri, Option<PartnerSystemCandidateTrustInteroperabilityProfileUri>>> findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriAndProtectedSystemHelper(
            final org.gtri.fj.data.List<PartnerSystemCandidate> partnerSystemCandidateList,
            final org.gtri.fj.data.List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriList,
            final org.gtri.fj.data.List<ProtectedSystem> protectedSystemList) {

        return fromNull(PartnerSystemCandidateTrustInteroperabilityProfileUri.executeQuery("SELECT DISTINCT partnerSystemCandidate, trustInteroperabilityProfileUri, partnerSystemCandidateTrustInteroperabilityProfileUri " +
                "FROM PartnerSystemCandidate partnerSystemCandidate, TrustInteroperabilityProfileUri trustInteroperabilityProfileUri " +
                "JOIN partnerSystemCandidate.trustmarkBindingRegistrySystemMapUriType trustmarkBindingRegistrySystemMapUriType " +
                "JOIN trustmarkBindingRegistrySystemMapUriType.trustmarkBindingRegistryUri trustmarkBindingRegistryUri " +
                "JOIN trustmarkBindingRegistryUri.trustmarkBindingRegistrySet trustmarkBindingRegistry " +
                "JOIN trustmarkBindingRegistry.organization organization " +
                "JOIN organization.protectedSystemSet organizationProtectedSystem " +
                "JOIN trustInteroperabilityProfileUri.protectedSystemTrustInteroperabilityProfileUriSet protectedSystemTrustInteroperabilityProfileUri " +
                "JOIN protectedSystemTrustInteroperabilityProfileUri.protectedSystem protectedSystemTrustInteroperabilityProfileUriProtectedSystem " +
                "LEFT JOIN PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri " +
                "ON partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidate = partnerSystemCandidate " +
                "AND partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri = trustInteroperabilityProfileUri " +
                "WHERE organizationProtectedSystem = protectedSystemTrustInteroperabilityProfileUriProtectedSystem " +
                (partnerSystemCandidateList.isNotEmpty() ? "AND (partnerSystemCandidate IN (:partnerSystemCandidateList)) " : "") +
                (trustInteroperabilityProfileUriList.isNotEmpty() ? "AND (trustInteroperabilityProfileUri IN (:trustInteroperabilityProfileUriList)) " : "") +
                (protectedSystemList.isNotEmpty() ? "AND (organizationProtectedSystem IN (:protectedSystemList)) " : "") +
                "ORDER BY partnerSystemCandidateTrustInteroperabilityProfileUri.evaluationAttemptLocalDateTime ",
                org.gtri.fj.data.TreeMap.iterableTreeMap(StringUtility.stringOrd, org.gtri.fj.data.Option.somes(org.gtri.fj.data.List.arrayList(
                        Option.iif(partnerSystemCandidateList.isNotEmpty(), P.p("partnerSystemCandidateList", partnerSystemCandidateList.toJavaList())),
                        Option.iif(trustInteroperabilityProfileUriList.isNotEmpty(), P.p("trustInteroperabilityProfileUriList", trustInteroperabilityProfileUriList.toJavaList())),
                        Option.iif(protectedSystemList.isNotEmpty(), P.p("protectedSystemList", protectedSystemList.toJavaList()))))).toMutableMap()))
                .map({ list -> iterableList(list).map({ Object[] array -> P.p(array[0], array[1], fromNull(array[2])) }) })
                .orSome(org.gtri.fj.data.List.nil())
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
