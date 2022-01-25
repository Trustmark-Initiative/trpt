package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidateTrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryOrganizationMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganization;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
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

import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.product.P.p;

public class JobUtilityForPartnerOrganizationCandidate {

    private JobUtilityForPartnerOrganizationCandidate() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForPartnerOrganizationCandidate.class);
    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);
    private static final TrustmarkBindingRegistryOrganizationMapJsonDeserializer trustmarkBindingRegistryOrganizationMapJsonDeserializer = new TrustmarkBindingRegistryOrganizationMapJsonDeserializer();

    /**
     * Synchronize all partner organization candidates; use now as the timestamp.
     */
    public static void synchronizePartnerOrganizationCandidate(final Duration duration) {

        synchronizePartnerOrganizationCandidate(duration, LocalDateTime.now(ZoneOffset.UTC));
    }

    /**
     * Synchronize all partner organization candidates; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizePartnerOrganizationCandidate(final Duration duration, final LocalDateTime now) {

        synchronizePartnerOrganizationCandidate(duration, now, () -> TrustmarkBindingRegistryUri.findAllHelper()
                .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistryOrganizationMapUriSetHelper()));
    }

    /**
     * Synchronize all partner organization candidates; use now as the timestamp.
     *
     * @param uriString the trustmark binding registry organization map uri
     */
    public static void synchronizePartnerOrganizationCandidate(final Duration duration, final String uriString) {

        synchronizePartnerOrganizationCandidate(duration, LocalDateTime.now(ZoneOffset.UTC), uriString);
    }

    /**
     * Synchronize the partner organization candidates associated with the trustmark binding registry organization map uri; use now as the timestamp.
     *
     * @param now       the timestamp
     * @param uriString the trustmark binding registry organization map uri
     */
    public static void synchronizePartnerOrganizationCandidate(final Duration duration, final LocalDateTime now, final String uriString) {

        synchronizePartnerOrganizationCandidate(duration, now, () -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList()
                .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistryOrganizationMapUriSetHelper()));
    }

    private static void synchronizePartnerOrganizationCandidate(final Duration duration, final LocalDateTime now, final F0<List<TrustmarkBindingRegistryOrganizationMapUri>> find) {

        retry(() -> TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> find.f()
                        .forEach(trustmarkBindingRegistryOrganizationMapUri -> fromNull(trustmarkBindingRegistryOrganizationMapUri.getDocument())
                                .forEach(document -> Try.f(() -> trustmarkBindingRegistryOrganizationMapJsonDeserializer.deserialize(document))._1().toEither().foreachDoEffect(
                                        parseException -> log.info(format("Trustmark binding registry organization map uri '%s' document read failure: '%s'.", trustmarkBindingRegistryOrganizationMapUri.getUri(), parseException.getMessage())),
                                        trustmarkBindingRegistryOrganizationMap -> synchronizePartnerOrganizationCandidate(duration, now, trustmarkBindingRegistryOrganizationMapUri, trustmarkBindingRegistryOrganizationMap))))), log);
    }

    private static void synchronizePartnerOrganizationCandidate(final Duration duration, final LocalDateTime now, final TrustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri, final TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap) {

        final P3<TreeMap<String, TrustmarkBindingRegistryOrganization>, List<PartnerOrganizationCandidate>, List<PartnerOrganizationCandidate>> pUpdate1 = trustmarkBindingRegistryOrganizationMapUri.partnerOrganizationCandidateSetHelper()
                .foldLeft((pUpdateInner, partnerOrganizationCandidateOld) -> pUpdateInner._1()
                                .get(partnerOrganizationCandidateOld.getIdentifier())
                                .map(trustmarkBindingRegistryOrganization -> {

                                    partnerOrganizationCandidateOld.setIdentifier(trustmarkBindingRegistryOrganization.getIdentifier());
                                    partnerOrganizationCandidateOld.setName(trustmarkBindingRegistryOrganization.getName());
                                    partnerOrganizationCandidateOld.setNameLong(trustmarkBindingRegistryOrganization.getDisplayName());
                                    partnerOrganizationCandidateOld.setDescription(trustmarkBindingRegistryOrganization.getDescription());

                                    partnerOrganizationCandidateOld.setRequestLocalDateTime(now);
                                    partnerOrganizationCandidateOld.setSuccessLocalDateTime(now);

                                    final String partnerOrganizationCandidateOldHash = reduce(Try.f(() -> new String(Hex.encode(hashFactory.hash(trustmarkBindingRegistryOrganization))))._1().toEither().leftMap(ioException -> {
                                        log.info(format("Trustmark binding registry organization map uri '%s'; partner organization candidate '%' hash failure: '%s'.", trustmarkBindingRegistryOrganizationMapUri.getUri(), trustmarkBindingRegistryOrganization.getIdentifier(), ioException.getMessage()));
                                        return null;
                                    }));

                                    if (partnerOrganizationCandidateOld.getHash() == null || !partnerOrganizationCandidateOld.getHash().equals(partnerOrganizationCandidateOldHash)) {

                                        log.info(format("Trustmark binding registry organization map uri '%s' content changed; partner organization candidate uri '%s' changed.", trustmarkBindingRegistryOrganizationMapUri.getUri(), partnerOrganizationCandidateOld.getIdentifier()));

                                        partnerOrganizationCandidateOld.partnerOrganizationCandidateTrustmarkUriSetHelper().forEach(partnerOrganizationCandidateTrustmarkUri -> {
                                            partnerOrganizationCandidateTrustmarkUri.partnerOrganizationCandidateHelper(null);
                                            partnerOrganizationCandidateTrustmarkUri.trustmarkUriHelper(null);
                                            partnerOrganizationCandidateTrustmarkUri.deleteHelper();
                                        });

                                        partnerOrganizationCandidateOld.partnerOrganizationCandidateTrustmarkUriSetHelper(
                                                trustmarkBindingRegistryOrganization.getOrganizationTrustmarkMap().values().map(TrustmarkBindingRegistryOrganizationTrustmark::getTrustmarkIdentifier)
                                                        .map(uri -> TrustmarkUri
                                                                .findByUriHelper(uri.toString())
                                                                .orSome(() -> {
                                                                    final TrustmarkUri trustmarkUri = new TrustmarkUri();
                                                                    trustmarkUri.setUri(uri.toString());
                                                                    return trustmarkUri.saveHelper();
                                                                }))
                                                        .map(trustmarkUri -> {
                                                            final PartnerOrganizationCandidateTrustmarkUri partnerOrganizationCandidateTrustmarkUri = new PartnerOrganizationCandidateTrustmarkUri();
                                                            partnerOrganizationCandidateTrustmarkUri.partnerOrganizationCandidateHelper(partnerOrganizationCandidateOld);
                                                            partnerOrganizationCandidateTrustmarkUri.trustmarkUriHelper(trustmarkUri);
                                                            return partnerOrganizationCandidateTrustmarkUri;
                                                        }));

                                        partnerOrganizationCandidateOld.setJson(trustmarkBindingRegistryOrganization.getOriginalSource());
                                        partnerOrganizationCandidateOld.setHash(partnerOrganizationCandidateOldHash);
                                        partnerOrganizationCandidateOld.setChangeLocalDateTime(now);

                                    } else {

                                        log.info(format("Trustmark binding registry organization map uri '%s' content changed; partner organization candidate uri '%s' content did not change.", trustmarkBindingRegistryOrganizationMapUri.getUri(), partnerOrganizationCandidateOld.getIdentifier()));
                                    }

                                    return p(
                                            pUpdateInner._1().delete(trustmarkBindingRegistryOrganization.getIdentifier()),
                                            pUpdateInner._2().snoc(partnerOrganizationCandidateOld),
                                            pUpdateInner._3());
                                })
                                .orSome(() -> {

                                    log.info(format("Trustmark binding registry organization map uri '%s' content changed; partner organization candidate uri '%s' relying on cache, if any.", trustmarkBindingRegistryOrganizationMapUri.getUri(), partnerOrganizationCandidateOld.getIdentifier()));

                                    partnerOrganizationCandidateOld.setRequestLocalDateTime(now);
                                    partnerOrganizationCandidateOld.setFailureLocalDateTime(now);
                                    partnerOrganizationCandidateOld.setFailureMessage("The trustmark binding registry did not reference this partner organization candidate.");

                                    return p(
                                            pUpdateInner._1(),
                                            pUpdateInner._2(),
                                            pUpdateInner._3().snoc(partnerOrganizationCandidateOld));
                                }),
                        p(trustmarkBindingRegistryOrganizationMap.getOrganizationMap(), List.<PartnerOrganizationCandidate>nil(), List.<PartnerOrganizationCandidate>nil()));

        final P3<List<PartnerOrganizationCandidate>, List<PartnerOrganizationCandidate>, List<PartnerOrganizationCandidate>> pUpdate2 = p(
                pUpdate1._1().values().map(trustmarkBindingRegistryOrganization -> {

                    log.info(format("Trustmark binding registry organization map uri '%s' content changed; partner organization candidate uri '%s' changed.", trustmarkBindingRegistryOrganizationMapUri.getUri(), trustmarkBindingRegistryOrganization.getIdentifier()));

                    final PartnerOrganizationCandidate partnerOrganizationCandidate = new PartnerOrganizationCandidate();

                    partnerOrganizationCandidate.trustmarkBindingRegistryOrganizationMapUriHelper(trustmarkBindingRegistryOrganizationMapUri);

                    partnerOrganizationCandidate.setIdentifier(trustmarkBindingRegistryOrganization.getIdentifier());
                    partnerOrganizationCandidate.setName(trustmarkBindingRegistryOrganization.getName());
                    partnerOrganizationCandidate.setNameLong(trustmarkBindingRegistryOrganization.getDisplayName());
                    partnerOrganizationCandidate.setDescription(trustmarkBindingRegistryOrganization.getDescription());

                    partnerOrganizationCandidate.setTrustmarkRecipientIdentifierArrayJson(new JSONArray(trustmarkBindingRegistryOrganization.getOrganizationTrustmarkMap().values().map(TrustmarkBindingRegistryOrganizationTrustmark::getTrustmarkIdentifier).map(URI::toString).toCollection()).toString());

                    partnerOrganizationCandidate.setRequestLocalDateTime(now);
                    partnerOrganizationCandidate.setSuccessLocalDateTime(now);

                    partnerOrganizationCandidate.setJson(trustmarkBindingRegistryOrganization.getOriginalSource());
                    partnerOrganizationCandidate.setHash(reduce(Try.f(() -> new String(Hex.encode(hashFactory.hash(trustmarkBindingRegistryOrganization))))._1().toEither().leftMap(ioException -> {
                        log.info(format("Trustmark binding registry organization map uri '%s'; partner organization candidate '%' hash failure: '%s'.", trustmarkBindingRegistryOrganizationMapUri.getUri(), trustmarkBindingRegistryOrganization.getIdentifier(), ioException.getMessage()));
                        return null;
                    })));
                    partnerOrganizationCandidate.setChangeLocalDateTime(now);

                    partnerOrganizationCandidate.partnerOrganizationCandidateTrustmarkUriSetHelper(
                            trustmarkBindingRegistryOrganization.getOrganizationTrustmarkMap().values().map(TrustmarkBindingRegistryOrganizationTrustmark::getTrustmarkIdentifier)
                                    .map(uri -> TrustmarkUri
                                            .findByUriHelper(uri.toString())
                                            .orSome(() -> {
                                                final TrustmarkUri trustmarkUri = new TrustmarkUri();
                                                trustmarkUri.setUri(uri.toString());
                                                return trustmarkUri.saveHelper();
                                            }))
                                    .map(trustmarkUri -> {
                                        final PartnerOrganizationCandidateTrustmarkUri partnerOrganizationCandidateTrustmarkUri = new PartnerOrganizationCandidateTrustmarkUri();
                                        partnerOrganizationCandidateTrustmarkUri.partnerOrganizationCandidateHelper(partnerOrganizationCandidate);
                                        partnerOrganizationCandidateTrustmarkUri.trustmarkUriHelper(trustmarkUri);
                                        return partnerOrganizationCandidateTrustmarkUri;
                                    }));

                    return partnerOrganizationCandidate;
                }),
                pUpdate1._2(),
                pUpdate1._3());

        pUpdate2._1().forEach(partnerOrganizationCandidate -> partnerOrganizationCandidate.saveHelper()); // insert
        pUpdate2._2().forEach(partnerOrganizationCandidate -> partnerOrganizationCandidate.saveHelper()); // update
        pUpdate2._3().forEach(partnerOrganizationCandidate -> partnerOrganizationCandidate.saveHelper()); // delete?

        // synchronize evaluations, if necessary
//
//        synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(duration, pUpdate2._1(), nil(), nil());

        trustmarkBindingRegistryOrganizationMapUri.partnerOrganizationCandidateSetHelper(
                pUpdate2._1()
                        .append(pUpdate2._2())
                        .append(pUpdate2._3()));

        trustmarkBindingRegistryOrganizationMapUri.saveHelper();
    }
}
