package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.gtri.fj.data.Option.fromNull;

public final class DatabaseCacheTrustmarkBindingRegistryOrganizationMapResolver extends DatabaseCacheResolver<TrustmarkBindingRegistryOrganizationMap, TrustmarkBindingRegistryOrganizationMapUri> implements TrustmarkBindingRegistryOrganizationMapResolver {

    public DatabaseCacheTrustmarkBindingRegistryOrganizationMapResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationMapResolver.class),
                (uriString) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationMapUri.findByUriHelper(uriString)),
                uri -> fromNull(uri.getDocument()),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
