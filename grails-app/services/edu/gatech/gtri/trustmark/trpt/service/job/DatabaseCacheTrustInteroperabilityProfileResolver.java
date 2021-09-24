package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustInteroperabilityProfileUri.synchronizeTrustInteroperabilityProfileUri;

public final class DatabaseCacheTrustInteroperabilityProfileResolver extends DatabaseCacheResolver<TrustInteroperabilityProfile, TrustInteroperabilityProfileUri> implements TrustInteroperabilityProfileResolver {

    public DatabaseCacheTrustInteroperabilityProfileResolver() {
        super(
                FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class),
                (uriString) -> TrustInteroperabilityProfileUri.withTransactionHelper(() -> TrustInteroperabilityProfileUri.findByUriHelper(uriString)),
                TrustInteroperabilityProfileUri::getJson,
                (uriString) -> synchronizeTrustInteroperabilityProfileUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
