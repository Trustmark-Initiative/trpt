package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkDefinition;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverImplWithoutTerms;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkDefinitionResolver extends DatabaseCacheResolver<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public DatabaseCacheTrustmarkDefinitionResolver() {
        super(
                new TrustmarkDefinitionResolverImplWithoutTerms(),
                (uriString) -> TrustmarkDefinitionUri.withTransactionHelper(() -> TrustmarkDefinitionUri.findByUriHelper(uriString).map(TrustmarkDefinitionUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmarkDefinition.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}

