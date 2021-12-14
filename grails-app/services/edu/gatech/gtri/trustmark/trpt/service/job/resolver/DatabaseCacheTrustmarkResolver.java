package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmark;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.gtri.fj.data.Option.fromNull;

public final class DatabaseCacheTrustmarkResolver extends DatabaseCacheResolver<Trustmark, TrustmarkUri> implements TrustmarkResolver {

    public DatabaseCacheTrustmarkResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                (uriString) -> TrustmarkUri.withTransactionHelper(() -> TrustmarkUri.findByUriHelper(uriString)),
                uri -> fromNull(uri.getDocument()),
                (uriString) -> UriSynchronizerForTrustmark.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}

