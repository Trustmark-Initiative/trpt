package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.MailEvaluationUpdate;
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
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.TryEffect;
import org.gtri.fj.product.Unit;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationAttemptLocalDateTime(LocalDateTime.now(ZoneOffset.UTC));

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

// For now, don't save the trust expression evaluation json.
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

// For now, don't save the trustmark definition requirement evaluation json; it is not currently in use.
//                                        final String trustmarkDefinitionRequirementEvaluationJsonString = jsonProducerForTrustmarkDefinitionRequirementEvaluation
//                                                .serialize(trustmarkDefinitionRequirementEvaluation)
//                                                .toString();

                                        PartnerSystemCandidateTrustInteroperabilityProfileUri.withTransactionHelper(() -> PartnerSystemCandidateTrustInteroperabilityProfileUri.findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(
                                                        partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(),
                                                        partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                                                .forEach(partnerSystemCandidateTrustInteroperabilityProfileUriInner -> {

                                                    final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationLocalDateTime(now);
// For now, don't save the trust expression evaluation json.
                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustExpression(gzip(trustExpressionEvaluationJsonString).toOption().orSome(new byte[]{}));
// For now, don't save the trustmark definition requirement evaluation json; it is not currently in use.
//                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.setEvaluationTrustmarkDefinitionRequirement(trustmarkDefinitionRequirementEvaluationJsonString);

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

                                                    final MailEvaluationUpdate mailEvaluationUpdate = new MailEvaluationUpdate();
                                                    mailEvaluationUpdate.partnerSystemCandidateTrustInteroperabilityProfileUriHelper(partnerSystemCandidateTrustInteroperabilityProfileUriInner);
                                                    mailEvaluationUpdate.setRequestDateTime(now);
                                                    mailEvaluationUpdate.saveHelper();

                                                    partnerSystemCandidateTrustInteroperabilityProfileUriInner.mailEvaluationUpdateSetHelper(
                                                            partnerSystemCandidateTrustInteroperabilityProfileUriInner.mailEvaluationUpdateSetHelper().snoc(mailEvaluationUpdate));

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

    public static Validation<IOException, byte[]> gzip(final String trustExpressionEvaluationJsonString) {

        final ByteArrayOutputStream trustExpressionEvaluationByteArrayOutputStream = new ByteArrayOutputStream();

        return Try.<GZIPOutputStream, IOException>f(() -> new GZIPOutputStream(trustExpressionEvaluationByteArrayOutputStream))._1()
                .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.write(trustExpressionEvaluationJsonString.getBytes(StandardCharsets.UTF_8)))._1()
                        .map(unit -> gzipOutputStream))
                .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.flush())._1()
                        .map(unit -> gzipOutputStream))
                .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.close())._1()
                        .map(unit -> gzipOutputStream))
                .map(gzipOutputStream -> trustExpressionEvaluationByteArrayOutputStream.toByteArray());
    }

    public static Validation<IOException, String> gunzip(final byte[] trustExpressionEvaluationByteArray) {

        final ByteArrayOutputStream trustExpressionEvaluationByteArrayOutputStream = new ByteArrayOutputStream();

        final Validation<IOException, String> validation= Try.<GZIPInputStream, IOException>f(() -> new GZIPInputStream(new ByteArrayInputStream(trustExpressionEvaluationByteArray)))._1()
                .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> {
                    int read;
                    while ((read = gzipOutputStream.read()) != -1) {
                        trustExpressionEvaluationByteArrayOutputStream.write(read);
                    }
                })._1().map(unit -> gzipOutputStream))
                .bind(gzipInputStream -> TryEffect.<Unit, IOException>f(() -> gzipInputStream.close())._1()
                        .map(unit -> gzipInputStream))
                .map(gzipInputStream -> new String(trustExpressionEvaluationByteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8));

        return validation;
    }
}
