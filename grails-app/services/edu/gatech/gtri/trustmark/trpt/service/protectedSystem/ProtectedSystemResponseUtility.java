package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemPartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.EvaluationResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileResponse;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F6;
import org.gtri.fj.function.F8;
import org.gtri.fj.product.P6;
import org.json.JSONObject;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.stringFor;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponseUtility.organizationResponse;
import static edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateUtility.partnerSystemCandidateResponse;
import static edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class ProtectedSystemResponseUtility {
    private ProtectedSystemResponseUtility() {
    }

    public static ProtectedSystemTypeResponse protectedSystemTypeResponse(
            final ProtectedSystemType protectedSystemType) {

        return new ProtectedSystemTypeResponse(
                protectedSystemType.name(),
                protectedSystemType.getName());
    }

    public static ProtectedSystemResponse protectedSystemResponse(
            final ProtectedSystem protectedSystem) {

        return protectedSystemResponseHelper(
                protectedSystem,
                PartnerSystemCandidate
                        .findAllByOrganizationInAndTypeInHelper(arrayList(protectedSystem.organizationHelper()), protectedSystem.getType().getPartnerSystemCandidateTypeList())
                        .map(partnerSystemCandidate -> protectedSystemPartnerSystemCandidateResponse(protectedSystem, partnerSystemCandidate))
                        .sort(ord((o1, o2) -> stringOrd.compare(o1.getPartnerCandidate().getName(), o2.getPartnerCandidate().getName())))
                        .toJavaList(),
                ProtectedSystemResponse::new);
    }

    public static ProtectedSystemResponseWithTrustExpressionEvaluation protectedSystemResponseWithTrustExpressionEvaluation(
            final ProtectedSystem protectedSystem,
            final PartnerSystemCandidate partnerSystemCandidate) {

        return protectedSystemResponseHelper(
                protectedSystem,
                arrayList(protectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation(protectedSystem, partnerSystemCandidate)).toJavaList(),
                ProtectedSystemResponseWithTrustExpressionEvaluation::new);
    }

    private static <T1, T2> T2 protectedSystemResponseHelper(
            final ProtectedSystem protectedSystem,
            final T1 responseInner,
            final F6<OrganizationResponse, Long, ProtectedSystemTypeResponse, String, java.util.List<ProtectedSystemTrustInteroperabilityProfileResponse>, T1, T2> fResponse) {

        return fResponse.f(
                organizationResponse(protectedSystem.organizationHelper()),
                protectedSystem.idHelper(),
                protectedSystemTypeResponse(protectedSystem.getType()),
                protectedSystem.getName(),
                protectedSystem
                        .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                        .map(ProtectedSystemResponseUtility::protectedSystemTrustInteroperabilityProfileResponse)
                        .sort(ord((o1, o2) -> o1.getTrustInteroperabilityProfile().getName() != null && o2.getTrustInteroperabilityProfile().getName() != null ?
                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getName(), o2.getTrustInteroperabilityProfile().getName()) :
                                o1.getTrustInteroperabilityProfile().getName() != null ?
                                        Ordering.LT :
                                        o2.getTrustInteroperabilityProfile().getName() != null ?
                                                Ordering.GT :
                                                stringOrd.compare(o1.getTrustInteroperabilityProfile().getUri(), o2.getTrustInteroperabilityProfile().getUri())))
                        .toJavaList(),
                responseInner);
    }

    private static ProtectedSystemPartnerSystemCandidateResponse protectedSystemPartnerSystemCandidateResponse(
            final ProtectedSystem protectedSystem,
            final PartnerSystemCandidate partnerSystemCandidate) {

        return protectedSystemPartnerSystemCandidateResponseHelper(
                protectedSystem,
                partnerSystemCandidate,
                ProtectedSystemResponseUtility::partnerSystemCandidateTrustInteroperabilityProfileResponse,
                ProtectedSystemPartnerSystemCandidateResponse::new,
                PartnerSystemCandidateTrustInteroperabilityProfileResponse::getTrustInteroperabilityProfile);
    }

    private static ProtectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation protectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation(
            final ProtectedSystem protectedSystem,
            final PartnerSystemCandidate partnerSystemCandidate) {

        return protectedSystemPartnerSystemCandidateResponseHelper(
                protectedSystem,
                partnerSystemCandidate,
                ProtectedSystemResponseUtility::partnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation,
                ProtectedSystemPartnerSystemCandidateResponseWithTrustExpressionEvaluation::new,
                PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation::getTrustInteroperabilityProfile);
    }

    private static <T1, T2> T2 protectedSystemPartnerSystemCandidateResponseHelper(
            final ProtectedSystem protectedSystem,
            final PartnerSystemCandidate partnerSystemCandidate,
            final F1<PartnerSystemCandidateTrustInteroperabilityProfileUri, T1> fResponseInner,
            final F8<PartnerSystemCandidateResponse, Boolean, Boolean, Integer, Integer, Integer, Integer, java.util.List<T1>, T2> fResponse,
            final F1<T1, TrustInteroperabilityProfileResponse> getTrustInteroperabilityProfile) {

        final P6<Boolean, Integer, Integer, Integer, Integer, List<T1>> p = protectedSystem
                .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                .bind(protectedSystemTrustInteroperabilityProfileUri -> protectedSystemTrustInteroperabilityProfileUri
                        .trustInteroperabilityProfileUriHelper()
                        .partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper()
                        .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper() == partnerSystemCandidate.idHelper())
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
                partnerSystemCandidateResponse(partnerSystemCandidate),
                p._1(),
                protectedSystem.protectedSystemPartnerSystemCandidateSetHelper()
                        .map(ProtectedSystemPartnerSystemCandidate::partnerSystemCandidateHelper)
                        .exists(partnerSystemCandidateInner -> partnerSystemCandidateInner.idHelper() == partnerSystemCandidate.idHelper()),
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

    private static PartnerSystemCandidateTrustInteroperabilityProfileResponse partnerSystemCandidateTrustInteroperabilityProfileResponse(
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) {

        return new PartnerSystemCandidateTrustInteroperabilityProfileResponse(
                trustInteroperabilityProfileResponse(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                evaluationResponse(partnerSystemCandidateTrustInteroperabilityProfileUri));
    }

    private static PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation partnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) {

        return new PartnerSystemCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
                trustInteroperabilityProfileResponse(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                Stream.concat(
                                Stream.of(evaluationResponseWithTrustExpressionEvaluation(partnerSystemCandidateTrustInteroperabilityProfileUri)),
                                PartnerSystemCandidateTrustInteroperabilityProfileUriHistory
                                        .findAllByPartnerSystemCandidateTrustInteroperabilityProfileUriHelper(
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                                        .map(ProtectedSystemResponseUtility::evaluationResponseWithTrustExpressionEvaluation)
                                        .toJavaList()
                                        .stream())
                        .collect(Collectors.toList()));
    }

    private static ProtectedSystemTrustInteroperabilityProfileResponse protectedSystemTrustInteroperabilityProfileResponse(
            final ProtectedSystemTrustInteroperabilityProfileUri protectedSystemTrustInteroperabilityProfileUri) {

        return new ProtectedSystemTrustInteroperabilityProfileResponse(
                trustInteroperabilityProfileResponse(protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                protectedSystemTrustInteroperabilityProfileUri.isMandatory());
    }

    public static EvaluationResponseWithTrustExpressionEvaluation evaluationResponseWithTrustExpressionEvaluation(
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) {

        return new EvaluationResponseWithTrustExpressionEvaluation(partnerSystemCandidateTrustInteroperabilityProfileUri.idHelper(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                true,
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpression())));
    }

    public static EvaluationResponseWithTrustExpressionEvaluation evaluationResponseWithTrustExpressionEvaluation(
            final PartnerSystemCandidateTrustInteroperabilityProfileUriHistory partnerSystemCandidateTrustInteroperabilityProfileUriHistory) {

        return new EvaluationResponseWithTrustExpressionEvaluation(partnerSystemCandidateTrustInteroperabilityProfileUriHistory.idHelper(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationAttemptLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpressionSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                false,
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpression())));
    }

    public static EvaluationResponse evaluationResponse(
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) {

        return new EvaluationResponse(partnerSystemCandidateTrustInteroperabilityProfileUri.idHelper(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationAttemptLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustExpressionSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                true);
    }

    public static EvaluationResponse evaluationResponse(
            final PartnerSystemCandidateTrustInteroperabilityProfileUriHistory partnerSystemCandidateTrustInteroperabilityProfileUriHistory) {

        return new EvaluationResponse(partnerSystemCandidateTrustInteroperabilityProfileUriHistory.idHelper(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationAttemptLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationLocalDateTime(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustExpressionSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                partnerSystemCandidateTrustInteroperabilityProfileUriHistory.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                false);
    }
}
