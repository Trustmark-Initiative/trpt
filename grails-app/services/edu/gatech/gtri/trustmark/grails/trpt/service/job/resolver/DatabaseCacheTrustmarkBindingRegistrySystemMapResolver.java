package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistrySystemMap;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkBindingRegistrySystemMapResolver extends DatabaseCacheResolver<TrustmarkBindingRegistrySystemMap> implements TrustmarkBindingRegistrySystemMapResolver {

    public DatabaseCacheTrustmarkBindingRegistrySystemMapResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistrySystemMapResolver.class),
                (uriString) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistrySystemMapUriType.findByUriHelper(uriString).map(TrustmarkBindingRegistrySystemMapUriType::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistrySystemMap.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
