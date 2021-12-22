package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriType;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystemType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F0;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidate.synchronizePartnerSystemCandidate;
import static org.gtri.fj.data.List.arrayList;

public class JobUtilityForTrustmarkBindingRegistry {

    private JobUtilityForTrustmarkBindingRegistry() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForTrustmarkBindingRegistry.class);

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies() {

        synchronizeTrustmarkBindingRegistryUri();
        UriSynchronizerForTrustmarkBindingRegistry.INSTANCE.synchronizeUri();
        synchronizePartnerSystemCandidate();
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     */
    public static void synchronizeTrustmarkBindingRegistryUri() {

        synchronizeTrustmarkBindingRegistryUri(LocalDateTime.now(ZoneOffset.UTC));
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies(final LocalDateTime now) {

        synchronizeTrustmarkBindingRegistryUri(now);
        UriSynchronizerForTrustmarkBindingRegistry.INSTANCE.synchronizeUri();
        synchronizePartnerSystemCandidate(now);
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUri(final LocalDateTime now) {

        synchronizeTrustmarkBindingRegistryUri(now, () -> TrustmarkBindingRegistryUri.findAllHelper());
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies(final LocalDateTime now, final String uriString) {

        synchronizeTrustmarkBindingRegistryUri(now, uriString);
        TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList()
                        .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistryUriTypeSetHelper())
                        .map(trustmarkBindingRegistryUriType -> UriSynchronizerForTrustmarkBindingRegistry.INSTANCE.synchronizeUri(now, trustmarkBindingRegistryUriType.getUri())));
        synchronizePartnerSystemCandidate(now, uriString);
    }

    /**
     * Synchronize the trustmark binding registry URI; use now as the timestamp.
     *
     * @param now       the timestamp
     * @param uriString the uri
     */
    public static void synchronizeTrustmarkBindingRegistryUri(final LocalDateTime now, final String uriString) {

        synchronizeTrustmarkBindingRegistryUri(now, () -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList());
    }

    private static void synchronizeTrustmarkBindingRegistryUri(final LocalDateTime now, final F0<List<TrustmarkBindingRegistryUri>> find) {

        TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> find.f()
                        .map(trustmarkBindingRegistryUri -> arrayList(TrustmarkBindingRegistrySystemType.values())
                                .map(trustmarkBindingRegistrySystemType -> TrustmarkBindingRegistryUriType.findByUriHelper(trustmarkBindingRegistryUri.getUri() + trustmarkBindingRegistrySystemType.getUriRelative())
                                        .orSome(() -> {
                                            final TrustmarkBindingRegistryUriType trustmarkBindingRegistryUriType = new TrustmarkBindingRegistryUriType();
                                            trustmarkBindingRegistryUriType.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);
                                            trustmarkBindingRegistryUriType.setUri(trustmarkBindingRegistryUri.getUri() + trustmarkBindingRegistrySystemType.getUriRelative());
                                            trustmarkBindingRegistryUriType.setType(trustmarkBindingRegistrySystemType);
                                            return trustmarkBindingRegistryUriType.saveAndFlushHelper();
                                        }))));
    }
}
