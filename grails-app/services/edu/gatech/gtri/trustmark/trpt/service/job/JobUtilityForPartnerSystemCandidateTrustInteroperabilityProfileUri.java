package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateMailEvaluationUpdate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemPartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.service.job.resolver.DatabaseCacheTrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.trpt.service.job.resolver.DatabaseCacheTrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.trpt.service.job.resolver.DatabaseCacheTrustmarkResolver;
import edu.gatech.gtri.trustmark.trpt.service.job.resolver.DatabaseCacheTrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator.TrustExpressionEvaluatorImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator.TrustmarkDefinitionRequirementEvaluatorImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.parser.TrustExpressionParserImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureValidatorImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.fileFor;
import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.stringFor;
import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;

public class JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri {

    private JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.class);

    private static final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = new DatabaseCacheTrustInteroperabilityProfileResolver();
    private static final TrustmarkDefinitionResolver trustmarkDefinitionResolver = new DatabaseCacheTrustmarkDefinitionResolver();
    private static final TrustmarkResolver trustmarkResolver = new DatabaseCacheTrustmarkResolver();
    private static final TrustmarkStatusReportResolver trustmarkStatusReportResolver = new DatabaseCacheTrustmarkStatusReportResolver();

    private static final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustInteroperabilityProfileResolver, trustmarkDefinitionResolver);
    private static final TrustmarkDefinitionRequirementEvaluator trustmarkDefinitionRequirementEvaluator = new TrustmarkDefinitionRequirementEvaluatorImpl(trustmarkResolver, trustExpressionParser);

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducerForTrustExpressionEvaluation = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirementEvaluation, JSONObject> jsonProducerForTrustmarkDefinitionRequirementEvaluation = jsonManager.findProducerStrict(TrustmarkDefinitionRequirementEvaluation.class, JSONObject.class).some();

    public static void synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(
            final Duration duration,
            final List<PartnerSystemCandidate> partnerSystemCandidateList,
            final List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriList,
            final List<ProtectedSystem> protectedSystemList) {

        final List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriList = retry(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriAndProtectedSystemHelper(partnerSystemCandidateList, trustInteroperabilityProfileUriList, protectedSystemList)
                .map(p -> p._3().orSome(() -> {
                    final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri = new PartnerSystemCandidateTrustInteroperabilityProfileUri();
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(p._1());
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(p._2());
                    return partnerSystemCandidateTrustInteroperabilityProfileUri.saveAndFlushHelper();
                }))
                .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> {

                    partnerSystemCandidateTrustInteroperabilityProfileUri
                            .trustInteroperabilityProfileUriHelper()
                            .getDocumentChangeLocalDateTime();

                    partnerSystemCandidateTrustInteroperabilityProfileUri
                            .partnerSystemCandidateHelper()
                            .partnerSystemCandidateTrustmarkUriSetHelper()
                            .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                            .forEach(trustmarkUri -> trustmarkUri.getUri());

                    partnerSystemCandidateTrustInteroperabilityProfileUri
                            .setEvaluationAttemptLocalDateTime(LocalDateTime.now(ZoneOffset.UTC));

                    return partnerSystemCandidateTrustInteroperabilityProfileUri;
                })), log);

        new Thread(() -> partnerSystemCandidateTrustInteroperabilityProfileUriList.forEach(partnerSystemCandidateTrustInteroperabilityProfileUri -> synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(duration, partnerSystemCandidateTrustInteroperabilityProfileUri))).start();
    }

    private static void synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(
            final Duration duration,
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri) {

        if (partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime() == null ||
                partnerSystemCandidateTrustInteroperabilityProfileUri
                        .partnerSystemCandidateHelper()
                        .partnerSystemCandidateTrustmarkUriSetHelper()
                        .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                        .exists(trustmarkUri -> trustmarkUri.getDocumentChangeLocalDateTime() == null)) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' waiting for dependency.",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        } else if (partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime() == null) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' evaluating...",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

            synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' evaluated",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        } else if (partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime())) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trust interoperability profile); re-evaluating...",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

            synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trust interoperability profile); re-evaluated",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        } else if (partnerSystemCandidateTrustInteroperabilityProfileUri
                .partnerSystemCandidateHelper()
                .partnerSystemCandidateTrustmarkUriSetHelper()
                .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                .exists(trustmarkUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustmarkUri.getDocumentChangeLocalDateTime()))) {

            partnerSystemCandidateTrustInteroperabilityProfileUri
                    .partnerSystemCandidateHelper()
                    .partnerSystemCandidateTrustmarkUriSetHelper()
                    .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                    .filter(trustmarkUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustmarkUri.getDocumentChangeLocalDateTime()))
                    .forEach(trustmarkUri -> log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trustmark '%s'); re-evaluating...",
                            partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                            partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName(),
                            trustmarkUri.getUri())));

            synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trustmark); re-evaluated",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        } else if (partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC).minus(duration))) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' expired; re-evaluating...",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

            synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(partnerSystemCandidateTrustInteroperabilityProfileUri, false);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' expired; re-evaluated.",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        } else {

            log.trace(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' did not change.",
                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()));

        }
    }

    private static void synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(
            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri,
            final boolean mail) {

        final TrustmarkVerifierFactory trustmarkVerifierFactory = FactoryLoader.getInstance(TrustmarkVerifierFactory.class);
        final TrustmarkVerifier trustmarkVerifier = trustmarkVerifierFactory.createVerifier(
                new XmlSignatureValidatorImpl(),
                trustmarkStatusReportResolver,
                nil(),
                iterableList(new JSONArray(partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getTrustmarkRecipientIdentifierArrayJson()).toList()).map(object -> URI.create((String) object)));

        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(trustmarkResolver, trustmarkVerifier, trustExpressionParser);

        final TrustExpressionEvaluation trustExpressionEvaluation = trustExpressionEvaluator.evaluate(
                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                partnerSystemCandidateTrustInteroperabilityProfileUri
                        .partnerSystemCandidateHelper()
                        .partnerSystemCandidateTrustmarkUriSetHelper()
                        .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                        .map(TrustmarkUri::getUri));

        // identify the source of the occasional NullPointerException
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri => %s", partnerSystemCandidateTrustInteroperabilityProfileUri));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper() => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri() => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri => %s", partnerSystemCandidateTrustInteroperabilityProfileUri));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper() => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper() is null? => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper() == null));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper) is null? => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper) == null));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper).map(TrustmarkUri::getUri) is null? => %s", partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper).map(TrustmarkUri::getUri) == null));

        final TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation = trustmarkDefinitionRequirementEvaluator.evaluate(
                partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                partnerSystemCandidateTrustInteroperabilityProfileUri
                        .partnerSystemCandidateHelper()
                        .partnerSystemCandidateTrustmarkUriSetHelper()
                        .map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper)
                        .map(TrustmarkUri::getUri));

        retry(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(
                        partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(),
                        partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                .forEach(partnerSystemCandidateTrustInteroperabilityProfileUriInner -> {

                    final LocalDateTime evaluationLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

                    final String evaluationTrustExpressionString = jsonProducerForTrustExpressionEvaluation
                            .serialize(trustExpressionEvaluation)
                            .toString(4);

//                    final File evaluationTrustmarkDefinitionRequirement = fileFor(jsonProducerForTrustmarkDefinitionRequirementEvaluation
//                            .serialize(trustmarkDefinitionRequirementEvaluation)
//                            .toString(4));

                    final Boolean evaluationTrustExpressionSatisfied = reduce(trustExpressionEvaluation.getTrustExpression().getData().toEither().bimap(
                            nel -> null,
                            data -> data.matchValueBoolean(
                                    value -> value,
                                    () -> null)));

                    final Integer evaluationTrustmarkDefinitionRequirementSatisfied = trustmarkDefinitionRequirementEvaluation
                            .getTrustmarkDefinitionRequirementSatisfaction()
                            .map(list -> list
                                    .filter(pInner -> pInner._2().isNotEmpty())
                                    .length())
                            .orSuccess((Integer) null);

                    final Integer evaluationTrustmarkDefinitionRequirementUnsatisfied = trustmarkDefinitionRequirementEvaluation
                            .getTrustmarkDefinitionRequirementSatisfaction()
                            .map(list -> list
                                    .filter(pInner -> pInner._2().isEmpty())
                                    .length())
                            .orSuccess((Integer) null);

                    if (partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression() != null &&
                            ((evaluationTrustExpressionSatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied()) ||
                                    (evaluationTrustmarkDefinitionRequirementUnsatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()) ||
                                    (evaluationTrustmarkDefinitionRequirementSatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied()) ||
                                    (!evaluationTrustExpressionString.equals(stringFor(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression()))))) {

                        if (evaluationTrustExpressionSatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied()) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; trust expression satisfaction changed from '%s' to '%s'.",
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateHelper().getName(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied(),
                                    evaluationTrustExpressionSatisfied));
                        }

                        if (evaluationTrustmarkDefinitionRequirementUnsatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied()) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; trustmark definition requirements unsatisfied changed from '%s' to '%s'.",
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateHelper().getName(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied(),
                                    evaluationTrustmarkDefinitionRequirementUnsatisfied));
                        }

                        if (evaluationTrustmarkDefinitionRequirementSatisfied != partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied()) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; trustmark definition requirements satisfied changed from '%s' to '%s'.",
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateHelper().getName(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied(),
                                    evaluationTrustmarkDefinitionRequirementSatisfied));
                        }

                        if (!evaluationTrustExpressionString.equals(stringFor(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression()))) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed; trust expression evaluation changed.",
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateHelper().getName()));
                        }

                        final PartnerSystemCandidateTrustInteroperabilityProfileUriHistory partnerSystemCandidateTrustInteroperabilityProfileUriHistory = new PartnerSystemCandidateTrustInteroperabilityProfileUriHistory();
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationAttemptLocalDateTime(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationAttemptLocalDateTime());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationLocalDateTime(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationLocalDateTime());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustExpressionSatisfied(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustExpression(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirementSatisfied(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirement(partnerSystemCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirement());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.partnerSystemCandidateHelper(partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.trustInteroperabilityProfileUriHelper(partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper());
                        partnerSystemCandidateTrustInteroperabilityProfileUriHistory.saveHelper();
                    }

                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationLocalDateTime(evaluationLocalDateTime);
                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpression(fileFor(evaluationTrustExpressionString));
                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpressionSatisfied(evaluationTrustExpressionSatisfied);
                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementSatisfied(evaluationTrustmarkDefinitionRequirementSatisfied);
                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(evaluationTrustmarkDefinitionRequirementUnsatisfied);

                    ProtectedSystemPartnerSystemCandidate.executeUpdateHelper("DELETE FROM ProtectedSystemPartnerSystemCandidate protectedSystemPartnerSystemCandidate " +
                            "WHERE id IN (SELECT DISTINCT protectedSystemPartnerSystemCandidateInner.id FROM ProtectedSystemPartnerSystemCandidate protectedSystemPartnerSystemCandidateInner " +
                            "JOIN protectedSystemPartnerSystemCandidateInner.protectedSystem protectedSystem " +
                            "JOIN protectedSystem.protectedSystemTrustInteroperabilityProfileUriSet protectedSystemTrustInteroperabilityProfileUri " +
                            "JOIN protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri1 " +
                            "JOIN protectedSystemPartnerSystemCandidateInner.partnerSystemCandidate partnerSystemCandidate " +
                            "JOIN partnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileUriSet partnerSystemCandidateTrustInteroperabilityProfileUri " +
                            "JOIN partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri2 " +
                            "WHERE " +
                            "protectedSystemPartnerSystemCandidateInner.trust = TRUE AND " +
                            "protectedSystemTrustInteroperabilityProfileUri.mandatory = TRUE AND " +
                            "(partnerSystemCandidateTrustInteroperabilityProfileUri.evaluationTrustExpressionSatisfied IS NULL OR " +
                            "partnerSystemCandidateTrustInteroperabilityProfileUri.evaluationTrustExpressionSatisfied = FALSE) AND " +
                            "trustInteroperabilityProfileUri1.id = trustInteroperabilityProfileUri2.id)");

                    if (mail) {

                        final PartnerSystemCandidateMailEvaluationUpdate partnerSystemCandidateMailEvaluationUpdate = new PartnerSystemCandidateMailEvaluationUpdate();
                        partnerSystemCandidateMailEvaluationUpdate.partnerSystemCandidateTrustInteroperabilityProfileUriHelper(partnerSystemCandidateTrustInteroperabilityProfileUriInner);
                        partnerSystemCandidateMailEvaluationUpdate.setRequestDateTime(evaluationLocalDateTime);
                        partnerSystemCandidateMailEvaluationUpdate.saveHelper();

                        partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateMailEvaluationUpdateSetHelper(
                                partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateMailEvaluationUpdateSetHelper().snoc(partnerSystemCandidateMailEvaluationUpdate));
                    }

                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.saveAndFlushHelper();
                })), log);
    }
}
