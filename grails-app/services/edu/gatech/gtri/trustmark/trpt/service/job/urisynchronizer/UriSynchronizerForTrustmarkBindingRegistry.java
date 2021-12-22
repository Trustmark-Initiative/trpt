package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriTypeHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import org.apache.commons.logging.LogFactory;

import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;

public class UriSynchronizerForTrustmarkBindingRegistry extends UriSynchronizer<TrustmarkBindingRegistry, TrustmarkBindingRegistryUriType, TrustmarkBindingRegistryUriTypeHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistry INSTANCE = new UriSynchronizerForTrustmarkBindingRegistry();

    private UriSynchronizerForTrustmarkBindingRegistry() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistry.class),
                "Trustmark Binding Registry",
                FactoryLoader.getInstance(TrustmarkBindingRegistryResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                TrustmarkBindingRegistryUriType::withTransactionHelper,
                () -> TrustmarkBindingRegistryUriType.withTransactionHelper(() -> TrustmarkBindingRegistryUriType.findAllHelper()),
                TrustmarkBindingRegistryUriType::findByUriHelper,
                () -> new TrustmarkBindingRegistryUriType(),
                (hasSource, uri) -> {
                    uri.setType(hasSource.getSystemMap().values().headOption().map(TrustmarkBindingRegistrySystem::getSystemType).toNull());
                },
                TrustmarkBindingRegistryUriType::saveHelper,
                () -> new TrustmarkBindingRegistryUriTypeHistory(),
                (uri, uriHistory) -> {
                    uriHistory.setType(uri.getType());
                },
                TrustmarkBindingRegistryUriTypeHistory::saveHelper,
                true);
    }
}
