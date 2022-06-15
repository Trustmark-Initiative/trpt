package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F5;
import org.gtri.fj.function.F6;
import org.gtri.fj.function.F7;
import org.gtri.fj.function.F8;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P6;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByNameHelper;
import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByUriHelper;
import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.stringFor;
import static edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateUtility.partnerOrganizationCandidateResponse;
import static edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeEmpty;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.lang.LongUtility.longOrd;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class OrganizationUtility {

    private OrganizationUtility() {
    }

    public static OrganizationResponse organizationResponse(final Organization organization) {
        return new OrganizationResponse(
                organization.idHelper(),
                organization.getName(),
                organization.getDescription(),
                organization.getUri());
    }

    public static OrganizationResponseWithDetail organizationResponseWithDetail(final Organization organization) {

        return new OrganizationResponseWithDetail(
                organization.idHelper(),
                organization.getName(),
                organization.getDescription(),
                organization.getUri(),
                organization
                        .organizationTrustInteroperabilityProfileUriSetHelper()
                        .map(OrganizationUtility::organizationTrustInteroperabilityProfileResponse)
                        .sort(ord((o1, o2) -> o1.getTrustInteroperabilityProfile().getName() != null && o2.getTrustInteroperabilityProfile().getName() != null ?
                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getName(), o2.getTrustInteroperabilityProfile().getName()) :
                                o1.getTrustInteroperabilityProfile().getName() != null ?
                                        Ordering.LT :
                                        o2.getTrustInteroperabilityProfile().getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getUri(), o2.getTrustInteroperabilityProfile().getUri())))
                        .toJavaList(),
                PartnerOrganizationCandidate
                        .findAllByOrganizationInHelper(arrayList(organization))
                        .map(partnerSystemCandidate -> organizationPartnerOrganizationCandidateResponse(organization, partnerSystemCandidate))
                        .sort(ord((o1, o2) -> stringOrd.compare(o1.getPartnerOrganizationCandidate().getName(), o2.getPartnerOrganizationCandidate().getName())))
                        .toJavaList());
    }

    public static OrganizationResponseWithTrustExpressionEvaluation organizationResponseWithTrustExpressionEvaluation(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return new OrganizationResponseWithTrustExpressionEvaluation(
                organizationResponse(organization),
                organization.idHelper(),
                organization.getName(),
                organization
                        .organizationTrustInteroperabilityProfileUriSetHelper()
                        .map(OrganizationUtility::organizationTrustInteroperabilityProfileResponse)
                        .sort(ord((o1, o2) -> o1.getTrustInteroperabilityProfile().getName() != null && o2.getTrustInteroperabilityProfile().getName() != null ?
                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getName(), o2.getTrustInteroperabilityProfile().getName()) :
                                o1.getTrustInteroperabilityProfile().getName() != null ?
                                        Ordering.LT :
                                        o2.getTrustInteroperabilityProfile().getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getUri(), o2.getTrustInteroperabilityProfile().getUri())))
                        .toJavaList(),
                arrayList(organizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(organization, partnerOrganizationCandidate)).toJavaList());
    }

    private static OrganizationTrustInteroperabilityProfileResponse organizationTrustInteroperabilityProfileResponse(
            final OrganizationTrustInteroperabilityProfileUri organizationTrustInteroperabilityProfileUri) {

        return new OrganizationTrustInteroperabilityProfileResponse(
                trustInteroperabilityProfileResponse(organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                organizationTrustInteroperabilityProfileUri.isMandatory());
    }

    private static OrganizationPartnerOrganizationCandidateResponse organizationPartnerOrganizationCandidateResponse(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return organizationPartnerOrganizationCandidateResponseHelper(
                organization,
                partnerOrganizationCandidate,
                OrganizationUtility::partnerOrganizationCandidateTrustInteroperabilityProfileResponse,
                OrganizationPartnerOrganizationCandidateResponse::new,
                PartnerOrganizationCandidateTrustInteroperabilityProfileResponse::getTrustInteroperabilityProfile);
    }

    private static OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation organizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return organizationPartnerOrganizationCandidateResponseHelper(
                organization,
                partnerOrganizationCandidate,
                OrganizationUtility::partnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation,
                OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation::new,
                PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation::getTrustInteroperabilityProfile);
    }

    private static <T1, T2> T2 organizationPartnerOrganizationCandidateResponseHelper(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate,
            final F1<PartnerOrganizationCandidateTrustInteroperabilityProfileUri, T1> fResponseInner,
            final F8<PartnerOrganizationCandidateResponse, Boolean, Boolean, Integer, Integer, Integer, Integer, java.util.List<T1>, T2> fResponse,
            final F1<T1, TrustInteroperabilityProfileResponse> getTrustInteroperabilityProfile) {

        final P6<Boolean, Integer, Integer, Integer, Integer, List<T1>> p = organization
                .organizationTrustInteroperabilityProfileUriSetHelper()
                .bind(protectedSystemTrustInteroperabilityProfileUri -> protectedSystemTrustInteroperabilityProfileUri
                        .trustInteroperabilityProfileUriHelper()
                        .partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().idHelper() == partnerOrganizationCandidate.idHelper())
                        .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> p(protectedSystemTrustInteroperabilityProfileUri.isMandatory(), partnerSystemCandidateTrustInteroperabilityProfileUri)))
                .foldLeft((pInner, p2) -> p(
                                pInner._1() && (!p2._1() || fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false)),
                                fromNull(p2._2().getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._2(),
                                fromNull(p2._2().getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._3(),
                                (fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._4(),
                                (fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._5(),
                                pInner._6().snoc(fResponseInner.f(p2._2()))),
                        p(true, 0, 0, 0, 0, nil()));

        return fResponse.f(
                partnerOrganizationCandidateResponse(partnerOrganizationCandidate),
                p._1(),
                organization.organizationPartnerOrganizationCandidateSetHelper()
                        .map(OrganizationPartnerOrganizationCandidate::partnerOrganizationCandidateHelper)
                        .exists(partnerSystemCandidateInner -> partnerSystemCandidateInner.idHelper() == partnerOrganizationCandidate.idHelper()),
                p._2(),
                p._3(),
                p._4(),
                p._5(),
                p._6()
                        .sort(ord((o1, o2) -> getTrustInteroperabilityProfile.f(o1).getName() != null && getTrustInteroperabilityProfile.f(o2).getName() != null ?
                                stringOrd.compare(getTrustInteroperabilityProfile.f(o1).getName(), getTrustInteroperabilityProfile.f(o2).getName()) :
                                getTrustInteroperabilityProfile.f(o1).getName() != null ?
                                        Ordering.LT :
                                        getTrustInteroperabilityProfile.f(o2).getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(getTrustInteroperabilityProfile.f(o1).getUri(), getTrustInteroperabilityProfile.f(o2).getUri())))
                        .toJavaList());
    }

    private static PartnerOrganizationCandidateTrustInteroperabilityProfileResponse partnerOrganizationCandidateTrustInteroperabilityProfileResponse(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        return new PartnerOrganizationCandidateTrustInteroperabilityProfileResponse(
                TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied());
    }

    private static PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation partnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        return new PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
                TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression())));
    }

    private static boolean trustable() {
        return false;
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationName(final String name) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.name, Organization::findByNameHelper, 1, 1000, name);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationName(final long id, final String name) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.name, nameInner -> findByNameHelper(nameInner).filter(organization -> organization.idHelper() != id), 1, 1000, name);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationUri(final String uri) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.uri, Organization::findByUriHelper, 1, 1000, uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationUri(final long id, final String uri) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.uri, uriInner -> findByUriHelper(uriInner).filter(organization -> organization.idHelper() != id), 1, 1000, uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationDescription(final String description) {

        return mustBeNullOr(OrganizationField.description, description, (field, value) -> mustBeNonNullAndLength(field, 1, 1000, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Organization> validationId(final long id, final List<Organization> organizationList) {

        return mustBeReference(OrganizationField.id, Organization::findByIdHelper, id, organizationList, equal((o1, o2) -> o1.idHelper() == o2.idHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<Organization>> validationIdList(final List<Long> idList, final List<Organization> organizationList) {

        return mustBeNonNullAndDistinctAndValid(OrganizationField.idList, idList, longOrd, id -> validationId(id, organizationList)
                .bind(organization -> accumulate(
                        mustBeEmpty(OrganizationField.userSet, organization.userSetHelper()),
                        mustBeEmpty(OrganizationField.protectedSystemSet, organization.protectedSystemSetHelper()),
                        mustBeEmpty(OrganizationField.trustmarkBindingRegistrySet, organization.trustmarkBindingRegistrySetHelper()),
                        (userSet, protectedSystemSet, trustmarkBindingRegistrySet) -> organization)));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>>> validationOrganizationTrustInteroperabilityProfileList(final List<OrganizationTrustInteroperabilityProfileUpsertRequest> organizationTrustInteroperabilityProfileList) {

        return mustBeNonNullAndDistinctAndValid(
                OrganizationField.organizationTrustInteroperabilityProfileList,
                organizationTrustInteroperabilityProfileList,
                ord((o1, o2) -> o1.getUri() == null && o2.getUri() == null ?
                        Ordering.EQ :
                        o1.getUri() == null ?
                                Ordering.LT :
                                o2.getUri() == null ?
                                        Ordering.GT :
                                        stringOrd.compare(o1.getUri(), o2.getUri())),
                nonNullAndDistinct -> mustBeNonNullAndLength(
                        OrganizationField.uri,
                        1,
                        1000,
                        nonNullAndDistinct.getUri())
                        .map(uri -> p(TrustInteroperabilityProfileUri.findByUriHelper(uri).toEither(uri), nonNullAndDistinct.isMandatory())));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<PartnerOrganizationCandidate>> validationPartnerOrganizationCandidateList(final List<Long> partnerOrganizationCandidateList) {

        return mustBeNonNullAndDistinctAndValid(
                OrganizationField.partnerOrganizationCandidateList,
                partnerOrganizationCandidateList,
                longOrd,
                nonNullAndDistinct -> validationPartnerOrganizationCandidate(nonNullAndDistinct));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, PartnerOrganizationCandidate> validationPartnerOrganizationCandidate(final long partnerOrganizationCandidate) {

        return mustBeReference(
                OrganizationField.partnerOrganizationCandidate,
                PartnerOrganizationCandidate::findByIdHelper,
                partnerOrganizationCandidate);
    }
}
