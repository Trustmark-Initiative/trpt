package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
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

public class JobUtilityForTrustmarkDefinitionUri {

    private JobUtilityForTrustmarkDefinitionUri() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForTrustmarkDefinitionUri.class);
    private static final TrustmarkDefinitionResolver trustmarkDefinitionResolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
    private static final HashFactory hashFactory = FactoryLoader.getInstance(HashFactory.class);

    public static void synchronizeTrustmarkDefinitionUri() {

        // inside transaction
        //  retrieve trust interoperability profile uri
        // outside transaction
        //  retrieve trust interoperability profile
        //  calculate trust interoperability profile hash
        // inside transaction
        //  save trust interoperability profile uri information

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        TrustmarkDefinitionUri
                .withTransactionHelper(() -> TrustmarkDefinitionUri.findAllHelper())
                .map(trustmarkDefinitionUri -> trustmarkDefinitionUri.getUri())
                .map(uriString -> synchronizeTrustmarkDefinitionUri(now, uriString));
    }

    public static Validation<NonEmptyList<Exception>, TrustmarkDefinition> synchronizeTrustmarkDefinitionUri(final LocalDateTime now, final String uriString) {

        final Effect1<TrustmarkDefinitionUri> historyEffect = trustmarkDefinitionUri -> {

            final TrustmarkDefinitionUriHistory trustmarkDefinitionUriHistory = new TrustmarkDefinitionUriHistory();
            trustmarkDefinitionUriHistory.setUri(trustmarkDefinitionUri.getUri());
            trustmarkDefinitionUriHistory.setHash(trustmarkDefinitionUri.getHash());
            trustmarkDefinitionUriHistory.setXml(trustmarkDefinitionUri.getXml());
            trustmarkDefinitionUriHistory.setRequestLocalDateTime(trustmarkDefinitionUri.getRequestLocalDateTime());
            trustmarkDefinitionUriHistory.setSuccessLocalDateTime(trustmarkDefinitionUri.getSuccessLocalDateTime());
            trustmarkDefinitionUriHistory.setFailureLocalDateTime(trustmarkDefinitionUri.getFailureLocalDateTime());
            trustmarkDefinitionUriHistory.setChangeLocalDateTime(trustmarkDefinitionUri.getChangeLocalDateTime());
            trustmarkDefinitionUriHistory.setFailureMessage(trustmarkDefinitionUri.getFailureMessage());
            trustmarkDefinitionUriHistory.saveHelper();
        };

        final P2<String, Validation<NonEmptyList<Exception>, P3<URI, TrustmarkDefinition, String>>> p = createResolveHash("Trustmark definition", uriString, trustmarkDefinitionResolver::resolve, hashFactory::hash);

        TrustmarkDefinitionUri.withTransactionHelper(() -> {
            final TrustmarkDefinitionUri trustmarkDefinitionUri = TrustmarkDefinitionUri.findByUriHelper(p._1())
                    .orSome(() -> {
                        final TrustmarkDefinitionUri trustmarkDefinitionUriInner = new TrustmarkDefinitionUri();
                        trustmarkDefinitionUriInner.setUri(uriString);
                        return trustmarkDefinitionUriInner;
                    });

            log.info(format("TrustmarkDefinition uri '%s' time changed.", trustmarkDefinitionUri.getUri()));

            reduce(p._2().toEither().bimap(
                    nel -> {

                        trustmarkDefinitionUri.setRequestLocalDateTime(now);
                        trustmarkDefinitionUri.setFailureLocalDateTime(now);
                        trustmarkDefinitionUri.setFailureMessage(truncate(nel.head().getMessage(), 1000));

                        log.info(format("TrustmarkDefinition uri '%s' relying on cache.", trustmarkDefinitionUri.getUri()));

                        historyEffect.f(trustmarkDefinitionUri);

                        return trustmarkDefinitionUri;
                    },
                    pInner -> {

                        final TrustmarkDefinition trustmarkDefinition = pInner._2();
                        final String hash = pInner._3();

                        trustmarkDefinitionUri.setRequestLocalDateTime(now);
                        trustmarkDefinitionUri.setSuccessLocalDateTime(now);

                        if (trustmarkDefinitionUri.getHash() == null || !trustmarkDefinitionUri.getHash().equals(hash)) {

                            log.info(format("TrustmarkDefinition uri '%s' content changed.", trustmarkDefinitionUri.getUri()));

                            trustmarkDefinitionUri.setHash(hash);
                            trustmarkDefinitionUri.setXml(trustmarkDefinition.getOriginalSource());
                            trustmarkDefinitionUri.setChangeLocalDateTime(now);

                            historyEffect.f(trustmarkDefinitionUri);

                        } else {

                            log.info(format("TrustmarkDefinition uri '%s' content did not change.", trustmarkDefinitionUri.getUri()));
                        }

                        return trustmarkDefinitionUri;
                    })).saveHelper();
        });

        return p._2().map(p3 -> p3._2());
    }
}
