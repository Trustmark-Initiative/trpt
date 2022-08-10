package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistrySystemMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P3;
import org.json.JSONArray;
import org.springframework.security.crypto.codec.Hex;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.fileFor;
import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.stringFor;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri;
import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.product.P.p;

public class JobUtilityForPartnerSystemCandidate {

    private JobUtilityForPartnerSystemCandidate() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForPartnerSystemCandidate.class);
    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);
    private static final TrustmarkBindingRegistrySystemMapJsonDeserializer trustmarkBindingRegistrySystemMapJsonDeserializer = new TrustmarkBindingRegistrySystemMapJsonDeserializer();
    private static final TrustmarkBindingRegistrySystemJsonProducer trustmarkBindingRegistrySystemJsonProducer = new TrustmarkBindingRegistrySystemJsonProducer();

    /**
     * Synchronize all partner system candidates; use now as the timestamp.
     */
    public static void synchronizePartnerSystemCandidate(final Duration duration) {

        synchronizePartnerSystemCandidate(duration, LocalDateTime.now(ZoneOffset.UTC));
    }

    /**
     * Synchronize all partner system candidates; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizePartnerSystemCandidate(final Duration duration, final LocalDateTime now) {

        synchronizePartnerSystemCandidate(duration, now, () -> TrustmarkBindingRegistryUri.findAllHelper()
                .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistrySystemMapUriTypeSetHelper()));
    }

    /**
     * Synchronize all partner system candidates; use now as the timestamp.
     *
     * @param uriString the trustmark binding registry system map uri
     */
    public static void synchronizePartnerSystemCandidate(final Duration duration, final String uriString) {

        synchronizePartnerSystemCandidate(duration, LocalDateTime.now(ZoneOffset.UTC), uriString);
    }

    /**
     * Synchronize the partner system candidates associated with the trustmark binding registry system map uri; use now as the timestamp.
     *
     * @param now       the timestamp
     * @param uriString the trustmark binding registry system map uri
     */
    public static void synchronizePartnerSystemCandidate(final Duration duration, final LocalDateTime now, final String uriString) {

        synchronizePartnerSystemCandidate(duration, now, () -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList()
                .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistrySystemMapUriTypeSetHelper()));
    }

    private static void synchronizePartnerSystemCandidate(final Duration duration, final LocalDateTime now, final F0<List<TrustmarkBindingRegistrySystemMapUriType>> find) {

        retry(() -> TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> find.f()
                        .forEach(trustmarkBindingRegistrySystemMapUriType -> fromNull(stringFor(trustmarkBindingRegistrySystemMapUriType.fileHelper()))
                                .forEach(document -> Try.f(() -> trustmarkBindingRegistrySystemMapJsonDeserializer.deserialize(document))._1().toEither().foreachDoEffect(
                                        parseException -> log.info(format("Trustmark binding registry system map uri '%s' document read failure: '%s'.", trustmarkBindingRegistrySystemMapUriType.getUri(), parseException.getMessage())),
                                        trustmarkBindingRegistrySystemMap -> synchronizePartnerSystemCandidate(duration, now, trustmarkBindingRegistrySystemMapUriType, trustmarkBindingRegistrySystemMap))))), log);
    }

    private static void synchronizePartnerSystemCandidate(final Duration duration, final LocalDateTime now, final TrustmarkBindingRegistrySystemMapUriType trustmarkBindingRegistrySystemMapUriType, final TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap) {

        final P3<TreeMap<String, TrustmarkBindingRegistrySystem>, List<PartnerSystemCandidate>, List<PartnerSystemCandidate>> pUpdate1 = trustmarkBindingRegistrySystemMapUriType.partnerSystemCandidateSetHelper()
                .foldLeft((pUpdateInner, partnerSystemCandidateOld) -> pUpdateInner._1()
                                .get(partnerSystemCandidateOld.getUri())
                                .map(trustmarkBindingRegistrySystem -> {

                                    partnerSystemCandidateOld.setName(trustmarkBindingRegistrySystem.getName());
                                    partnerSystemCandidateOld.setUri(trustmarkBindingRegistrySystem.getIdentifier());
                                    partnerSystemCandidateOld.setUriEntityDescriptor(fromNull(trustmarkBindingRegistrySystem.getMetadata()).map(URI::toString).toNull());
                                    partnerSystemCandidateOld.setType(trustmarkBindingRegistrySystem.getSystemType());

                                    partnerSystemCandidateOld.setRequestLocalDateTime(now);
                                    partnerSystemCandidateOld.setSuccessLocalDateTime(now);

                                    final String partnerSystemCandidateOldHash = reduce(Try.f(() -> new String(Hex.encode(hashFactory.hash(trustmarkBindingRegistrySystem))))._1().toEither().leftMap(ioException -> {
                                        log.info(format("Trustmark binding registry system map uri '%s'; partner system candidate '%' hash failure: '%s'.", trustmarkBindingRegistrySystemMapUriType.getUri(), trustmarkBindingRegistrySystem.getIdentifier(), ioException.getMessage()));
                                        return null;
                                    }));

                                    if (partnerSystemCandidateOld.getHash() == null || !partnerSystemCandidateOld.getHash().equals(partnerSystemCandidateOldHash)) {

                                        log.info(format("Trustmark binding registry system map uri '%s' content changed; partner system candidate uri '%s' changed.", trustmarkBindingRegistrySystemMapUriType.getUri(), partnerSystemCandidateOld.getUri()));

                                        partnerSystemCandidateOld.partnerSystemCandidateTrustmarkUriSetHelper().forEach(partnerSystemCandidateTrustmarkUri -> {
                                            partnerSystemCandidateTrustmarkUri.partnerSystemCandidateHelper(null);
                                            partnerSystemCandidateTrustmarkUri.trustmarkUriHelper(null);
                                            partnerSystemCandidateTrustmarkUri.deleteHelper();
                                        });

                                        partnerSystemCandidateOld.partnerSystemCandidateTrustmarkUriSetHelper(
                                                trustmarkBindingRegistrySystem.getTrustmarks()
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

                                        partnerSystemCandidateOld.setDocument(fileFor(trustmarkBindingRegistrySystemJsonProducer.serialize(trustmarkBindingRegistrySystem).toString()));
                                        partnerSystemCandidateOld.setHash(partnerSystemCandidateOldHash);
                                        partnerSystemCandidateOld.setChangeLocalDateTime(now);

                                    } else {

                                        log.trace(format("Trustmark binding registry system map uri '%s' content changed; partner system candidate uri '%s' content did not change.", trustmarkBindingRegistrySystemMapUriType.getUri(), partnerSystemCandidateOld.getUri()));
                                    }

                                    return p(
                                            pUpdateInner._1().delete(trustmarkBindingRegistrySystem.getIdentifier()),
                                            pUpdateInner._2().snoc(partnerSystemCandidateOld),
                                            pUpdateInner._3());
                                })
                                .orSome(() -> {

                                    log.info(format("Trustmark binding registry system map uri '%s' content changed; partner system candidate uri '%s' relying on cache, if any.", trustmarkBindingRegistrySystemMapUriType.getUri(), partnerSystemCandidateOld.getUri()));

                                    partnerSystemCandidateOld.setRequestLocalDateTime(now);
                                    partnerSystemCandidateOld.setFailureLocalDateTime(now);
                                    partnerSystemCandidateOld.setFailureMessage("The trustmark binding registry did not reference this partner system candidate.");

                                    return p(
                                            pUpdateInner._1(),
                                            pUpdateInner._2(),
                                            pUpdateInner._3().snoc(partnerSystemCandidateOld));
                                }),
                        p(trustmarkBindingRegistrySystemMap.getSystemMap(), List.<PartnerSystemCandidate>nil(), List.<PartnerSystemCandidate>nil()));

        final P3<List<PartnerSystemCandidate>, List<PartnerSystemCandidate>, List<PartnerSystemCandidate>> pUpdate2 = p(
                pUpdate1._1().values().map(trustmarkBindingRegistrySystem -> {

                    log.info(format("Trustmark binding registry system map uri '%s' content changed; partner system candidate uri '%s' changed.", trustmarkBindingRegistrySystemMapUriType.getUri(), trustmarkBindingRegistrySystem.getIdentifier()));

                    final PartnerSystemCandidate partnerSystemCandidate = new PartnerSystemCandidate();

                    partnerSystemCandidate.trustmarkBindingRegistrySystemMapUriTypeHelper(trustmarkBindingRegistrySystemMapUriType);

                    partnerSystemCandidate.setName(trustmarkBindingRegistrySystem.getName());
                    partnerSystemCandidate.setUri(trustmarkBindingRegistrySystem.getIdentifier());
                    partnerSystemCandidate.setUriEntityDescriptor(fromNull(trustmarkBindingRegistrySystem.getMetadata()).map(URI::toString).toNull());
                    partnerSystemCandidate.setTrustmarkRecipientIdentifierArrayJson(new JSONArray(trustmarkBindingRegistrySystem.getTrustmarkRecipientIdentifiers().map(URI::toString).toCollection()).toString());
                    partnerSystemCandidate.setType(trustmarkBindingRegistrySystem.getSystemType());

                    partnerSystemCandidate.setRequestLocalDateTime(now);
                    partnerSystemCandidate.setSuccessLocalDateTime(now);

                    partnerSystemCandidate.setDocument(fileFor(trustmarkBindingRegistrySystemJsonProducer.serialize(trustmarkBindingRegistrySystem).toString()));
                    partnerSystemCandidate.setHash(reduce(Try.f(() -> new String(Hex.encode(hashFactory.hash(trustmarkBindingRegistrySystem))))._1().toEither().leftMap(ioException -> {
                        log.info(format("Trustmark binding registry system map uri '%s'; partner system candidate '%' hash failure: '%s'.", trustmarkBindingRegistrySystemMapUriType.getUri(), trustmarkBindingRegistrySystem.getIdentifier(), ioException.getMessage()));
                        return null;
                    })));
                    partnerSystemCandidate.setChangeLocalDateTime(now);

                    partnerSystemCandidate.partnerSystemCandidateTrustmarkUriSetHelper(
                            trustmarkBindingRegistrySystem.getTrustmarks()
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

        // synchronize evaluations, if necessary

        synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(duration, pUpdate2._1(), nil(), nil());

        trustmarkBindingRegistrySystemMapUriType.partnerSystemCandidateSetHelper(
                pUpdate2._1()
                        .append(pUpdate2._2())
                        .append(pUpdate2._3()));

        trustmarkBindingRegistrySystemMapUriType.saveHelper();
    }
}
