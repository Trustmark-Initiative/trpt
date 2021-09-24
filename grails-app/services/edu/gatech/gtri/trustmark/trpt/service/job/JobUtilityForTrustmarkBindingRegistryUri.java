package edu.gatech.gtri.trustmark.trpt.service.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriTypeHistory;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.Ord;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Effect1;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;
import org.json.JSONArray;
import org.springframework.security.crypto.codec.Hex;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateType.fromNameForTrustmarkBindingRegistry;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.createResolveHash;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.httpGetArrayNode;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;
import static java.lang.String.format;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iteratorList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.TreeMap.empty;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class JobUtilityForTrustmarkBindingRegistryUri {

    private JobUtilityForTrustmarkBindingRegistryUri() {
    }


    private static final MessageDigest messageDigest;
    private static final Log log = LogFactory.getLog(JobUtilityForTrustmarkBindingRegistryUri.class);

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public interface TrustmarkBindingRegistryFromJson {

        interface TrustmarkFromJson {

            URI getUrl();
        }

        interface PartnerSystemCandidateFromJson {

            String getName();

            String getEntityId();

            PartnerSystemCandidateType getProviderType();

            String getSaml2MetadataUrl();

            TreeMap<URI, TrustmarkFromJson> getTrustmarkMap();

            JsonNode getJsonNode();
        }

        URI getURI();

        String getSource();

        TreeMap<String, PartnerSystemCandidateFromJson> getProviderMap();
    }

    public static TrustmarkBindingRegistryFromJson resolve(final URI uri) {

        final ArrayNode arrayNode = httpGetArrayNode(uri).right().value();

        return new TrustmarkBindingRegistryFromJson() {

            private final TreeMap<String, PartnerSystemCandidateFromJson> partnerSystemCandidateFromJsonMap = iterableTreeMap(stringOrd, iteratorList(arrayNode.iterator()).map(jsonNode -> new TrustmarkBindingRegistryFromJson.PartnerSystemCandidateFromJson() {

                        private final Ord<URI> uriOrd = ord((o1, o2) -> Ordering.fromInt(o1.compareTo(o2)));
                        private final String name = jsonNode.get("name") == null || jsonNode.get("name").isNull() ? null : jsonNode.get("name").asText();
                        private final String entityId = jsonNode.get("entityId") == null || jsonNode.get("entityId").isNull() ? null : jsonNode.get("entityId").asText();
                        private final PartnerSystemCandidateType providerType = jsonNode.get("providerType") == null || jsonNode.get("providerType").isNull() ? null : fromNameForTrustmarkBindingRegistry(jsonNode.get("providerType").asText());
                        private final String saml2MetadataUrl = jsonNode.get("saml2MetadataUrl") == null || jsonNode.get("saml2MetadataUrl").isNull() ? null : jsonNode.get("saml2MetadataUrl").asText();
                        private final TreeMap<URI, TrustmarkFromJson> trustmarkMap = jsonNode.get("trustmarks") == null || jsonNode.get("trustmarks").isNull() || !jsonNode.get("trustmarks").isArray() ?
                                empty(uriOrd) :
                                iterableTreeMap(uriOrd, iteratorList(jsonNode.get("trustmarks").elements())
                                        .filter(jsonNodeInner -> jsonNodeInner.get("url") != null && !jsonNodeInner.get("url").isNull())
                                        .map(jsonNodeInner -> new TrustmarkFromJson() {

                                            private final URI url = URI.create(jsonNodeInner.get("url").asText());

                                            @Override
                                            public URI getUrl() {
                                                return url;
                                            }
                                        })
                                        .groupBy(trustmarkFromJson -> trustmarkFromJson.getUrl(), uriOrd)
                                        .toList()
                                        .map(p -> p(p._1(), p._2().head())));

                        @Override
                        public String getName() {

                            return name;
                        }

                        @Override
                        public String getEntityId() {

                            return entityId;
                        }

                        @Override
                        public PartnerSystemCandidateType getProviderType() {

                            return providerType;
                        }

                        @Override
                        public String getSaml2MetadataUrl() {

                            return saml2MetadataUrl;
                        }

                        @Override
                        public TreeMap<URI, TrustmarkFromJson> getTrustmarkMap() {

                            return trustmarkMap;
                        }

                        @Override
                        public JsonNode getJsonNode() {

                            return jsonNode;
                        }
                    })
                    .groupBy(partnerSystemCandidateFromJson -> partnerSystemCandidateFromJson.getEntityId(), stringOrd)
                    .toList()
                    .map(p -> p.map2(List::head)));


            @Override
            public URI getURI() {
                return uri;
            }

            @Override
            public String getSource() {
                return arrayNode.toString();
            }

            @Override
            public TreeMap<String, PartnerSystemCandidateFromJson> getProviderMap() {
                return partnerSystemCandidateFromJsonMap;
            }
        };
    }

    public static byte[] hash(final TrustmarkBindingRegistryFromJson trustmarkBindingRegistryFromJson) {

        return messageDigest.digest(trustmarkBindingRegistryFromJson.getSource().getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] hash(final TrustmarkBindingRegistryFromJson.PartnerSystemCandidateFromJson partnerSystemCandidateFromJson) {

        return messageDigest.digest(new JSONArray(partnerSystemCandidateFromJson.getTrustmarkMap().keys().map(URI::toString).sort(stringOrd).toJavaList()).toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void synchronizeTrustmarkBindingRegistryUri() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> TrustmarkBindingRegistryUri.findAllHelper()
                        .bind(trustmarkBindingRegistryUri -> arrayList(PartnerSystemCandidateType.values())
                                .map(partnerSystemCandidateType -> TrustmarkBindingRegistryUriType.findByUriHelper(trustmarkBindingRegistryUri.getUri() + partnerSystemCandidateType.getUriRelative())
                                        .orSome(() -> {
                                            final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType = new TrustmarkBindingRegistryUriType();
                                            trustmarkBindingRegistryUriType.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);
                                            trustmarkBindingRegistryUriType.setUri(trustmarkBindingRegistryUri.getUri() + partnerSystemCandidateType.getUriRelative());
                                            trustmarkBindingRegistryUriType.setType(partnerSystemCandidateType);
                                            return trustmarkBindingRegistryUriType.saveHelper();
                                        }))))
                .forEach(trustmarkBindingRegistryUriType -> synchronizeTrustmarkBindingRegistryUriType(now, trustmarkBindingRegistryUriType.getUri()));
    }

    public static void synchronizeTrustmarkBindingRegistryUri(final LocalDateTime now, final String uriString) {

        TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> TrustmarkBindingRegistryUri.findByUriHelper(uriString)
                        .map(trustmarkBindingRegistryUri -> arrayList(PartnerSystemCandidateType.values())
                                .map(partnerSystemCandidateType -> TrustmarkBindingRegistryUriType.findByUriHelper(trustmarkBindingRegistryUri.getUri() + partnerSystemCandidateType.getUriRelative())
                                        .orSome(() -> {
                                            final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType = new TrustmarkBindingRegistryUriType();
                                            trustmarkBindingRegistryUriType.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);
                                            trustmarkBindingRegistryUriType.setUri(trustmarkBindingRegistryUri.getUri() + partnerSystemCandidateType.getUriRelative());
                                            trustmarkBindingRegistryUriType.setType(partnerSystemCandidateType);
                                            return trustmarkBindingRegistryUriType.saveHelper();
                                        })))
                        .orSome(nil()))
                .forEach(trustmarkBindingRegistryUriType -> synchronizeTrustmarkBindingRegistryUriType(now, trustmarkBindingRegistryUriType.getUri()));
    }

    public static void synchronizeTrustmarkBindingRegistryUriType(final LocalDateTime now, final String uriString) {

        final Effect1<TrustmarkBindingRegistryUriType> historyEffect = trustmarkBindingRegistryUriType -> {

            final TrustmarkBindingRegistryUriTypeHistory trustmarkBindingRegistryUriTypeHistory = new TrustmarkBindingRegistryUriTypeHistory();
            trustmarkBindingRegistryUriTypeHistory.setUri(trustmarkBindingRegistryUriType.getUri());
            trustmarkBindingRegistryUriTypeHistory.setType(trustmarkBindingRegistryUriType.getType());
            trustmarkBindingRegistryUriTypeHistory.setHash(trustmarkBindingRegistryUriType.getHash());
            trustmarkBindingRegistryUriTypeHistory.setJson(trustmarkBindingRegistryUriType.getJson());
            trustmarkBindingRegistryUriTypeHistory.setRequestLocalDateTime(trustmarkBindingRegistryUriType.getRequestLocalDateTime());
            trustmarkBindingRegistryUriTypeHistory.setSuccessLocalDateTime(trustmarkBindingRegistryUriType.getSuccessLocalDateTime());
            trustmarkBindingRegistryUriTypeHistory.setFailureLocalDateTime(trustmarkBindingRegistryUriType.getFailureLocalDateTime());
            trustmarkBindingRegistryUriTypeHistory.setChangeLocalDateTime(trustmarkBindingRegistryUriType.getChangeLocalDateTime());
            trustmarkBindingRegistryUriTypeHistory.setFailureMessage(trustmarkBindingRegistryUriType.getFailureMessage());
            trustmarkBindingRegistryUriTypeHistory.saveHelper();
        };

        final P2<String, Validation<NonEmptyList<Exception>, P3<URI, TrustmarkBindingRegistryFromJson, String>>> p = createResolveHash("Trustmark binding registry", uriString, JobUtilityForTrustmarkBindingRegistryUri::resolve, JobUtilityForTrustmarkBindingRegistryUri::hash);

        TrustmarkBindingRegistryUriType.withTransactionHelper(() ->
                TrustmarkBindingRegistryUriType.findByUriHelper(p._1())
                        .forEach(trustmarkBindingRegistryUriType -> {

                            log.info(format("Trustmark binding registry uri '%s' time changed.", trustmarkBindingRegistryUriType.getUri()));

                            reduce(p._2().toEither().bimap(
                                    nel -> {

                                        trustmarkBindingRegistryUriType.setRequestLocalDateTime(now);
                                        trustmarkBindingRegistryUriType.setFailureLocalDateTime(now);
                                        trustmarkBindingRegistryUriType.setFailureMessage(truncate(nel.head().getMessage(), 1000));

                                        log.info(format("Trustmark binding registry uri '%s' relying on cache.", trustmarkBindingRegistryUriType.getUri()));

                                        historyEffect.f(trustmarkBindingRegistryUriType);

                                        trustmarkBindingRegistryUriType.partnerSystemCandidateSetHelper().forEach(partnerSystemCandidate -> {

                                            log.info(format("Trustmark binding registry uri '%s' relying on cache; partner system candidate uri '%s' relying on cache.", trustmarkBindingRegistryUriType.getUri(), partnerSystemCandidate.getUri()));

                                            partnerSystemCandidate.setRequestLocalDateTime(now);
                                            partnerSystemCandidate.setFailureLocalDateTime(now);
                                            partnerSystemCandidate.setFailureMessage(truncate(nel.head().getMessage(), 1000));
                                            partnerSystemCandidate.saveHelper();
                                        });

                                        return trustmarkBindingRegistryUriType;
                                    },
                                    pInner -> {

                                        final TrustmarkBindingRegistryFromJson trustmarkBindingRegistryFromJson = pInner._2();
                                        final String hash = pInner._3();

                                        trustmarkBindingRegistryUriType.setRequestLocalDateTime(now);
                                        trustmarkBindingRegistryUriType.setSuccessLocalDateTime(now);

                                        if (trustmarkBindingRegistryUriType.getHash() == null || !trustmarkBindingRegistryUriType.getHash().equals(hash)) {

                                            log.info(format("Trustmark binding registry uri '%s' content changed.", trustmarkBindingRegistryUriType.getUri()));

                                            trustmarkBindingRegistryUriType.setJson(trustmarkBindingRegistryFromJson.getSource());
                                            trustmarkBindingRegistryUriType.setHash(hash);
                                            trustmarkBindingRegistryUriType.setChangeLocalDateTime(now);

                                            historyEffect.f(trustmarkBindingRegistryUriType);

                                            final P3<TreeMap<String, TrustmarkBindingRegistryFromJson.PartnerSystemCandidateFromJson>, List<PartnerSystemCandidate>, List<PartnerSystemCandidate>> pUpdate1 = trustmarkBindingRegistryUriType.partnerSystemCandidateSetHelper()
                                                    .foldLeft((pUpdateInner, partnerSystemCandidateOld) -> pUpdateInner._1()
                                                                    .get(partnerSystemCandidateOld.getUri())
                                                                    .map(partnerSystemCandidateFromJson -> {

                                                                        partnerSystemCandidateOld.setName(partnerSystemCandidateFromJson.getName());
                                                                        partnerSystemCandidateOld.setUri(partnerSystemCandidateFromJson.getEntityId());
                                                                        partnerSystemCandidateOld.setUriEntityDescriptor(partnerSystemCandidateFromJson.getSaml2MetadataUrl());
                                                                        partnerSystemCandidateOld.setType(partnerSystemCandidateFromJson.getProviderType());
                                                                        partnerSystemCandidateOld.setRequestLocalDateTime(now);
                                                                        partnerSystemCandidateOld.setSuccessLocalDateTime(now);

                                                                        final String partnerSystemCandidateOldHash = new String(Hex.encode(hash(partnerSystemCandidateFromJson)));

                                                                        if (partnerSystemCandidateOld.getHash() == null || !partnerSystemCandidateOld.getHash().equals(partnerSystemCandidateOldHash)) {

                                                                            log.info(format("Trustmark binding registry uri '%s' content changed; partner system candidate uri '%s' changed.", trustmarkBindingRegistryUriType.getUri(), partnerSystemCandidateOld.getUri()));

                                                                            partnerSystemCandidateOld.partnerSystemCandidateTrustmarkUriSetHelper().forEach(partnerSystemCandidateTrustmarkUri -> {
                                                                                partnerSystemCandidateTrustmarkUri.partnerSystemCandidateHelper(null);
                                                                                partnerSystemCandidateTrustmarkUri.trustmarkUriHelper(null);
                                                                                partnerSystemCandidateTrustmarkUri.deleteHelper();
                                                                            });

                                                                            partnerSystemCandidateOld.partnerSystemCandidateTrustmarkUriSetHelper(
                                                                                    partnerSystemCandidateFromJson.getTrustmarkMap().keys()
                                                                                            .map(uri -> TrustmarkUri
                                                                                                    .findByUriHelper(uri.toString())
                                                                                                    .orSome(() -> {
                                                                                                        final TrustmarkUri trustmarkUri = new TrustmarkUri();
                                                                                                        trustmarkUri.setUri(uri.toString());
                                                                                                        return trustmarkUri.saveHelper();
                                                                                                    }))
                                                                                            .map(trustmarkUri -> {
                                                                                                final PartnerSystemCandidateTrustmarkUri partnerSystemCandidateTrustmarkUri = new PartnerSystemCandidateTrustmarkUri();
                                                                                                partnerSystemCandidateTrustmarkUri.partnerSystemCandidateHelper(partnerSystemCandidateOld);
                                                                                                partnerSystemCandidateTrustmarkUri.trustmarkUriHelper(trustmarkUri);
                                                                                                return partnerSystemCandidateTrustmarkUri;
                                                                                            }));

                                                                            partnerSystemCandidateOld.setJson(partnerSystemCandidateFromJson.getJsonNode().toString());
                                                                            partnerSystemCandidateOld.setHash(partnerSystemCandidateOldHash);
                                                                            partnerSystemCandidateOld.setChangeLocalDateTime(now);

                                                                        } else {

                                                                            log.info(format("Trustmark binding registry uri '%s' content changed; partner system candidate uri '%s' content did not change.", trustmarkBindingRegistryUriType.getUri(), partnerSystemCandidateOld.getUri()));
                                                                        }

                                                                        return p(
                                                                                pUpdateInner._1().delete(partnerSystemCandidateFromJson.getEntityId()),
                                                                                pUpdateInner._2().snoc(partnerSystemCandidateOld),
                                                                                pUpdateInner._3());
                                                                    })
                                                                    .orSome(() -> {

                                                                        log.info(format("Trustmark binding registry uri '%s' content changed; partner system candidate uri '%s' relying on cache.", trustmarkBindingRegistryUriType.getUri(), partnerSystemCandidateOld.getUri()));

                                                                        partnerSystemCandidateOld.setRequestLocalDateTime(now);
                                                                        partnerSystemCandidateOld.setFailureLocalDateTime(now);
                                                                        partnerSystemCandidateOld.setFailureMessage("The trustmark binding registry did not reference this partner system candidate.");

                                                                        return p(
                                                                                pUpdateInner._1(),
                                                                                pUpdateInner._2(),
                                                                                pUpdateInner._3().snoc(partnerSystemCandidateOld));
                                                                    }),
                                                            p(trustmarkBindingRegistryFromJson.getProviderMap(), List.<PartnerSystemCandidate>nil(), List.<PartnerSystemCandidate>nil()));

                                            final P3<List<PartnerSystemCandidate>, List<PartnerSystemCandidate>, List<PartnerSystemCandidate>> pUpdate2 = p(
                                                    pUpdate1._1().values().map(partnerSystemCandidateFromJson -> {

                                                        log.info(format("Trustmark binding registry uri '%s' content changed; partner system candidate uri '%s' changed.", trustmarkBindingRegistryUriType.getUri(), partnerSystemCandidateFromJson.getEntityId()));

                                                        final PartnerSystemCandidate partnerSystemCandidate = new PartnerSystemCandidate();

                                                        partnerSystemCandidate.trustmarkBindingRegistryUriTypeHelper(trustmarkBindingRegistryUriType);
                                                        partnerSystemCandidate.setName(partnerSystemCandidateFromJson.getName());
                                                        partnerSystemCandidate.setUri(partnerSystemCandidateFromJson.getEntityId());
                                                        partnerSystemCandidate.setUriEntityDescriptor(partnerSystemCandidateFromJson.getSaml2MetadataUrl());
                                                        partnerSystemCandidate.setType(partnerSystemCandidateFromJson.getProviderType());
                                                        partnerSystemCandidate.setHash(partnerSystemCandidateFromJson.getName());
                                                        partnerSystemCandidate.setRequestLocalDateTime(now);
                                                        partnerSystemCandidate.setSuccessLocalDateTime(now);
                                                        partnerSystemCandidate.setJson(partnerSystemCandidateFromJson.getJsonNode().toString());
                                                        partnerSystemCandidate.setHash(new String(Hex.encode(hash(partnerSystemCandidateFromJson))));
                                                        partnerSystemCandidate.setChangeLocalDateTime(now);

                                                        partnerSystemCandidate.partnerSystemCandidateTrustmarkUriSetHelper(
                                                                partnerSystemCandidateFromJson.getTrustmarkMap().keys()
                                                                        .map(uri -> TrustmarkUri
                                                                                .findByUriHelper(uri.toString())
                                                                                .orSome(() -> {
                                                                                    final TrustmarkUri trustmarkUri = new TrustmarkUri();
                                                                                    trustmarkUri.setUri(uri.toString());
                                                                                    return trustmarkUri.saveHelper();
                                                                                }))
                                                                        .map(trustmarkUri -> {
                                                                            final PartnerSystemCandidateTrustmarkUri partnerSystemCandidateTrustmarkUri = new PartnerSystemCandidateTrustmarkUri();
                                                                            partnerSystemCandidateTrustmarkUri.partnerSystemCandidateHelper(partnerSystemCandidate);
                                                                            partnerSystemCandidateTrustmarkUri.trustmarkUriHelper(trustmarkUri);
                                                                            return partnerSystemCandidateTrustmarkUri;
                                                                        }));

                                                        return partnerSystemCandidate;
                                                    }),
                                                    pUpdate1._2(),
                                                    pUpdate1._3());

                                            pUpdate2._1().forEach(partnerSystemCandidate -> partnerSystemCandidate.saveHelper()); // insert
                                            pUpdate2._2().forEach(partnerSystemCandidate -> partnerSystemCandidate.saveHelper()); // update
                                            pUpdate2._3().forEach(partnerSystemCandidate -> partnerSystemCandidate.saveHelper()); // delete?

                                            trustmarkBindingRegistryUriType.partnerSystemCandidateSetHelper(
                                                    pUpdate2._1()
                                                            .append(pUpdate2._2())
                                                            .append(pUpdate2._3()));

                                        } else {

                                            log.info(format("Trustmark binding registry uri '%s' content did not change.", trustmarkBindingRegistryUriType.getUri()));

                                            trustmarkBindingRegistryUriType.partnerSystemCandidateSetHelper().forEach(partnerSystemCandidate -> {
                                                partnerSystemCandidate.setRequestLocalDateTime(now);
                                                partnerSystemCandidate.setSuccessLocalDateTime(now);
                                                partnerSystemCandidate.saveHelper();
                                            });
                                        }

                                        return trustmarkBindingRegistryUriType;
                                    })).saveHelper();
                        }));
    }
}
