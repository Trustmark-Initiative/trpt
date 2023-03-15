package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkBindingRegistryOrganizationTrustmarkMapResolver extends DatabaseCacheResolver<TrustmarkBindingRegistryOrganizationTrustmarkMap> implements TrustmarkBindingRegistryOrganizationTrustmarkMapResolver {

    public DatabaseCacheTrustmarkBindingRegistryOrganizationTrustmarkMapResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationTrustmarkMapResolver.class),
                (uriString) -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findByUriHelper(uriString).map(TrustmarkBindingRegistryOrganizationTrustmarkMapUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
