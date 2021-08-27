package edu.gatech.gtri.trustmark.trpt.service.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluatorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;
import org.json.JSONObject;
import org.springframework.security.crypto.codec.Hex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iteratorList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class JobUtility {

    private JobUtility() {
    }

    private static final Log log = LogFactory.getLog(JobUtility.class);

    private static final TrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustExpressionEvaluatorFactory.class);
    private static final TrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

    private static final TrustmarkDefinitionRequirementEvaluatorFactory trustmarkDefinitionRequirementEvaluatorFactory = FactoryLoader.getInstance(TrustmarkDefinitionRequirementEvaluatorFactory.class);
    private static final TrustmarkDefinitionRequirementEvaluator trustmarkDefinitionRequirementEvaluator = trustmarkDefinitionRequirementEvaluatorFactory.createDefaultEvaluator();

    private static final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
    private static final TrustmarkResolver trustmarkResolver = FactoryLoader.getInstance(TrustmarkResolver.class);

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducerForTrustExpressionEvaluation = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirementEvaluation, JSONObject> jsonProducerForTrustmarkDefinitionRequirementEvaluation = jsonManager.findProducerStrict(TrustmarkDefinitionRequirementEvaluation.class, JSONObject.class).some();

    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);

    public static String truncate(String string, int length) {

        return string == null ? null : string.substring(0, Math.min(string.length(), length));
    }

    public static String httpGet(final String urlString) throws Exception {

        final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
        httpURLConnection.setRequestMethod("GET");

        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            final StringBuilder stringBuilder = new StringBuilder();
            String readLine;

            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(readLine);
            }

            bufferedReader.close();

            return stringBuilder.toString();

        } else {

            throw new Exception(httpURLConnection.getResponseMessage());
        }
    }

    public static Either<String, ArrayNode> httpGetArrayNode(final String urlString) {

        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                final JsonNode jsonNode = new ObjectMapper().readTree(httpURLConnection.getInputStream());

                if (jsonNode.isArray()) {

                    return right((ArrayNode) jsonNode);

                } else {

                    return left("The system expects a JSON array; the response is not a JSON array.");
                }
            } else {

                return left(httpURLConnection.getResponseMessage());
            }
        } catch (Exception exception) {

            return left(exception.getMessage());
        }
    }

    public interface PartnerSystemCandidateFromJson {

        String getName();

        String getEntityId();

        String getSaml2MetadataUrl();

        List<String> getTrustmarkUrlList();

        JsonNode getJsonNode();
    }

    public static List<PartnerSystemCandidateFromJson> partnerSystemCandidateList(final ArrayNode arrayNode) {

        return iteratorList(arrayNode.iterator()).map(jsonNode -> new PartnerSystemCandidateFromJson() {
            @Override
            public String getName() {

                return jsonNode.get("name").asText();
            }

            @Override
            public String getEntityId() {

                return jsonNode.get("entityId").asText();
            }

            @Override
            public String getSaml2MetadataUrl() {

                return jsonNode.get("saml2MetadataUrl").asText();
            }

            @Override
            public List<String> getTrustmarkUrlList() {

                return iteratorList(jsonNode.get("trustmarks").elements()).map(jsonNodeInner -> jsonNodeInner.get("url").asText());
            }

            @Override
            public JsonNode getJsonNode() {

                return jsonNode;
            }
        });
    }

    public static TreeMap<String, String> trustmarkUriMap(final List<String> trustmarkUriList) {

        return somes(somes(trustmarkUriList
                .map(trustmarkUri -> Try.f(() -> trustmarkResolver.resolve(URI.create(trustmarkUri)))._1().f().map(exception -> {
                    exception.printStackTrace();
                    return exception;
                }).toOption()))
                .map(trustmark -> Try.f(() -> p(trustmark.getIdentifier().toString(), new String(Hex.encode(hashFactory.hash(trustmark)))))._1().f().map(exception -> {
                    exception.printStackTrace();
                    return exception;
                }).toOption()))
                .groupBy(P2::_1, P2::_2, ord((o1, o2) -> stringOrd.compare(o1, o2)))
                .map(list -> list.head());
    }

    public static TreeMap<String, String> deserializeTrustmarkUriMapJson(final String jsonString) {

        return fromNull(jsonString)
                .bind(jsonStringInner -> Try.f(() -> (ObjectNode) new ObjectMapper().readTree(jsonStringInner))._1().toOption())
                .map(objectNode -> iteratorList(objectNode.fieldNames()).map(fieldName -> p(fieldName, objectNode.get(fieldName).asText())))
                .orSome(nil())
                .groupBy(P2::_1, P2::_2, ord((o1, o2) -> stringOrd.compare(o1, o2)))
                .map(list -> list.head());
    }

    public static String serializeTrustmarkUriMapJson(final TreeMap<String, String> treeMap) {

        return treeMap.toList().foldLeft((objectNode, p) -> objectNode.put(p._1(), p._2()), new ObjectMapper().createObjectNode()).toString();
    }

    public static void synchronizeTrustInteroperabilityProfileUri() {

        TrustInteroperabilityProfileUri.findAllHelper().forEach(trustInteroperabilityProfileUri -> {

            log.info(format("Reading trust interoperability profile uri '%s' ...", trustInteroperabilityProfileUri.getUri()));

            final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

            try {
                final TrustInteroperabilityProfile trustInteroperabilityProfile = trustInteroperabilityProfileResolver.resolve(URI.create(trustInteroperabilityProfileUri.getUri()));
                final String hash = new String(Hex.encode(hashFactory.hash(trustInteroperabilityProfile)));

                trustInteroperabilityProfileUri.setName(truncate(trustInteroperabilityProfile.getName(), 200));
                trustInteroperabilityProfileUri.setDescription(truncate(trustInteroperabilityProfile.getDescription(), 200));
                trustInteroperabilityProfileUri.setPublicationLocalDateTime(trustInteroperabilityProfile.getPublicationDateTime().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                trustInteroperabilityProfileUri.setIssuerName(truncate(trustInteroperabilityProfile.getIssuer().getName(), 200));
                trustInteroperabilityProfileUri.setIssuerIdentifier(truncate(trustInteroperabilityProfile.getIssuer().getIdentifier().toString(), 200));
                trustInteroperabilityProfileUri.setRequestLocalDateTime(now);
                trustInteroperabilityProfileUri.setSuccessLocalDateTime(now);

                if (trustInteroperabilityProfileUri.getHash() == null || !trustInteroperabilityProfileUri.getHash().equals(hash)) {
                    trustInteroperabilityProfileUri.setHash(hash);
                    trustInteroperabilityProfileUri.setChangeLocalDateTime(now);
                }

                trustInteroperabilityProfileUri.saveAndFlushHelper();

            } catch (final Exception exception) {

                log.info(format("Reading trust interoperability profile uri '%s' failure: '%s'.", trustInteroperabilityProfileUri.getUri(), exception.getMessage()));

                trustInteroperabilityProfileUri.setRequestLocalDateTime(now);
                trustInteroperabilityProfileUri.setFailureLocalDateTime(now);
                trustInteroperabilityProfileUri.setFailureMessage(truncate(exception.getMessage(), 200));

                trustInteroperabilityProfileUri.setHash(null);
                trustInteroperabilityProfileUri.setChangeLocalDateTime(now);

                trustInteroperabilityProfileUri.saveAndFlushHelper();
            }
        });
    }

    public static void synchronizeTrustmarkBindingRegistry() {

        TrustmarkBindingRegistryUri.findAllByOrderByUriAscHelper().forEach(trustmarkBindingRegistryUri -> {

            log.info(format("Reading trustmark binding registry uri '%s' ...", trustmarkBindingRegistryUri.getUri()));

            arrayList(PartnerSystemCandidateType.values()).forEach(partnerSystemCandidateType -> {

                final String uri = trustmarkBindingRegistryUri.getUri() + partnerSystemCandidateType.getUriRelative();
                final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

                log.info(format("Reading trustmark binding registry uri '%s' ...", uri));

                final Either<String, ArrayNode> arrayNodeEither = httpGetArrayNode(uri);

                arrayNodeEither.left().forEach(
                        message -> {

                            log.info(format("Reading trustmark binding registry uri '%s' failure: '%s'.", uri, message));

                            trustmarkBindingRegistryUri.setRequestLocalDateTime(now);
                            trustmarkBindingRegistryUri.setFailureLocalDateTime(now);
                            trustmarkBindingRegistryUri.setFailureMessage(truncate(message, 200));

                            trustmarkBindingRegistryUri.partnerSystemCandidateSetHelper().forEach(partnerSystemCandidate -> {

                                log.info(format("Updating partner system candidate '%s' ...", partnerSystemCandidate.getUri()));

                                partnerSystemCandidate.setRequestLocalDateTime(now);
                                partnerSystemCandidate.setFailureLocalDateTime(now);
                                partnerSystemCandidate.setFailureMessage(truncate(message, 200));

                                partnerSystemCandidate.setTrustmarkUriMapJson(null);
                                partnerSystemCandidate.setChangeLocalDateTime(now);

                                partnerSystemCandidate.saveAndFlushHelper();
                            });
                        });

                arrayNodeEither.right().forEach(
                        arrayNode -> {
                            trustmarkBindingRegistryUri.setRequestLocalDateTime(now);
                            trustmarkBindingRegistryUri.setSuccessLocalDateTime(now);

                            partnerSystemCandidateList(arrayNode)
                                    .foldLeft(
                                            partnerSystemCandidateMap -> partnerSystemCandidateFromJson ->
                                                    PartnerSystemCandidate
                                                            .findByTrustmarkBindingRegistryUriAndUriHelper(trustmarkBindingRegistryUri, partnerSystemCandidateFromJson.getEntityId())
                                                            .map(partnerSystemCandidate -> {

                                                                log.info(format("Updating partner system candidate '%s' ...", partnerSystemCandidateFromJson.getEntityId()));

                                                                partnerSystemCandidate.setName(partnerSystemCandidateFromJson.getName());
                                                                partnerSystemCandidate.setUri(partnerSystemCandidateFromJson.getEntityId());
                                                                partnerSystemCandidate.setUriEntityDescriptor(partnerSystemCandidateFromJson.getSaml2MetadataUrl());
                                                                partnerSystemCandidate.setType(partnerSystemCandidateType);
                                                                partnerSystemCandidate.setJson(partnerSystemCandidateFromJson.getJsonNode().toString());
                                                                partnerSystemCandidate.setRequestLocalDateTime(now);
                                                                partnerSystemCandidate.setSuccessLocalDateTime(now);

                                                                final TreeMap<String, String> trustmarkUriMapNew = trustmarkUriMap(partnerSystemCandidateFromJson.getTrustmarkUrlList());
                                                                final TreeMap<String, String> trustmarkUriMapOld = deserializeTrustmarkUriMapJson(partnerSystemCandidate.getTrustmarkUriMapJson());

                                                                if (!trustmarkUriMapNew.equals(trustmarkUriMapOld)) {
                                                                    partnerSystemCandidate.setTrustmarkUriMapJson(serializeTrustmarkUriMapJson(trustmarkUriMapNew));
                                                                    partnerSystemCandidate.setChangeLocalDateTime(now);
                                                                }

                                                                partnerSystemCandidate.saveAndFlushHelper();

                                                                return partnerSystemCandidateMap.delete(partnerSystemCandidateFromJson.getEntityId());
                                                            })
                                                            .orSome(() -> {

                                                                log.info(format("Inserting partner system candidate '%s' ...", partnerSystemCandidateFromJson.getEntityId()));

                                                                final PartnerSystemCandidate partnerSystemCandidate = new PartnerSystemCandidate();

                                                                partnerSystemCandidate.setName(partnerSystemCandidateFromJson.getName());
                                                                partnerSystemCandidate.setUri(partnerSystemCandidateFromJson.getEntityId());
                                                                partnerSystemCandidate.setUriEntityDescriptor(partnerSystemCandidateFromJson.getSaml2MetadataUrl());
                                                                partnerSystemCandidate.setType(partnerSystemCandidateType);
                                                                partnerSystemCandidate.setTrustmarkUriMapJson(partnerSystemCandidateFromJson.getTrustmarkUrlList().toString());
                                                                partnerSystemCandidate.setJson(partnerSystemCandidateFromJson.getJsonNode().toString());
                                                                partnerSystemCandidate.setRequestLocalDateTime(now);
                                                                partnerSystemCandidate.setSuccessLocalDateTime(now);
                                                                partnerSystemCandidate.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);

                                                                final TreeMap<String, String> trustmarkUriMapNew = trustmarkUriMap(partnerSystemCandidateFromJson.getTrustmarkUrlList());
                                                                final TreeMap<String, String> trustmarkUriMapOld = deserializeTrustmarkUriMapJson(partnerSystemCandidate.getTrustmarkUriMapJson());

                                                                if (!trustmarkUriMapNew.equals(trustmarkUriMapOld)) {
                                                                    partnerSystemCandidate.setTrustmarkUriMapJson(serializeTrustmarkUriMapJson(trustmarkUriMapNew));
                                                                    partnerSystemCandidate.setChangeLocalDateTime(now);
                                                                }

                                                                partnerSystemCandidate.saveAndFlushHelper();

                                                                return partnerSystemCandidateMap.delete(partnerSystemCandidateFromJson.getEntityId());
                                                            }),
                                            PartnerSystemCandidate
                                                    .findAllByTrustmarkBindingRegistryUriAndTypeHelper(trustmarkBindingRegistryUri, partnerSystemCandidateType)
                                                    .groupBy(partnerSystemCandidate -> partnerSystemCandidate.getUri(), stringOrd))
                                    .toList()
                                    .forEach(p -> p._2().forEach(partnerSystemCandidate -> {

                                        log.info(format("Updating partner system candidate '%s' ...", partnerSystemCandidate.getUri()));

                                        partnerSystemCandidate.setRequestLocalDateTime(now);
                                        partnerSystemCandidate.setFailureLocalDateTime(now);
                                        partnerSystemCandidate.setFailureMessage("The trustmark binding registry did not reference this partner system candidate.");

                                        partnerSystemCandidate.setTrustmarkUriMapJson(null);
                                        partnerSystemCandidate.setChangeLocalDateTime(now);

                                        partnerSystemCandidate.saveAndFlushHelper();
                                    }));
                        });
            });
        });
    }

    public static void synchronizeTrustExpressionEvaluation() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustInteroperabilityProfileUri.findAllHelper()
                .filter(entity ->
                        entity.getSuccessLocalDateTime() != null &&
                                (entity.getFailureLocalDateTime() == null ||
                                        entity.getSuccessLocalDateTime().isAfter(entity.getFailureLocalDateTime())))
                .forEach(trustInteroperabilityProfileUri ->
                        PartnerSystemCandidate.findAllHelper()
                                .filter(entity ->
                                        entity.getSuccessLocalDateTime() != null &&
                                                (entity.getFailureLocalDateTime() == null ||
                                                        entity.getSuccessLocalDateTime().isAfter(entity.getFailureLocalDateTime())))
                                .forEach(partnerSystemCandidate -> {

                                    log.info(format("Evaluating trust interoperability profile '%s' for partner system candidate '%s' ...", trustInteroperabilityProfileUri.getUri(), partnerSystemCandidate.getName()));

                                    try {

                                        final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUri =
                                                PartnerSystemCandidateTrustInteroperabilityProfileUri
                                                        .findByPartnerSystemCandidateAndTrustInteroperabilityProfileUriHelper(partnerSystemCandidate, trustInteroperabilityProfileUri)
                                                        .orSome(() -> {
                                                            final PartnerSystemCandidateTrustInteroperabilityProfileUri partnerSystemCandidateTrustInteroperabilityProfileUriInner = new PartnerSystemCandidateTrustInteroperabilityProfileUri();
                                                            partnerSystemCandidateTrustInteroperabilityProfileUriInner.partnerSystemCandidateHelper(partnerSystemCandidate);
                                                            partnerSystemCandidateTrustInteroperabilityProfileUriInner.trustInteroperabilityProfileUriHelper(trustInteroperabilityProfileUri);
                                                            return partnerSystemCandidateTrustInteroperabilityProfileUriInner;
                                                        });

                                        final List<String> trustmarkUriList = deserializeTrustmarkUriMapJson(partnerSystemCandidate.getTrustmarkUriMapJson()).toList().map(P2::_1);

                                        if (partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime() == null ||
                                                trustInteroperabilityProfileUri.getChangeLocalDateTime() == null ||
                                                partnerSystemCandidate.getChangeLocalDateTime() == null ||
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(trustInteroperabilityProfileUri.getChangeLocalDateTime()) ||
                                                partnerSystemCandidateTrustInteroperabilityProfileUri.getEvaluationLocalDateTime().isBefore(partnerSystemCandidate.getChangeLocalDateTime())) {

                                            final TrustExpressionEvaluation trustExpressionEvaluation = trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                                                    trustInteroperabilityProfileUri.getUri(),
                                                    trustmarkUriList);

                                            final TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation = trustmarkDefinitionRequirementEvaluator.evaluate(
                                                    trustInteroperabilityProfileUri.getUri(),
                                                    trustmarkUriList);

                                            log.info(format("Evaluating trust interoperability profile '%s' for trustmarks '%s'", trustInteroperabilityProfileUri.getUri(), String.join(", ", trustmarkUriList.toJavaList())));

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationLocalDateTime(now);

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationTrustExpression(
                                                    jsonProducerForTrustExpressionEvaluation.serialize(trustExpressionEvaluation)
                                                            .toString());

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationTrustExpressionSatisfied(
                                                    reduce(trustExpressionEvaluation.getTrustExpression().getData().toEither().bimap(
                                                            nel -> null,
                                                            data -> data.matchValueBoolean(
                                                                    value -> value,
                                                                    () -> null))));

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationTrustmarkDefinitionRequirement(
                                                    jsonProducerForTrustmarkDefinitionRequirementEvaluation.serialize(trustmarkDefinitionRequirementEvaluation)
                                                            .toString());

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationTrustmarkDefinitionRequirementSatisfied(
                                                    trustmarkDefinitionRequirementEvaluation
                                                            .getTrustmarkDefinitionRequirementSatisfaction()
                                                            .map(list -> list
                                                                    .filter(p -> p._2().isNotEmpty())
                                                                    .length())
                                                            .orSuccess((Integer) null));

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.setEvaluationTrustmarkDefinitionRequirementUnsatisfied(
                                                    trustmarkDefinitionRequirementEvaluation
                                                            .getTrustmarkDefinitionRequirementSatisfaction()
                                                            .map(list -> list
                                                                    .filter(p -> p._2().isEmpty())
                                                                    .length())
                                                            .orSuccess((Integer) null));

                                            partnerSystemCandidateTrustInteroperabilityProfileUri.saveAndFlushHelper();
                                        }

                                    } catch (final Exception exception) {

                                        exception.printStackTrace();
                                    }
                                }));
    }
}
