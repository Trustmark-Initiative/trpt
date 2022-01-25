package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.logging.LogFactory;

import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;

public class UriSynchronizerForTrustInteroperabilityProfile extends UriSynchronizer<TrustInteroperabilityProfile, TrustInteroperabilityProfileUri, TrustInteroperabilityProfileUriHistory> {

    public static final UriSynchronizerForTrustInteroperabilityProfile INSTANCE = new UriSynchronizerForTrustInteroperabilityProfile();

    private UriSynchronizerForTrustInteroperabilityProfile() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustInteroperabilityProfile.class),
                "Trust Interoperability Profile",
                FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                () -> TrustInteroperabilityProfileUri.withTransactionHelper(() -> TrustInteroperabilityProfileUri.findAllHelper()),
                (uriString, f) -> TrustInteroperabilityProfileUri.withTransactionHelper(() -> f.f(TrustInteroperabilityProfileUri.findByUriHelper(uriString))),
                () -> new TrustInteroperabilityProfileUri(),
                (hasSource, uri) -> {
                    uri.setName(truncate(hasSource.getName(), 1000));
                    uri.setDescription(truncate(hasSource.getDescription(), 1000));
                    uri.setPublicationLocalDateTime(hasSource.getPublicationDateTime().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                    uri.setIssuerName(truncate(hasSource.getIssuer().getName(), 1000));
                    uri.setIssuerIdentifier(truncate(hasSource.getIssuer().getIdentifier().toString(), 1000));
                },
                TrustInteroperabilityProfileUri::saveHelper,
                () -> new TrustInteroperabilityProfileUriHistory(),
                (uri, uriHistory) -> {
                    uriHistory.setName(truncate(uri.getName(), 1000));
                    uriHistory.setDescription(truncate(uri.getDescription(), 1000));
                    uriHistory.setPublicationLocalDateTime(uri.getPublicationLocalDateTime());
                    uriHistory.setIssuerName(truncate(uri.getIssuerName(), 1000));
                    uriHistory.setIssuerIdentifier(truncate(uri.getIssuerIdentifier(), 1000));
                },
                TrustInteroperabilityProfileUriHistory::saveHelper);
    }
}
