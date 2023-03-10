package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkBindingRegistryOrganizationMapResolver extends DatabaseCacheResolver<TrustmarkBindingRegistryOrganizationMap> implements TrustmarkBindingRegistryOrganizationMapResolver {

    public DatabaseCacheTrustmarkBindingRegistryOrganizationMapResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationMapResolver.class),
                (uriString) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationMapUri.findByUriHelper(uriString).map(TrustmarkBindingRegistryOrganizationMapUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
