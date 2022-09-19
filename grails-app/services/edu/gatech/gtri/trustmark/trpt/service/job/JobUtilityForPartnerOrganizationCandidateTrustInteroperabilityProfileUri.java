package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateMailEvaluationUpdate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.service.file.FileUtility;
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
import org.gtri.fj.data.Option;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.optionEqual;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.lang.BooleanUtility.booleanEqual;
import static org.gtri.fj.lang.IntegerUtility.intEqual;
import static org.gtri.fj.lang.StringUtility.stringEqual;

public class JobUtilityForPartnerOrganizationCandidateTrustInteroperabilityProfileUri {

    private JobUtilityForPartnerOrganizationCandidateTrustInteroperabilityProfileUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForPartnerOrganizationCandidateTrustInteroperabilityProfileUri.class);

    private static final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = new DatabaseCacheTrustInteroperabilityProfileResolver();
    private static final TrustmarkDefinitionResolver trustmarkDefinitionResolver = new DatabaseCacheTrustmarkDefinitionResolver();
    private static final TrustmarkResolver trustmarkResolver = new DatabaseCacheTrustmarkResolver();
    private static final TrustmarkStatusReportResolver trustmarkStatusReportResolver = new DatabaseCacheTrustmarkStatusReportResolver();

    private static final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustInteroperabilityProfileResolver, trustmarkDefinitionResolver);
    private static final TrustmarkDefinitionRequirementEvaluator trustmarkDefinitionRequirementEvaluator = new TrustmarkDefinitionRequirementEvaluatorImpl(trustmarkResolver, trustExpressionParser);

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducerForTrustExpressionEvaluation = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirementEvaluation, JSONObject> jsonProducerForTrustmarkDefinitionRequirementEvaluation = jsonManager.findProducerStrict(TrustmarkDefinitionRequirementEvaluation.class, JSONObject.class).some();

    public static void synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(
            final Duration duration,
            final List<PartnerOrganizationCandidate> partnerOrganizationCandidateList,
            final List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriList,
            final List<Organization> organizationList) {

        final List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriList = retry(() -> PartnerOrganizationCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerOrganizationCandidateTrustInteroperabilityProfileUri.findByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUriAndProtectedSystemHelper(partnerOrganizationCandidateList, trustInteroperabilityProfileUriList, organizationList)
                .map(p -> p._3().orSome(() -> {
                    final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri = new PartnerOrganizationCandidateTrustInteroperabilityProfileUri();
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper(p._1());
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(p._2());
                    return partnerOrganizationCandidateTrustInteroperabilityProfileUri.saveAndFlushHelper();
                }))
                .map(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> {

                    partnerOrganizationCandidateTrustInteroperabilityProfileUri
                            .trustInteroperabilityProfileUriHelper()
                            .getDocumentChangeLocalDateTime();

                    partnerOrganizationCandidateTrustInteroperabilityProfileUri
                            .partnerOrganizationCandidateHelper()
                            .partnerOrganizationCandidateTrustmarkUriSetHelper()
                            .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                            .forEach(trustmarkUri -> trustmarkUri.getUri());

                    partnerOrganizationCandidateTrustInteroperabilityProfileUri
                            .setEvaluationAttemptLocalDateTime(LocalDateTime.now(ZoneOffset.UTC));

                    return partnerOrganizationCandidateTrustInteroperabilityProfileUri;
                })), log);

        partnerOrganizationCandidateTrustInteroperabilityProfileUriList.forEach(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(duration, partnerOrganizationCandidateTrustInteroperabilityProfileUri));
    }

    private static void synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(
            final Duration duration,
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri) {

        if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime() == null ||
                partnerOrganizationCandidateTrustInteroperabilityProfileUri
                        .partnerOrganizationCandidateHelper()
                        .partnerOrganizationCandidateTrustmarkUriSetHelper()
                        .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                        .exists(trustmarkUri -> trustmarkUri.getDocumentChangeLocalDateTime() == null)) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' waiting for dependency.",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime() == null) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime())) {

            log.info(format("Evaluation (%s) for trust interoperability profile '%s' (%s) and partner organization candidate '%s' changed (due to changed trust interoperability profile); re-evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation (%s) for trust interoperability profile '%s' (%s) and partner organization candidate '%s' changed (due to changed trust interoperability profile); re-evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri
                .partnerOrganizationCandidateHelper()
                .partnerOrganizationCandidateTrustmarkUriSetHelper()
                .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                .exists(trustmarkUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustmarkUri.getDocumentChangeLocalDateTime()))) {

            partnerOrganizationCandidateTrustInteroperabilityProfileUri
                    .partnerOrganizationCandidateHelper()
                    .partnerOrganizationCandidateTrustmarkUriSetHelper()
                    .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                    .filter(trustmarkUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustmarkUri.getDocumentChangeLocalDateTime()))
                    .forEach(trustmarkUri -> log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed (due to changed trustmark '%s'); re-evaluating...",
                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName(),
                            trustmarkUri.getUri())));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed (due to changed trustmark); re-evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC).minus(duration))) {

            log.info(format("Evaluation (%s) for trust interoperability profile '%s' and partner organization candidate '%s' expired; re-evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, false);

            log.info(format("Evaluation (%s) for trust interoperability profile '%s' and partner organization candidate '%s' expired; re-evaluated.",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else {

            log.trace(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' did not change.",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        }
    }

    private static void synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(
            final PartnerOrganizationCandidateTrustInteroperabilityProfileUri partnerOrganizationCandidateTrustInteroperabilityProfileUri,
            final boolean mail) {

        final TrustmarkVerifierFactory trustmarkVerifierFactory = FactoryLoader.getInstance(TrustmarkVerifierFactory.class);
        final TrustmarkVerifier trustmarkVerifier = trustmarkVerifierFactory.createVerifier(
                new XmlSignatureValidatorImpl(),
                trustmarkStatusReportResolver,
                nil(),
                iterableList(new JSONArray(partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getTrustmarkRecipientIdentifierArrayJson()).toList()).map(object -> URI.create((String) object)));

        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(trustmarkResolver, trustmarkVerifier, trustExpressionParser);

        final TrustExpressionEvaluation trustExpressionEvaluation = trustExpressionEvaluator.evaluate(
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri
                        .partnerOrganizationCandidateHelper()
                        .partnerOrganizationCandidateTrustmarkUriSetHelper()
                        .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                        .map(TrustmarkUri::getUri));

        // identify the source of the occasional NullPointerException
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper() => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri() => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper() => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper()));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper() is null? => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().partnerOrganizationCandidateTrustmarkUriSetHelper() == null));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper) is null? => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().partnerOrganizationCandidateTrustmarkUriSetHelper().map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper) == null));
        log.info(format("partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().partnerSystemCandidateTrustmarkUriSetHelper().map(PartnerSystemCandidateTrustmarkUri::trustmarkUriHelper).map(TrustmarkUri::getUri) is null? => %s", partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().partnerOrganizationCandidateTrustmarkUriSetHelper().map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper).map(TrustmarkUri::getUri) == null));

        final TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation = trustmarkDefinitionRequirementEvaluator.evaluate(
                partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                partnerOrganizationCandidateTrustInteroperabilityProfileUri
                        .partnerOrganizationCandidateHelper()
                        .partnerOrganizationCandidateTrustmarkUriSetHelper()
                        .map(PartnerOrganizationCandidateTrustmarkUri::trustmarkUriHelper)
                        .map(TrustmarkUri::getUri));

        retry(() -> PartnerOrganizationCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerOrganizationCandidateTrustInteroperabilityProfileUri.findByPartnerOrganizationCandidateAndTrustInteroperabilityProfileUriHelper(
                        partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper(),
                        partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                .forEach(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner -> {

                    final LocalDateTime evaluationLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

                    final Option<String> evaluationTrustExpressionStringOld = fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression()).map(FileUtility::stringFor);

                    final Option<String> evaluationTrustExpressionString = fromNull(jsonProducerForTrustExpressionEvaluation
                            .serialize(trustExpressionEvaluation)
                            .toString(4));

//                    final File evaluationTrustmarkDefinitionRequirement = fileFor(jsonProducerForTrustmarkDefinitionRequirementEvaluation
//                            .serialize(trustmarkDefinitionRequirementEvaluation)
//                            .toString(4));

                    final Option<Boolean> evaluationTrustExpressionSatisfiedOld = fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied());

                    final Option<Boolean> evaluationTrustExpressionSatisfied = reduce(trustExpressionEvaluation.getTrustExpression().getData().toEither().bimap(
                            nel -> none(),
                            data -> data.matchValueBoolean(
                                    value -> some(value),
                                    () -> none())));

                    final Option<Integer> evaluationTrustmarkDefinitionRequirementSatisfiedOld = fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied());

                    final Option<Integer> evaluationTrustmarkDefinitionRequirementSatisfied = trustmarkDefinitionRequirementEvaluation
                            .getTrustmarkDefinitionRequirementSatisfaction()
                            .map(list -> some(list
                                    .filter(pInner -> pInner._2().isNotEmpty())
                                    .length()))
                            .orSuccess(none());

                    final Option<Integer> evaluationTrustmarkDefinitionRequirementUnsatisfiedOld = fromNull(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied());

                    final Option<Integer> evaluationTrustmarkDefinitionRequirementUnsatisfied = trustmarkDefinitionRequirementEvaluation
                            .getTrustmarkDefinitionRequirementSatisfaction()
                            .map(list -> some(list
                                    .filter(pInner -> pInner._2().isEmpty())
                                    .length()))
                            .orSuccess(none());

                    if ((!optionEqual(booleanEqual).eq(evaluationTrustExpressionSatisfied, evaluationTrustExpressionSatisfiedOld)) ||
                            (!optionEqual(intEqual).eq(evaluationTrustmarkDefinitionRequirementUnsatisfied, evaluationTrustmarkDefinitionRequirementUnsatisfiedOld)) ||
                            (!optionEqual(intEqual).eq(evaluationTrustmarkDefinitionRequirementSatisfied, evaluationTrustmarkDefinitionRequirementSatisfiedOld)) ||
                            (!optionEqual(stringEqual).eq(evaluationTrustExpressionString, evaluationTrustExpressionStringOld))) {

                        if (!optionEqual(booleanEqual).eq(evaluationTrustExpressionSatisfied, evaluationTrustExpressionSatisfiedOld)) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed; trust expression satisfaction changed from '%s' to '%s'.",
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateHelper().getName(),
                                    evaluationTrustExpressionSatisfiedOld,
                                    evaluationTrustExpressionSatisfied));
                        }

                        if (!optionEqual(intEqual).eq(evaluationTrustmarkDefinitionRequirementUnsatisfied, evaluationTrustmarkDefinitionRequirementUnsatisfiedOld)) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed; trustmark definition requirements unsatisfied changed from '%s' to '%s'.",
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateHelper().getName(),
                                    evaluationTrustmarkDefinitionRequirementUnsatisfiedOld,
                                    evaluationTrustmarkDefinitionRequirementUnsatisfied));
                        }

                        if (!optionEqual(intEqual).eq(evaluationTrustmarkDefinitionRequirementSatisfied, evaluationTrustmarkDefinitionRequirementSatisfiedOld)) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed; trustmark definition requirements satisfied changed from '%s' to '%s'.",
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateHelper().getName(),
                                    evaluationTrustmarkDefinitionRequirementSatisfiedOld,
                                    evaluationTrustmarkDefinitionRequirementSatisfied));
                        }

                        if (!optionEqual(stringEqual).eq(evaluationTrustExpressionString, evaluationTrustExpressionStringOld)) {
                            log.info(format("Evaluation for trust interoperability profile '%s' and partner organization candidate '%s' changed; trust expression evaluation changed.",
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper().getUri(),
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateHelper().getName()));
                        }

                        final PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory = new PartnerOrganizationCandidateTrustInteroperabilityProfileUriHistory();
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationAttemptLocalDateTime(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationAttemptLocalDateTime());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationLocalDateTime(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationLocalDateTime());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustExpressionSatisfied(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpressionSatisfied());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustExpression(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustExpression());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirementSatisfied(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementSatisfied());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirementUnsatisfied());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.setEvaluationTrustmarkDefinitionRequirement(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.getEvaluationTrustmarkDefinitionRequirement());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.partnerOrganizationCandidateHelper(partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.trustInteroperabilityProfileUriHelper(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper());
                        partnerOrganizationCandidateTrustInteroperabilityProfileUriHistory.saveHelper();
                    }

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationLocalDateTime(evaluationLocalDateTime);
                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpression(evaluationTrustExpressionString.map(FileUtility::fileFor).toNull());
                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpressionSatisfied(evaluationTrustExpressionSatisfied.toNull());
                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementSatisfied(evaluationTrustmarkDefinitionRequirementSatisfied.toNull());
                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(evaluationTrustmarkDefinitionRequirementUnsatisfied.toNull());

                    OrganizationPartnerOrganizationCandidate.executeUpdateHelper("DELETE FROM OrganizationPartnerOrganizationCandidate organizationPartnerOrganizationCandidate " +
                            "WHERE id IN (SELECT DISTINCT organizationPartnerOrganizationCandidateInner.id FROM OrganizationPartnerOrganizationCandidate organizationPartnerOrganizationCandidateInner " +
                            "JOIN organizationPartnerOrganizationCandidateInner.organization organization " +
                            "JOIN organization.organizationTrustInteroperabilityProfileUriSet organizationTrustInteroperabilityProfileUri " +
                            "JOIN organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri1 " +
                            "JOIN organizationPartnerOrganizationCandidateInner.partnerOrganizationCandidate partnerOrganizationCandidate " +
                            "JOIN partnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileUriSet partnerOrganizationCandidateTrustInteroperabilityProfileUri " +
                            "JOIN partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUri trustInteroperabilityProfileUri2 " +
                            "WHERE " +
                            "organizationPartnerOrganizationCandidateInner.trust = TRUE AND " +
                            "organizationTrustInteroperabilityProfileUri.mandatory = TRUE AND " +
                            "(partnerOrganizationCandidateTrustInteroperabilityProfileUri.evaluationTrustExpressionSatisfied IS NULL OR " +
                            "partnerOrganizationCandidateTrustInteroperabilityProfileUri.evaluationTrustExpressionSatisfied = FALSE) AND " +
                            "trustInteroperabilityProfileUri1.id = trustInteroperabilityProfileUri2.id)");

                    if (mail) {

                        final PartnerOrganizationCandidateMailEvaluationUpdate mailEvaluationUpdate = new PartnerOrganizationCandidateMailEvaluationUpdate();
                        mailEvaluationUpdate.partnerOrganizationCandidateTrustInteroperabilityProfileUriHelper(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner);
                        mailEvaluationUpdate.setRequestDateTime(evaluationLocalDateTime);
                        mailEvaluationUpdate.saveHelper();

                        partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateMailEvaluationUpdateSetHelper(
                                partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateMailEvaluationUpdateSetHelper().snoc(mailEvaluationUpdate));
                    }

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.saveAndFlushHelper();
                })), log);
    }
}
