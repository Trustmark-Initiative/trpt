package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriType;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.gtri.fj.data.Option.fromNull;

public final class DatabaseCacheTrustmarkBindingRegistryResolver extends DatabaseCacheResolver<TrustmarkBindingRegistry, TrustmarkBindingRegistryUriType> implements TrustmarkBindingRegistryResolver {

    public DatabaseCacheTrustmarkBindingRegistryResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistryResolver.class),
                (uriString) -> TrustmarkBindingRegistryUriType.withTransactionHelper(() -> TrustmarkBindingRegistryUriType.findByUriHelper(uriString)),
                uri -> fromNull(uri.getDocument()),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistry.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
