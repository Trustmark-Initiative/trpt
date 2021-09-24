package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Effect1;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.createResolveHash;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;

public class JobUtilityForTrustInteroperabilityProfileUri {

    private JobUtilityForTrustInteroperabilityProfileUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForTrustInteroperabilityProfileUri.class);
    private static final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);

    public static void synchronizeTrustInteroperabilityProfileUri() {

        // inside transaction
        //  retrieve trust interoperability profile uri
        // outside transaction
        //  retrieve trust interoperability profile
        //  calculate trust interoperability profile hash
        // inside transaction
        //  save trust interoperability profile uri information

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustInteroperabilityProfileUri
                .withTransactionHelper(() -> TrustInteroperabilityProfileUri.findAllHelper())
                .map(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri.getUri())
                .forEach(uriString -> synchronizeTrustInteroperabilityProfileUri(now, uriString));
    }

    public static Validation<NonEmptyList<Exception>, TrustInteroperabilityProfile> synchronizeTrustInteroperabilityProfileUri(final LocalDateTime now, final String uriString) {

        final Effect1<TrustInteroperabilityProfileUri> historyEffect = trustInteroperabilityProfileUri -> {

            final TrustInteroperabilityProfileUriHistory trustInteroperabilityProfileUriHistory = new TrustInteroperabilityProfileUriHistory();
            trustInteroperabilityProfileUriHistory.setUri(trustInteroperabilityProfileUri.getUri());
            trustInteroperabilityProfileUriHistory.setName(trustInteroperabilityProfileUri.getName());
            trustInteroperabilityProfileUriHistory.setDescription(trustInteroperabilityProfileUri.getDescription());
            trustInteroperabilityProfileUriHistory.setPublicationLocalDateTime(trustInteroperabilityProfileUri.getPublicationLocalDateTime());
            trustInteroperabilityProfileUriHistory.setIssuerName(trustInteroperabilityProfileUri.getIssuerName());
            trustInteroperabilityProfileUriHistory.setIssuerIdentifier(trustInteroperabilityProfileUri.getIssuerIdentifier());
            trustInteroperabilityProfileUriHistory.setHash(trustInteroperabilityProfileUri.getHash());
            trustInteroperabilityProfileUriHistory.setJson(trustInteroperabilityProfileUri.getJson());
            trustInteroperabilityProfileUriHistory.setRequestLocalDateTime(trustInteroperabilityProfileUri.getRequestLocalDateTime());
            trustInteroperabilityProfileUriHistory.setSuccessLocalDateTime(trustInteroperabilityProfileUri.getSuccessLocalDateTime());
            trustInteroperabilityProfileUriHistory.setFailureLocalDateTime(trustInteroperabilityProfileUri.getFailureLocalDateTime());
            trustInteroperabilityProfileUriHistory.setChangeLocalDateTime(trustInteroperabilityProfileUri.getChangeLocalDateTime());
            trustInteroperabilityProfileUriHistory.setFailureMessage(trustInteroperabilityProfileUri.getFailureMessage());
            trustInteroperabilityProfileUriHistory.saveHelper();
        };

        final P2<String, Validation<NonEmptyList<Exception>, P3<URI, TrustInteroperabilityProfile, String>>> p = createResolveHash("Trust interoperability profile", uriString, trustInteroperabilityProfileResolver::resolve, hashFactory::hash);

        TrustInteroperabilityProfileUri.withTransactionHelper(() -> {
            final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = TrustInteroperabilityProfileUri.findByUriHelper(p._1())
                    .orSome(() -> {
                        final TrustInteroperabilityProfileUri trustInteroperabilityProfileUriInner = new TrustInteroperabilityProfileUri();
                        trustInteroperabilityProfileUriInner.setUri(uriString);
                        return trustInteroperabilityProfileUriInner;
                    });

            log.info(format("Trust interoperability profile uri '%s' time changed.", trustInteroperabilityProfileUri.getUri()));

            reduce(p._2().toEither().bimap(
                    nel -> {

                        trustInteroperabilityProfileUri.setRequestLocalDateTime(now);
                        trustInteroperabilityProfileUri.setFailureLocalDateTime(now);
                        trustInteroperabilityProfileUri.setFailureMessage(truncate(nel.head().getMessage(), 1000));

                        log.info(format("Trust interoperability profile uri '%s' relying on cache.", trustInteroperabilityProfileUri.getUri()));

                        historyEffect.f(trustInteroperabilityProfileUri);

                        return trustInteroperabilityProfileUri;
                    },
                    pInner -> {

                        final TrustInteroperabilityProfile trustInteroperabilityProfile = pInner._2();
                        final String hash = pInner._3();

                        trustInteroperabilityProfileUri.setRequestLocalDateTime(now);
                        trustInteroperabilityProfileUri.setSuccessLocalDateTime(now);

                        if (trustInteroperabilityProfileUri.getHash() == null || !trustInteroperabilityProfileUri.getHash().equals(hash)) {

                            log.info(format("Trust interoperability profile uri '%s' content changed.", trustInteroperabilityProfileUri.getUri()));

                            trustInteroperabilityProfileUri.setJson(trustInteroperabilityProfile.getOriginalSource());
                            trustInteroperabilityProfileUri.setName(truncate(trustInteroperabilityProfile.getName(), 1000));
                            trustInteroperabilityProfileUri.setDescription(truncate(trustInteroperabilityProfile.getDescription(), 1000));
                            trustInteroperabilityProfileUri.setPublicationLocalDateTime(trustInteroperabilityProfile.getPublicationDateTime().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                            trustInteroperabilityProfileUri.setIssuerName(truncate(trustInteroperabilityProfile.getIssuer().getName(), 1000));
                            trustInteroperabilityProfileUri.setIssuerIdentifier(truncate(trustInteroperabilityProfile.getIssuer().getIdentifier().toString(), 1000));
                            trustInteroperabilityProfileUri.setHash(hash);
                            trustInteroperabilityProfileUri.setChangeLocalDateTime(now);

                            historyEffect.f(trustInteroperabilityProfileUri);

                        } else {

                            log.info(format("Trust interoperability profile uri '%s' content did not change.", trustInteroperabilityProfileUri.getUri()));
                        }

                        return trustInteroperabilityProfileUri;
                    })).saveHelper();
        });

        return p._2().map(p3 -> p3._2());
    }
}
