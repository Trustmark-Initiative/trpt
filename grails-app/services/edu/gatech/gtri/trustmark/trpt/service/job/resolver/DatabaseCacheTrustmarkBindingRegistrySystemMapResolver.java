package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistrySystemMap;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.gtri.fj.data.Option.fromNull;

public final class DatabaseCacheTrustmarkBindingRegistrySystemMapResolver extends DatabaseCacheResolver<TrustmarkBindingRegistrySystemMap, TrustmarkBindingRegistrySystemMapUriType> implements TrustmarkBindingRegistrySystemMapResolver {

    public DatabaseCacheTrustmarkBindingRegistrySystemMapResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistrySystemMapResolver.class),
                (uriString) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistrySystemMapUriType.findByUriHelper(uriString)),
                uri -> fromNull(uri.getDocument()),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistrySystemMap.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
