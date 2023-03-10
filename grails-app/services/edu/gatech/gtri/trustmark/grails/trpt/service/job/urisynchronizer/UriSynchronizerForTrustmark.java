package edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmark extends UriSynchronizer<Trustmark, TrustmarkUri, TrustmarkUriHistory> {

    public static final UriSynchronizerForTrustmark INSTANCE = new UriSynchronizerForTrustmark();

    private UriSynchronizerForTrustmark() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmark.class),
                "Trustmark",
                FactoryLoader.getInstance(TrustmarkResolver.class),
                FactoryLoader.getInstance(HashFactory.class)::hash,
                trustmark -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        SerializerXml serializerXml = new SerializerXml();
                        serializerXml.serialize(trustmark, byteArrayOutputStream);
                        return byteArrayOutputStream.toByteArray();
                    } catch (final IOException ioException) {
                        return new byte[]{};
                    }
                },
                () -> TrustmarkUri.withTransactionHelper(() -> TrustmarkUri.findAllHelper()),
                (uriString, f) -> TrustmarkUri.withTransactionHelper(() -> f.f(TrustmarkUri.findByUriHelper(uriString))),
                () -> new TrustmarkUri(),
                TrustmarkUri::saveHelper);
    }
}
