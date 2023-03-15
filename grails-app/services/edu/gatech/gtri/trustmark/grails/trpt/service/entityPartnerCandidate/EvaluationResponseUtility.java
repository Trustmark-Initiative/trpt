package edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Evaluation;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedEntity;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedEntityPartnerCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedEntityTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.product.P6;
import org.json.JSONObject;

import java.util.stream.Collectors;

import static edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.PartnerCandidateTrustInteroperabilityProfileResponse.partnerCandidateTrustInteroperabilityProfileResponseOrd;
import static edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility.stringFor;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.product.P.p;

public class EvaluationResponseUtility {

    private EvaluationResponseUtility() {
    }

    public static <T1 extends PartnerCandidate> EvaluationResponseWithTrustExpressionEvaluation evaluationResponseWithTrustExpressionEvaluation(
            final Evaluation<T1> evaluation,
            final boolean evaluationCurrent) {

        return new EvaluationResponseWithTrustExpressionEvaluation(
                evaluation.idHelper(),
                evaluation.getEvaluationAttemptLocalDateTime(),
                evaluation.getEvaluationLocalDateTime(),
                evaluation.getEvaluationTrustExpressionSatisfied(),
                evaluation.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                evaluation.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                evaluationCurrent,
                evaluation.getEvaluationTrustExpression() == null ? null : new JSONObject(stringFor(evaluation.getEvaluationTrustExpression())));
    }

    public static <T1 extends PartnerCandidate> EvaluationResponse evaluationResponse(
            final Evaluation<T1> evaluation,
            final boolean evaluationCurrent) {

        return new EvaluationResponse(evaluation.idHelper(),
                evaluation.getEvaluationAttemptLocalDateTime(),
                evaluation.getEvaluationLocalDateTime(),
                evaluation.getEvaluationTrustExpressionSatisfied(),
                evaluation.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                evaluation.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                evaluationCurrent);
    }

    public static ProtectedEntityTrustInteroperabilityProfileResponse entityTrustInteroperabilityProfileResponse(
            final ProtectedEntityTrustInteroperabilityProfileUri protectedEntityTrustInteroperabilityProfileUri) {

        return new ProtectedEntityTrustInteroperabilityProfileResponse(
                TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse(protectedEntityTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()),
                protectedEntityTrustInteroperabilityProfileUri.isMandatory());
    }

    public static <T1 extends PartnerCandidate, T2 extends PartnerCandidateResponse> ProtectedEntityPartnerCandidateResponse entityPartnerCandidateResponse(
            final ProtectedEntity protectedEntity,
            final T1 partnerCandidate,
            final F1<T1, T2> partnerCandidateResponseF,
            final F2<TrustInteroperabilityProfileUri, PartnerCandidate, List<Evaluation<T1>>> evaluationListF) {

        final P6<Boolean, Integer, Integer, Integer, Integer, List<Evaluation<T1>>> p = evaluationSummary(protectedEntity, trustInteroperabilityProfileUri -> evaluationListF.f(trustInteroperabilityProfileUri, partnerCandidate));

        return new ProtectedEntityPartnerCandidateResponse(
                partnerCandidateResponseF.f(partnerCandidate),
                p._1(),
                trust(protectedEntity, partnerCandidate),
                p._2(),
                p._3(),
                p._4(),
                p._5(),
                p._6()
                        .map(evaluation -> new PartnerCandidateTrustInteroperabilityProfileResponse(
                                TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse(evaluation.trustInteroperabilityProfileUriHelper()),
                                evaluationResponse(evaluation, true)))
                        .sort(partnerCandidateTrustInteroperabilityProfileResponseOrd)
                        .toJavaList());
    }

    public static <T1 extends PartnerCandidate, T2 extends PartnerCandidateResponse> ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation<T2> entityPartnerCandidateResponseWithTrustExpressionEvaluation(
            final ProtectedEntity protectedEntity,
            final T1 partnerCandidate,
            final F1<T1, T2> partnerCandidateResponseF,
            final F2<TrustInteroperabilityProfileUri, PartnerCandidate, List<Evaluation<T1>>> evaluationListF) {

        final P6<Boolean, Integer, Integer, Integer, Integer, List<Evaluation<T1>>> p = evaluationSummary(protectedEntity, trustInteroperabilityProfileUri -> evaluationListF.f(trustInteroperabilityProfileUri, partnerCandidate));

        return new ProtectedEntityPartnerCandidateResponseWithTrustExpressionEvaluation(
                partnerCandidateResponseF.f(partnerCandidate),
                p._1(),
                trust(protectedEntity, partnerCandidate),
                p._2(),
                p._3(),
                p._4(),
                p._5(),
                p._6()
                        .map(evaluation -> new PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation(
                                TrustInteroperabilityProfileUtility.trustInteroperabilityProfileResponse(evaluation.trustInteroperabilityProfileUriHelper()),
                                concat(
                                        of(evaluationResponseWithTrustExpressionEvaluation(evaluation, true)),
                                        evaluation
                                                .findAllByPartnerCandidateTrustInteroperabilityProfileUriHelper()
                                                .map(evaluationHistory -> evaluationResponseWithTrustExpressionEvaluation(evaluationHistory, false))
                                                .toJavaList()
                                                .stream())
                                        .collect(Collectors.toList())))
                        .sort(PartnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluation.partnerCandidateTrustInteroperabilityProfileResponseWithTrustExpressionEvaluationOrd)
                        .toJavaList());
    }

    private static <T1 extends PartnerCandidate> P6<Boolean, Integer, Integer, Integer, Integer, List<Evaluation<T1>>> evaluationSummary(
            final ProtectedEntity protectedEntity,
            final F1<TrustInteroperabilityProfileUri, List<Evaluation<T1>>> f) {

        return protectedEntity
                .protectedEntityTrustInteroperabilityProfileUriSetHelper()
                .bind(protectedEntityTrustInteroperabilityProfileUri -> f.f(protectedEntityTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                        .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> p(protectedEntityTrustInteroperabilityProfileUri.isMandatory(), partnerSystemCandidateTrustInteroperabilityProfileUri)))
                .foldLeft(
                        (pInner, p2) -> p(
                                pInner._1() && (!p2._1() || fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false)),
                                fromNull(p2._2().getEvaluationTrustmarkDefinitionRequirementSatisfied()).orSome(0) + pInner._2(),
                                fromNull(p2._2().getEvaluationTrustmarkDefinitionRequirementUnsatisfied()).orSome(0) + pInner._3(),
                                (fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false) ? 1 : 0) + pInner._4(),
                                (fromNull(p2._2().getEvaluationTrustExpressionSatisfied()).orSome(false) ? 0 : 1) + pInner._5(),
                                pInner._6().snoc(p2._2())),
                        p(true, 0, 0, 0, 0, nil()));
    }

    private static boolean trust(
            final ProtectedEntity protectedEntity,
            final PartnerCandidate partnerCandidate) {

        return protectedEntity.protectedEntityPartnerCandidateSetHelper()
                .map(ProtectedEntityPartnerCandidate::partnerCandidateHelper)
                .exists(partnerCandidateInner -> partnerCandidateInner.idHelper() == partnerCandidate.idHelper());
    }

}
