package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmarkBindingRegistryOrganizationMap extends UriSynchronizer<TrustmarkBindingRegistryOrganizationMap, TrustmarkBindingRegistryOrganizationMapUri, TrustmarkBindingRegistryOrganizationMapUriHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistryOrganizationMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistryOrganizationMap();

    private UriSynchronizerForTrustmarkBindingRegistryOrganizationMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.class),
                "Trustmark Binding Registry Organization Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationMapResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                () -> TrustmarkBindingRegistryOrganizationMapUri.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationMapUri.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistryOrganizationMapUri.withTransactionHelper(() -> f.f(TrustmarkBindingRegistryOrganizationMapUri.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistryOrganizationMapUri(),
                TrustmarkBindingRegistryOrganizationMapUri::saveHelper,
                () -> new TrustmarkBindingRegistryOrganizationMapUriHistory(),
                TrustmarkBindingRegistryOrganizationMapUriHistory::saveHelper);
    }
}
