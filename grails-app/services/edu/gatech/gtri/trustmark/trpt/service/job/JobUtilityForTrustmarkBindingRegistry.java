package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistrySystemMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F0;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.fileFor;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerOrganizationCandidate.synchronizePartnerOrganizationCandidate;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidate.synchronizePartnerSystemCandidate;
import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static org.gtri.fj.data.List.arrayList;

public class JobUtilityForTrustmarkBindingRegistry {

    private JobUtilityForTrustmarkBindingRegistry() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForTrustmarkBindingRegistry.class);

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies(final Duration duration) {

        synchronizeTrustmarkBindingRegistryUri(duration);

        UriSynchronizerForTrustmarkBindingRegistrySystemMap.INSTANCE.synchronizeUri();
        synchronizePartnerSystemCandidate(duration);

        UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.INSTANCE.synchronizeUri();
        synchronizePartnerOrganizationCandidate(duration);
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     */
    public static void synchronizeTrustmarkBindingRegistryUri(final Duration duration) {

        synchronizeTrustmarkBindingRegistryUri(duration, LocalDateTime.now(ZoneOffset.UTC));
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies(final Duration duration, final LocalDateTime now) {

        synchronizeTrustmarkBindingRegistryUri(duration, now);
        UriSynchronizerForTrustmarkBindingRegistrySystemMap.INSTANCE.synchronizeUri();
        synchronizePartnerSystemCandidate(duration, now);

        UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.INSTANCE.synchronizeUri();
        synchronizePartnerOrganizationCandidate(duration, now);
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUri(final Duration duration, final LocalDateTime now) {

        synchronizeTrustmarkBindingRegistryUri(duration, now, () -> TrustmarkBindingRegistryUri.findAllHelper());
    }

    /**
     * Synchronize all trustmark binding registry URIs; use now as the timestamp.
     *
     * @param now the timestamp
     */
    public static void synchronizeTrustmarkBindingRegistryUriAndDependencies(final Duration duration, final LocalDateTime now, final String uriString) {

        synchronizeTrustmarkBindingRegistryUri(duration, now, uriString);
        retry(() -> TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList()
                        .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistrySystemMapUriTypeSetHelper())
                        .map(trustmarkBindingRegistrySystemMapUriType -> trustmarkBindingRegistrySystemMapUriType.getUri())), log)
                .map(uri -> UriSynchronizerForTrustmarkBindingRegistrySystemMap.INSTANCE.synchronizeUri(now, uri));
        retry(() -> TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList()
                        .bind(trustmarkBindingRegistryUri -> trustmarkBindingRegistryUri.trustmarkBindingRegistryOrganizationMapUriSetHelper())
                        .map(trustmarkBindingRegistryOrganizationMapUri -> trustmarkBindingRegistryOrganizationMapUri.getUri())), log)
                .map(uri -> UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.INSTANCE.synchronizeUri(now, uri));
        synchronizePartnerSystemCandidate(duration, now, uriString);
    }

    /**
     * Synchronize the trustmark binding registry URI; use now as the timestamp.
     *
     * @param now       the timestamp
     * @param uriString the uri
     */
    public static void synchronizeTrustmarkBindingRegistryUri(final Duration duration, final LocalDateTime now, final String uriString) {

        synchronizeTrustmarkBindingRegistryUri(duration, now, () -> TrustmarkBindingRegistryUri.findByUriHelper(uriString).toList());
    }

    private static void synchronizeTrustmarkBindingRegistryUri(final Duration duration, final LocalDateTime now, final F0<List<TrustmarkBindingRegistryUri>> find) {

        retry(() -> TrustmarkBindingRegistryUri
                .withTransactionHelper(() -> find.f()
                        .forEach(trustmarkBindingRegistryUri -> {
                            TrustmarkBindingRegistryOrganizationMapUri.findByUriHelper(trustmarkBindingRegistryUri.getUri() + "/public/organizations/").orSome(() -> {
                                final TrustmarkBindingRegistryOrganizationMapUri trustmarkBindingRegistryOrganizationMapUri = new TrustmarkBindingRegistryOrganizationMapUri();
                                trustmarkBindingRegistryOrganizationMapUri.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);
                                trustmarkBindingRegistryOrganizationMapUri.setUri(trustmarkBindingRegistryUri.getUri() + "/public/organizations/");
                                return trustmarkBindingRegistryOrganizationMapUri.saveHelper();
                            });

                            arrayList(TrustmarkBindingRegistrySystemType.values())
                                    .map(trustmarkBindingRegistrySystemType -> TrustmarkBindingRegistrySystemMapUriType.findByUriHelper(trustmarkBindingRegistryUri.getUri() + trustmarkBindingRegistrySystemType.getUriRelative())
                                            .orSome(() -> {
                                                final TrustmarkBindingRegistrySystemMapUriType trustmarkBindingRegistrySystemMapUriType = new TrustmarkBindingRegistrySystemMapUriType();
                                                trustmarkBindingRegistrySystemMapUriType.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri);
                                                trustmarkBindingRegistrySystemMapUriType.setUri(trustmarkBindingRegistryUri.getUri() + trustmarkBindingRegistrySystemType.getUriRelative());
                                                trustmarkBindingRegistrySystemMapUriType.setType(trustmarkBindingRegistrySystemType);
                                                trustmarkBindingRegistrySystemMapUriType.fileHelper(fileFor(new byte[]{}));
                                                return trustmarkBindingRegistrySystemMapUriType.saveHelper();
                                            }));
                        })), log);
    }
}
