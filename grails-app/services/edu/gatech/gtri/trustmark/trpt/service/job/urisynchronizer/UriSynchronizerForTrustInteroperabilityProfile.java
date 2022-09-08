package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverImplWithoutTerms;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;

public class UriSynchronizerForTrustInteroperabilityProfile extends UriSynchronizer<TrustInteroperabilityProfile, TrustInteroperabilityProfileUri, TrustInteroperabilityProfileUriHistory> {

    public static final UriSynchronizerForTrustInteroperabilityProfile INSTANCE = new UriSynchronizerForTrustInteroperabilityProfile();

    private UriSynchronizerForTrustInteroperabilityProfile() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustInteroperabilityProfile.class),
                "Trust Interoperability Profile",
                new TrustInteroperabilityProfileResolverImplWithoutTerms(),
                FactoryLoader.getInstance(HashFactory.class)::hash,
                trustInteroperabilityProfile -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        SerializerXml serializerXml = new SerializerXml();
                        serializerXml.serialize(trustInteroperabilityProfile, byteArrayOutputStream);
                        return byteArrayOutputStream.toByteArray();
                    } catch (final IOException ioException) {
                        return new byte[]{};
                    }
                },
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
                TrustInteroperabilityProfileUri::saveHelper);
    }
}
