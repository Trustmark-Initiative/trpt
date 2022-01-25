package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap extends UriSynchronizer<TrustmarkBindingRegistryOrganizationTrustmarkMap, TrustmarkBindingRegistryOrganizationTrustmarkMapUri, TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap();

    private UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap.class),
                "Trustmark Binding Registry Organization Trustmark Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationTrustmarkMapResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                () -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.withTransactionHelper(() -> f.f(TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistryOrganizationTrustmarkMapUri(),
                TrustmarkBindingRegistryOrganizationTrustmarkMapUri::saveHelper,
                () -> new TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory(),
                TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory::saveHelper);
    }
}
