package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriTypeHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmarkBindingRegistrySystemMap extends UriSynchronizer<TrustmarkBindingRegistrySystemMap, TrustmarkBindingRegistrySystemMapUriType, TrustmarkBindingRegistrySystemMapUriTypeHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistrySystemMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistrySystemMap();

    private UriSynchronizerForTrustmarkBindingRegistrySystemMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistrySystemMap.class),
                "Trustmark Binding Registry System Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistrySystemMapResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                () -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistrySystemMapUriType.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> f.f(TrustmarkBindingRegistrySystemMapUriType.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistrySystemMapUriType(),
                (hasSource, uri) -> {
                    uri.setType(hasSource.getSystemMap().values().headOption().map(TrustmarkBindingRegistrySystem::getSystemType).toNull());
                },
                TrustmarkBindingRegistrySystemMapUriType::saveHelper,
                () -> new TrustmarkBindingRegistrySystemMapUriTypeHistory(),
                (uri, uriHistory) -> {
                    uriHistory.setType(uri.getType());
                },
                TrustmarkBindingRegistrySystemMapUriTypeHistory::saveHelper);
    }
}
