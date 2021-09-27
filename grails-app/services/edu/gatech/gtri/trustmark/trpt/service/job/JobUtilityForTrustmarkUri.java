package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
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

public class JobUtilityForTrustmarkUri {

    private JobUtilityForTrustmarkUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForTrustmarkUri.class);
    private static final TrustmarkResolver trustmarkResolver = FactoryLoader.getInstance(TrustmarkResolver.class);
    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);

    public static void synchronizeTrustmarkUri() {

        // inside transaction
        //  retrieve trust interoperability profile uri
        // outside transaction
        //  retrieve trust interoperability profile
        //  calculate trust interoperability profile hash
        // inside transaction
        //  save trust interoperability profile uri information

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustmarkUri
                .withTransactionHelper(() -> TrustmarkUri.findAllHelper())
                .map(trustmarkUri -> trustmarkUri.getUri())
                .map(uriString -> synchronizeTrustmarkUri(now, uriString));
    }

    public static Validation<NonEmptyList<Exception>, Trustmark> synchronizeTrustmarkUri(final LocalDateTime now, final String uriString) {

        final Effect1<TrustmarkUri> historyEffect = trustmarkUri -> {

            final TrustmarkUriHistory trustmarkUriHistory = new TrustmarkUriHistory();
            trustmarkUriHistory.setUri(trustmarkUri.getUri());
            trustmarkUriHistory.setHash(trustmarkUri.getHash());
            trustmarkUriHistory.setXml(trustmarkUri.getXml());
            trustmarkUriHistory.setRequestLocalDateTime(trustmarkUri.getRequestLocalDateTime());
            trustmarkUriHistory.setSuccessLocalDateTime(trustmarkUri.getSuccessLocalDateTime());
            trustmarkUriHistory.setFailureLocalDateTime(trustmarkUri.getFailureLocalDateTime());
            trustmarkUriHistory.setChangeLocalDateTime(trustmarkUri.getChangeLocalDateTime());
            trustmarkUriHistory.setFailureMessage(trustmarkUri.getFailureMessage());
            trustmarkUriHistory.saveHelper();
        };

        final P2<String, Validation<NonEmptyList<Exception>, P3<URI, Trustmark, String>>> p = createResolveHash("Trustmark", uriString, trustmarkResolver::resolve, hashFactory::hash);

        TrustmarkUri.withTransactionHelper(() -> {
            final TrustmarkUri trustmarkUri = TrustmarkUri.findByUriHelper(p._1())
                    .orSome(() -> {
                        final TrustmarkUri trustmarkUriInner = new TrustmarkUri();
                        trustmarkUriInner.setUri(uriString);
                        return trustmarkUriInner;
                    });

            log.info(format("Trustmark uri '%s' time changed.", trustmarkUri.getUri()));

            reduce(p._2().toEither().bimap(
                    nel -> {

                        trustmarkUri.setRequestLocalDateTime(now);
                        trustmarkUri.setFailureLocalDateTime(now);
                        trustmarkUri.setFailureMessage(truncate(nel.head().getMessage(), 1000));

                        log.info(format("Trustmark uri '%s' relying on cache.", trustmarkUri.getUri()));

                        historyEffect.f(trustmarkUri);

                        return trustmarkUri;
                    },
                    pInner -> {

                        final Trustmark trustmark = pInner._2();
                        final String hash = pInner._3();

                        trustmarkUri.setRequestLocalDateTime(now);
                        trustmarkUri.setSuccessLocalDateTime(now);

                        if (trustmarkUri.getHash() == null || !trustmarkUri.getHash().equals(hash)) {

                            log.info(format("Trustmark uri '%s' content changed.", trustmarkUri.getUri()));

                            trustmarkUri.setHash(hash);
                            trustmarkUri.setXml(trustmark.getOriginalSource());
                            trustmarkUri.setChangeLocalDateTime(now);

                            historyEffect.f(trustmarkUri);

                        } else {

                            log.info(format("Trustmark uri '%s' content did not change.", trustmarkUri.getUri()));
                        }

                        return trustmarkUri;
                    })).saveHelper();
        });

        return p._2().map(p3 -> p3._2());
    }
}
