package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmark;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkResolver extends DatabaseCacheResolver<Trustmark> implements TrustmarkResolver {

    public DatabaseCacheTrustmarkResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                (uriString) -> TrustmarkUri.withTransactionHelper(() -> TrustmarkUri.findByUriHelper(uriString).map(TrustmarkUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmark.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}

