package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F8;
import org.gtri.fj.product.P6;
import org.json.JSONObject;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.stringFor;
import static edu.gatech.gtri.trustmark.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateUtility.partnerOrganizationCandidateResponse;
import static edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class OrganizationResponseUtility {

    private OrganizationResponseUtility() {
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
                        .map(OrganizationResponseUtility::organizationTrustInteroperabilityProfileResponse)
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
                        .map(OrganizationResponseUtility::organizationTrustInteroperabilityProfileResponse)
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
                OrganizationResponseUtility::partnerOrganizationCandidateTrustInteroperabilityProfileResponse,
                OrganizationPartnerOrganizationCandidateResponse::new,
                PartnerOrganizationCandidateTrustInteroperabilityProfileResponse::getTrustInteroperabilityProfile);
    }

    private static OrganizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation organizationPartnerOrganizationCandidateResponseWithTrustExpressionEvaluation(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return organizationPartnerOrganizationCandidateResponseHelper(
                organization,
                partnerOrganizationCandidate,
                OrganizationResponseUtility::partnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation,
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
                trustInteroperabilityProfileResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                evaluationResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri));
    }

    private static PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation partnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        return new PartnerOrganizationCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
                trustInteroperabilityProfileResponse(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                Stream.concat(
                                Stream.of(evaluationResponseWithTrustExpressionEvaluation(partnerOrganizationCandidateTrustInteroperabilityProfileUri)),
                                PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory
                                        .findAllByPartnerOrganizationCandidateTrustInteroperabilityProfileUriHelper(
                                                partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper(),
                                                partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                                        .map(OrganizationResponseUtility::evaluationResponseWithTrustExpressionEvaluation)
                                        .toJavaList()
                                        .stream())
                        .collect(Collectors.toList()));
    }

    public static EvaluationResponseWithTrustExpressionEvaluation evaluationResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        return new EvaluationResponseWithTrustExpressionEvaluation(
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.idHelper(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                true,
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression())));
    }

    public static EvaluationResponseWithTrustExpressionEvaluation evaluationResponseWithTrustExpressionEvaluation(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory) {

        return new EvaluationResponseWithTrustExpressionEvaluation(
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.idHelper(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                false,
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpression())));
    }

    public static EvaluationResponse evaluationResponse(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        return new EvaluationResponse(
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.idHelper(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                true);
    }

    public static EvaluationResponse evaluationResponse(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory) {

        return new EvaluationResponse(
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.idHelper(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationAttemptLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationLocalDateTime(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpressionSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                false);
    }
}
