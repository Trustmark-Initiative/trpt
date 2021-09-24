package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemPartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P4;
import org.gtri.fj.product.P5;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.organizationResponse;
import static edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateUtility.partnerSystemCandidateResponse;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.TreeMap.treeMap;
import static org.gtri.fj.lang.LongUtility.longOrd;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class ProtectedSystemUtility {
    private ProtectedSystemUtility() {
    }

    public static ProtectedSystemResponse protectedSystemResponse(final ProtectedSystem protectedSystem) {

        return new ProtectedSystemResponse(
                organizationResponse(protectedSystem.organizationHelper()),
                protectedSystem.idHelper(),
                protectedSystemTypeResponse(protectedSystem.getType()),
                protectedSystem.getName(),
                protectedSystem
                        .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                        .map(ProtectedSystemUtility::protectedSystemTrustInteroperabilityProfileResponse)
                        .sort(ord((o1, o2) -> stringOrd.compare(o1.getUri(), o2.getUri())))
                        .toJavaList(),
                PartnerSystemCandidate
                        .findAllByTypeInHelper(protectedSystem.getType().getPartnerSystemCandidateTypeList())
                        .map(partnerSystemCandidate -> protectedSystemPartnerSystemCandidateSummaryResponse(protectedSystem, partnerSystemCandidate))
                        .sort(ord((o1, o2) -> stringOrd.compare(o1.getPartnerSystemCandidate().getName(), o2.getPartnerSystemCandidate().getName())))
                        .toJavaList());
    }

    public static ProtectedSystemTypeResponse protectedSystemTypeResponse(final ProtectedSystemType protectedSystemType) {
        return new ProtectedSystemTypeResponse(
                protectedSystemType.name(),
                protectedSystemType.getName());
    }

    public static ProtectedSystemTrustInteroperabilityProfileResponse protectedSystemTrustInteroperabilityProfileResponse(final ProtectedSystemTrustInteroperabilityProfileUri protectedSystemTrustInteroperabilityProfileUri) {
        return new ProtectedSystemTrustInteroperabilityProfileResponse(
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().idHelper(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getName(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDescription(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getPublicationLocalDateTime(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getIssuerName(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getIssuerIdentifier(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getRequestLocalDateTime(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getSuccessLocalDateTime(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getFailureLocalDateTime(),
                protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getFailureMessage(),
                protectedSystemTrustInteroperabilityProfileUri.isMandatory());
    }

    public static ProtectedSystemPartnerSystemCandidateSummaryResponse protectedSystemPartnerSystemCandidateSummaryResponse(final ProtectedSystem protectedSystem, final PartnerSystemCandidate partnerSystemCandidate) {

        final P4<Integer, Integer, Integer, Integer> p = protectedSystem
                .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                .map(ProtectedSystemTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper)
                .bind(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri
                        .partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper() == partnerSystemCandidate.idHelper()))
                .foldLeft((pInner, partnerSystemCandidateTrustInteroperabilityProfileUri) -> p(
                                fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._1(),
                                fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._2(),
                                (fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._3(),
                                (fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._4()),
                        p(0, 0, 0, 0));

        return new ProtectedSystemPartnerSystemCandidateSummaryResponse(
                partnerSystemCandidateResponse(partnerSystemCandidate),
                protectedSystem.protectedSystemPartnerSystemCandidateSetHelper()
                        .map(ProtectedSystemPartnerSystemCandidate::partnerSystemCandidateHelper)
                        .exists(partnerSystemCandidateInner -> partnerSystemCandidateInner.idHelper() == partnerSystemCandidate.idHelper()),
                p._1(),
                p._2(),
                p._3(),
                p._4());
    }

    public static ProtectedSystemPartnerSystemCandidateResponse protectedSystemPartnerSystemCandidateResponse(final ProtectedSystem protectedSystem, final PartnerSystemCandidate partnerSystemCandidate) {

        final P5<Integer, Integer, Integer, Integer, TreeMap<String, JSONObject>> p = protectedSystem
                .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                .map(ProtectedSystemTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper)
                .bind(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri
                        .partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper() == partnerSystemCandidate.idHelper()))
                .foldLeft((pInner, partnerSystemCandidateTrustInteroperabilityProfileUri) -> p(
                                fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._1(),
                                fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._2(),
                                (fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._3(),
                                (fromNull(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._4(),
                                pInner._5().set(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(), partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression() == null ? null : new JSONObject(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression()))),
                        p(0, 0, 0, 0, treeMap(stringOrd)));

        return new ProtectedSystemPartnerSystemCandidateResponse(
                protectedSystem.organizationHelper().getName(),
                protectedSystem.getName(),
                partnerSystemCandidate.getName(),
                partnerSystemCandidateResponse(partnerSystemCandidate),
                protectedSystem.protectedSystemPartnerSystemCandidateSetHelper()
                        .map(ProtectedSystemPartnerSystemCandidate::partnerSystemCandidateHelper)
                        .exists(partnerSystemCandidateInner -> partnerSystemCandidateInner.idHelper() == partnerSystemCandidate.idHelper()),
                p._1(),
                p._2(),
                p._3(),
                p._4(),
                p._5().toMutableMap());
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, Organization> validationOrganization(final long organizationId) {

        return mustBeReference(ProtectedSystemField.organization, Organization::findByIdHelper, organizationId);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, String> validationName(final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                ProtectedSystemField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> ProtectedSystem.findByOrganizationAndNameHelper(organization, nameInner)),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, String> validationName(final long id, final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                ProtectedSystemField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> ProtectedSystem.findByOrganizationAndNameHelper(organization, nameInner))
                        .filter(protectedSystem -> protectedSystem.idHelper() != id),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>>> validationProtectedSystemTrustInteroperabilityProfileList(final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList) {

        return mustBeNonNullAndDistinctAndValid(
                ProtectedSystemField.protectedSystemTrustInteroperabilityProfileList,
                protectedSystemTrustInteroperabilityProfileList,
                ord((o1, o2) -> o1.getUri() == null && o2.getUri() == null ?
                        Ordering.EQ :
                        o1.getUri() == null ?
                                Ordering.LT :
                                o2.getUri() == null ?
                                        Ordering.GT :
                                        stringOrd.compare(o1.getUri(), o2.getUri())),
                nonNullAndDistinct -> mustBeNonNullAndLength(
                        ProtectedSystemField.uri,
                        1,
                        1000,
                        nonNullAndDistinct.getUri())
                        .map(uri -> p(TrustInteroperabilityProfileUri.findByUriHelper(uri).toEither(uri), nonNullAndDistinct.isMandatory())));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<PartnerSystemCandidate>> validationPartnerSystemCandidateList(final List<Long> partnerSystemCandidateList) {

        return mustBeNonNullAndDistinctAndValid(
                ProtectedSystemField.partnerSystemCandidateList,
                partnerSystemCandidateList,
                longOrd,
                nonNullAndDistinct -> validationPartnerSystemCandidate(nonNullAndDistinct));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, PartnerSystemCandidate> validationPartnerSystemCandidate(final long partnerSystemCandidate) {

        return mustBeReference(
                ProtectedSystemField.partnerSystemCandidate,
                PartnerSystemCandidate::findByIdHelper,
                partnerSystemCandidate);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemType> validationType(final ProtectedSystemType protectedSystemType) {

        return mustBeNonNull(ProtectedSystemField.type, protectedSystemType);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystem> validationId(final long id) {

        return mustBeReference(ProtectedSystemField.id, ProtectedSystem::findByIdHelper, id);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<ProtectedSystem>> validationIdList(final List<Long> idList) {

        return mustBeNonNullAndDistinctAndValid(ProtectedSystemField.idList, idList, longOrd, ProtectedSystemUtility::validationId);
    }
}
