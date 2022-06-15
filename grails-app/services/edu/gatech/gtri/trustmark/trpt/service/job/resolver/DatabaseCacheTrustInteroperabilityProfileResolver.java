package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverImplWithoutTerms;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustInteroperabilityProfileResolver extends DatabaseCacheResolver<TrustInteroperabilityProfile> implements TrustInteroperabilityProfileResolver {

    public DatabaseCacheTrustInteroperabilityProfileResolver() {
        super(
                new TrustInteroperabilityProfileResolverImplWithoutTerms(),
                (uriString) -> TrustInteroperabilityProfileUri.withTransactionHelper(() -> TrustInteroperabilityProfileUri.findByUriHelper(uriString).map(TrustInteroperabilityProfileUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }
}
