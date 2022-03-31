package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateMailEvaluationUpdate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustmarkUri;
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
import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;

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

        new Thread(() -> partnerOrganizationCandidateTrustInteroperabilityProfileUriList.forEach(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(duration, partnerOrganizationCandidateTrustInteroperabilityProfileUri))).start();
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

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' waiting for dependency.",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime() == null) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getDocumentChangeLocalDateTime())) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trust interoperability profile); re-evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trust interoperability profile); re-evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
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
                    .forEach(trustmarkUri -> log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trustmark '%s'); re-evaluating...",
                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName(),
                            trustmarkUri.getUri())));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, true);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' changed (due to changed trustmark); re-evaluated",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else if (partnerOrganizationCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC).minus(duration))) {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' expired; re-evaluating...",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

            synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(partnerOrganizationCandidateTrustInteroperabilityProfileUri, false);

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' expired; re-evaluated.",
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getUri(),
                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().getName()));

        } else {

            log.info(format("Evaluation for trust interoperability profile '%s' and partner system candidate '%s' unchanged.",
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

                    final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationLocalDateTime(now);

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpression(fileFor(jsonProducerForTrustExpressionEvaluation
                            .serialize(trustExpressionEvaluation)
                            .toString(4)));

// For now, don't save the trustmark definition requirement evaluation json; it is not currently in use.
//                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirement(fileFor(jsonProducerForTrustmarkDefinitionRequirementEvaluation
//                            .serialize(trustmarkDefinitionRequirementEvaluation)
//                            .toString(4)));

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpressionSatisfied(
                            reduce(trustExpressionEvaluation.getTrustExpression().getData().toEither().bimap(
                                    nel -> null,
                                    data -> data.matchValueBoolean(
                                            value -> value,
                                            () -> null))));

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementSatisfied(
                            trustmarkDefinitionRequirementEvaluation
                                    .getTrustmarkDefinitionRequirementSatisfaction()
                                    .map(list -> list
                                            .filter(pInner -> pInner._2().isNotEmpty())
                                            .length())
                                    .orSuccess((Integer) null));

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(
                            trustmarkDefinitionRequirementEvaluation
                                    .getTrustmarkDefinitionRequirementSatisfaction()
                                    .map(list -> list
                                            .filter(pInner -> pInner._2().isEmpty())
                                            .length())
                                    .orSuccess((Integer) null));

                    if (mail) {

                        final PartnerOrganizationCandidateMailEvaluationUpdate mailEvaluationUpdate = new PartnerOrganizationCandidateMailEvaluationUpdate();
                        mailEvaluationUpdate.partnerOrganizationCandidateTrustInteroperabilityProfileUriHelper(partnerOrganizationCandidateTrustInteroperabilityProfileUriInner);
                        mailEvaluationUpdate.setRequestDateTime(now);
                        mailEvaluationUpdate.saveHelper();

                        partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateMailEvaluationUpdateSetHelper(
                                partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.partnerOrganizationCandidateMailEvaluationUpdateSetHelper().snoc(mailEvaluationUpdate));
                    }

                    partnerOrganizationCandidateTrustInteroperabilityProfileUriInner.saveAndFlushHelper();
                })), log);
    }
}
