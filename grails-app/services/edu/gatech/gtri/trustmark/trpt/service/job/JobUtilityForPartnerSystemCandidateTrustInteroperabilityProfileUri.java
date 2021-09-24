package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator.TrustExpressionEvaluatorImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluatorImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.parser.TrustExpressionParserImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.Ordering;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.reduce;

public class JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri {

    private JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.class);

    private static final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = new DatabaseCacheTrustInteroperabilityProfileResolver();
    private static final TrustmarkDefinitionResolver trustmarkDefinitionResolver = new DatabaseCacheTrustmarkDefinitionResolver();
    private static final TrustmarkResolver trustmarkResolver = new DatabaseCacheTrustmarkResolver();

    private static final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustInteroperabilityProfileResolver, trustmarkDefinitionResolver);
    private static final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(trustmarkResolver, trustExpressionParser);
    private static final TrustmarkDefinitionRequirementEvaluator trustmarkDefinitionRequirementEvaluator = new TrustmarkDefinitionRequirementEvaluatorImpl(trustmarkResolver, trustExpressionParser);

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducerForTrustExpressionEvaluation = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirementEvaluation, JSONObject> jsonProducerForTrustmarkDefinitionRequirementEvaluation = jsonManager.findProducerStrict(TrustmarkDefinitionRequirementEvaluation.class, JSONObject.class).some();

    public static void synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustInteroperabilityProfileUri.withTransactionHelper(() -> TrustInteroperabilityProfileUri.findAllHelper()
                        .bind(trustInteroperabilityProfileUri -> PartnerSystemCandidate.findAllHelper()
                                .map(partnerSystemCandidate -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(partnerSystemCandidate, trustInteroperabilityProfileUri)
                                        .orSome(() -> {
                                            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri = new PartnerSystemCandidateTrustInteroperabilityProfileUri();
                                            partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(partnerSystemCandidate);
                                            partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(trustInteroperabilityProfileUri);
                                            return partnerSystemCandidateTrustInteroperabilityProfileUri.saveHelper();
                                        }))))
                .sort(ord((o1, o2) ->
                        o1.getEvaluationAttemptLocalDateTime() == null && o2.getEvaluationAttemptLocalDateTime() == null ? Ordering.EQ :
                                o1.getEvaluationAttemptLocalDateTime() == null ? Ordering.LT :
                                        o2.getEvaluationAttemptLocalDateTime() == null ? Ordering.GT :
                                                Ordering.fromInt(o1.getEvaluationAttemptLocalDateTime().compareTo(o2.getEvaluationAttemptLocalDateTime()))))
                .forEach(partnerSystemCandidateTrustInteroperabilityProfileUriOuter ->
                        PartnerSystemCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(
                                                partnerSystemCandidateTrustInteroperabilityProfileUriOuter.partnerSystemCandidateHelper(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUriOuter.trustInteroperabilityProfileUriHelper())
                                        .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> {

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationAttemptLocalDateTime(now);

                                            return partnerSystemCandidateTrustInteroperabilityProfileUri.saveAndFlushHelper();
                                        })
                                        .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> {

                                            partnerSystemCandidateTrustInteroperabilityProfileUri
                                                    .trustInteroperabilityProfileUriHelper()
                                                    .getChangeLocalDateTime();

                                            partnerSystemCandidateTrustInteroperabilityProfileUri
                                                    .partnerSystemCandidateHelper()
                                                    .partnerSystemCandidateTrustmarkUriSetHelper()
                                                    .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                                                    .forEach(trustmarkUri -> trustmarkUri.getUri());

                                            return partnerSystemCandidateTrustInteroperabilityProfileUri;
                                        }))
                                .forEach(partnerSystemCandidateTrustInteroperabilityProfileUri -> {

                                    if (partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getChangeLocalDateTime() == null ||
                                            partnerSystemCandidateTrustInteroperabilityProfileUri
                                                    .partnerSystemCandidateHelper()
                                                    .partnerSystemCandidateTrustmarkUriSetHelper()
                                                    .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                                                    .exists(trustmarkUri -> trustmarkUri.getChangeLocalDateTime() == null)) {

                                        log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' waiting for dependency.",
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

                                    } else if (partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime() == null ||
                                            partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getChangeLocalDateTime()) ||
                                            partnerSystemCandidateTrustInteroperabilityProfileUri
                                                    .partnerSystemCandidateHelper()
                                                    .partnerSystemCandidateTrustmarkUriSetHelper()
                                                    .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                                                    .exists(trustmarkUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustmarkUri.getChangeLocalDateTime()))) {

                                        log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; re-evaluating...",
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

                                        final TrustExpressionEvaluation trustExpressionEvaluation = trustExpressionEvaluator.evaluate(
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri
                                                        .partnerSystemCandidateHelper()
                                                        .partnerSystemCandidateTrustmarkUriSetHelper()
                                                        .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                                                        .map(TrustmarkUri::getUri));

                                        final String trustExpressionEvaluationJsonString = jsonProducerForTrustExpressionEvaluation
                                                .serialize(trustExpressionEvaluation)
                                                .toString();

                                        final TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation = trustmarkDefinitionRequirementEvaluator.evaluate(
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri
                                                        .partnerSystemCandidateHelper()
                                                        .partnerSystemCandidateTrustmarkUriSetHelper()
                                                        .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                                                        .map(TrustmarkUri::getUri));

                                        final String trustmarkDefinitionRequirementEvaluationJsonString = jsonProducerForTrustmarkDefinitionRequirementEvaluation
                                                .serialize(trustmarkDefinitionRequirementEvaluation)
                                                .toString();

                                        PartnerSystemCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(
                                                        partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(),
                                                        partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                                                .forEach(partnerSystemCandidateTrustInteroperabilityProfileUriInner -> {

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationLocalDateTime(now);
                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpression(trustExpressionEvaluationJsonString);
                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirement(trustmarkDefinitionRequirementEvaluationJsonString);

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpressionSatisfied(
                                                            reduce(trustExpressionEvaluation.getTrustExpression().getData().toEither().bimap(
                                                                    nel -> null,
                                                                    data -> data.matchValueBoolean(
                                                                            value -> value,
                                                                            () -> null))));

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementSatisfied(
                                                            trustmarkDefinitionRequirementEvaluation
                                                                    .getTrustmarkDefinitionRequirementSatisfaction()
                                                                    .map(list -> list
                                                                            .filter(pInner -> pInner._2().isNotEmpty())
                                                                            .length())
                                                                    .orSuccess((Integer) null));

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(
                                                            trustmarkDefinitionRequirementEvaluation
                                                                    .getTrustmarkDefinitionRequirementSatisfaction()
                                                                    .map(list -> list
                                                                            .filter(pInner -> pInner._2().isEmpty())
                                                                            .length())
                                                                    .orSuccess((Integer) null));

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.saveHelper();
                                                }));

                                        log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; re-evaluated...",
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

                                    } else {

                                        log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' unchanged.",
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

                                    }
                                }));
    }
}
