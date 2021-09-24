package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustmarkDefinitionUri.synchronizeTrustmarkDefinitionUri;

public final class DatabaseCacheTrustmarkDefinitionResolver extends DatabaseCacheResolver<TrustmarkDefinition, TrustmarkDefinitionUri> implements TrustmarkDefinitionResolver {

    public DatabaseCacheTrustmarkDefinitionResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkDefinitionResolver.class),
                (uriString) -> TrustmarkDefinitionUri.withTransactionHelper(() -> TrustmarkDefinitionUri.findByUriHelper(uriString)),
                TrustmarkDefinitionUri::getXml,
                (uriString) -> synchronizeTrustmarkDefinitionUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}

