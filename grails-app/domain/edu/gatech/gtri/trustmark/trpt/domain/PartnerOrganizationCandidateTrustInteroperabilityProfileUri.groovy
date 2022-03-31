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
class PartnerOrganizationCandidateTrustInteroperabilityProfileUri {

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
        table 'partner_organization_candidate_trust_interoperability_profile_ur'
    }

    static belongsTo = [
            partnerOrganizationCandidate   : PartnerOrganizationCandidate,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    static hasMany = [
            partnerOrganizationCandidateMailEvaluationUpdateSet: PartnerOrganizationCandidateMailEvaluationUpdate
    ]

    PartnerOrganizationCandidate partnerOrganizationCandidateHelper() { getPartnerOrganizationCandidate() }

    void partnerOrganizationCandidateHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate) { setPartnerOrganizationCandidate(partnerOrganizationCandidate) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    org.gtri.fj.data.List<PartnerOrganizationCandidateMailEvaluationUpdate> partnerOrganizationCandidateMailEvaluationUpdateSetHelper() { fromNull(getPartnerOrganizationCandidateMailEvaluationUpdateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateMailEvaluationUpdate> nil()) }

    void partnerOrganizationCandidateMailEvaluationUpdateSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateMailEvaluationUpdate> partnerOrganizationCandidateMailEvaluationUpdateSet) { setPartnerOrganizationCandidateMailEvaluationUpdateSet(new HashSet<>(partnerOrganizationCandidateMailEvaluationUpdateSet.toJavaList())) }

    static Option<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> findByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUriHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate, final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

        fromNull(findByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUri(partnerOrganizationCandidate, trustInteroperabilityProfileUri))
    }

    static org.gtri.fj.data.List<P3<PartnerOrganizationCandidate, TrustInteroperabilityProfileUri, Option<PartnerOrganizationCandidateTrustInteroperabilityProfileUri>>> findByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUriAndProtectedSystemHelper(
            final org.gtri.fj.data.List<PartnerOrganizationCandidate> partnerOrganizationCandidateList,
            final org.gtri.fj.data.List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriList,
            final org.gtri.fj.data.List<Organization> organizationList) {

        return fromNull(PartnerOrganizationCandidateTrustInteroperabilityProfileUri.executeQuery("SELECT DISTINCT partnerOrganizationCandidate, trustInteroperabilityProfileUri, partnerOrganizationCandidateTrustInteroperabilityProfileUri " +
                "FROM PartnerOrganizationCandidate partnerOrganizationCandidate, TrustInteroperabilityProfileUri trustInteroperabilityProfileUri " +
                "JOIN partnerOrganizationCandidate.trustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri " +
                "JOIN trustmarkBindingRegistryOrganizationMapUri.trustmarkBindingRegistryUri trustmarkBindingRegistryUri " +
                "JOIN trustmarkBindingRegistryUri.trustmarkBindingRegistrySet trustmarkBindingRegistry " +
                "JOIN trustmarkBindingRegistry.organization organization " +
                "JOIN trustInteroperabilityProfileUri.organizationTrustInteroperabilityProfileUriSet organizationTrustInteroperabilityProfileUri " +
                "JOIN organizationTrustInteroperabilityProfileUri.organization organizationTrustInteroperabilityProfileUriOrganization " +
                "LEFT JOIN PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri " +
                "ON partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidate = partnerOrganizationCandidate " +
                "AND partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri = trustInteroperabilityProfileUri " +
                "WHERE organization = organizationTrustInteroperabilityProfileUriOrganization " +
                (partnerOrganizationCandidateList.isNotEmpty() ? "AND (partnerOrganizationCandidate IN (:partnerOrganizationCandidateList)) " : "") +
                (trustInteroperabilityProfileUriList.isNotEmpty() ? "AND (trustInteroperabilityProfileUri IN (:trustInteroperabilityProfileUriList)) " : "") +
                (organizationList.isNotEmpty() ? "AND (organization IN (:organizationList)) " : "") +
                "ORDER BY partnerOrganizationCandidateTrustInteroperabilityProfileUri.evaluationAttemptLocalDateTime ",
                org.gtri.fj.data.TreeMap.iterableTreeMap(StringUtility.stringOrd, org.gtri.fj.data.Option.somes(org.gtri.fj.data.List.arrayList(
                        Option.iif(partnerOrganizationCandidateList.isNotEmpty(), P.p("partnerOrganizationCandidateList", partnerOrganizationCandidateList.toJavaList())),
                        Option.iif(trustInteroperabilityProfileUriList.isNotEmpty(), P.p("trustInteroperabilityProfileUriList", trustInteroperabilityProfileUriList.toJavaList())),
                        Option.iif(organizationList.isNotEmpty(), P.p("organizationList", organizationList.toJavaList()))))).toMutableMap()))
                .map({ list -> iterableList(list).map({ Object[] array -> P.p((PartnerOrganizationCandidate) array[0], (TrustInteroperabilityProfileUri) array[1], fromNull((PartnerOrganizationCandidateTrustInteroperabilityProfileUri) array[2])) }) })
                .orSome(org.gtri.fj.data.List.<P3<PartnerOrganizationCandidate, TrustInteroperabilityProfileUri, Option<PartnerOrganizationCandidateTrustInteroperabilityProfileUri>>> nil())
    }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    PartnerOrganizationCandidateTrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    PartnerOrganizationCandidateTrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> nil());
    }
}
