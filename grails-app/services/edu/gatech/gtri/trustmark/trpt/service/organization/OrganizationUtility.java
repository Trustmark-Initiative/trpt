package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P5;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByNameHelper;
import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByUriHelper;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.gunzip;
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


    private static OrganizationTrustInteroperabilityProfileResponse organizationTrustInteroperabilityProfileResponse(
            final OrganizationTrustInteroperabilityProfileUri organizationTrustInteroperabilityProfileUri) {

        return new OrganizationTrustInteroperabilityProfileResponse(
                trustInteroperabilityProfileResponse(organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                organizationTrustInteroperabilityProfileUri.isMandatory());
    }

    private static OrganizationPartnerOrganizationCandidateResponse organizationPartnerOrganizationCandidateResponse(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        final P5<Integer, Integer, Integer, Integer, List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponse>> p = organization
                .organizationTrustInteroperabilityProfileUriSetHelper()
                .map(OrganizationTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper)
                .bind(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri
                        .partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().idHelper() == partnerOrganizationCandidate.idHelper()))
                .foldLeft((pInner, partnerOrganizationCandidateTrustInteroperabilityProfileUri) -> p(
                                fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._1(),
                                fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._2(),
                                (fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._3(),
                                (fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._4(),
                                pInner._5().snoc(partnerOrganizationCandidateTrustInteroperabilityProfileResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri))),
                        p(0, 0, 0, 0, nil()));

        return new OrganizationPartnerOrganizationCandidateResponse(
                partnerOrganizationCandidateResponse(partnerOrganizationCandidate),
                organization.organizationPartnerOrganizationCandidateSetHelper()
                        .map(OrganizationPartnerOrganizationCandidate::partnerOrganizationCandidateHelper)
                        .exists(partnerOrganizationCandidateInner -> partnerOrganizationCandidateInner.idHelper() == partnerOrganizationCandidate.idHelper()),
                p._1(),
                p._2(),
                p._3(),
                p._4(),
                p._5()
                        .sort(ord((o1, o2) -> o1.getTrustInteroperabilityProfile().getName() != null && o2.getTrustInteroperabilityProfile().getName() != null ?
                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getName(), o2.getTrustInteroperabilityProfile().getName()) :
                                o1.getTrustInteroperabilityProfile().getName() != null ?
                                        Ordering.LT :
                                        o2.getTrustInteroperabilityProfile().getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getUri(), o2.getTrustInteroperabilityProfile().getUri())))
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

    private static OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation organizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        final P5<Integer, Integer, Integer, Integer, List<PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation>> p = organization
                .organizationTrustInteroperabilityProfileUriSetHelper()
                .map(OrganizationTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper)
                .bind(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri
                        .partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().idHelper() == partnerOrganizationCandidate.idHelper()))
                .foldLeft((pInner, partnerOrganizationCandidateTrustInteroperabilityProfileUri) -> p(
                                fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._1(),
                                fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._2(),
                                (fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._3(),
                                (fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._4(),
                                pInner._5().snoc(partnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(partnerOrganizationCandidateTrustInteroperabilityProfileUri))),
                        p(0, 0, 0, 0, nil()));

        return new OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
                partnerOrganizationCandidateResponse(partnerOrganizationCandidate),
                organization.organizationPartnerOrganizationCandidateSetHelper()
                        .map(OrganizationPartnerOrganizationCandidate::partnerOrganizationCandidateHelper)
                        .exists(partnerOrganizationCandidateInner -> partnerOrganizationCandidateInner.idHelper() == partnerOrganizationCandidate.idHelper()),
                p._1(),
                p._2(),
                p._3(),
                p._4(),
                p._5()
                        .sort(ord((o1, o2) -> o1.getTrustInteroperabilityProfile().getName() != null && o2.getTrustInteroperabilityProfile().getName() != null ?
                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getName(), o2.getTrustInteroperabilityProfile().getName()) :
                                o1.getTrustInteroperabilityProfile().getName() != null ?
                                        Ordering.LT :
                                        o2.getTrustInteroperabilityProfile().getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getUri(), o2.getTrustInteroperabilityProfile().getUri())))
                        .toJavaList());
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
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression() == null ? null : gunzip(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression()).toOption().map(JSONObject::new).toNull());
    }

}
