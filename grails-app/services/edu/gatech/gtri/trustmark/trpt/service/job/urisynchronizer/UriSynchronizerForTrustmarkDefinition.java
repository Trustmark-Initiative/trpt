package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverImplWithoutTerms;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmarkDefinition extends UriSynchronizer<TrustmarkDefinition, TrustmarkDefinitionUri, TrustmarkDefinitionUriHistory> {

    public static final UriSynchronizerForTrustmarkDefinition INSTANCE = new UriSynchronizerForTrustmarkDefinition();

    private UriSynchronizerForTrustmarkDefinition() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkDefinition.class),
                "Trustmark Definition",
                new TrustmarkDefinitionResolverImplWithoutTerms(),
                FactoryLoader.getInstance(HashFactory.class)::hash,
                trustmarkDefinition -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        SerializerXml serializerXml = new SerializerXml();
                        serializerXml.serialize(trustmarkDefinition, byteArrayOutputStream);
                        return byteArrayOutputStream.toByteArray();
                    } catch (final IOException ioException) {
                        return new byte[]{};
                    }
                },
                () -> TrustmarkDefinitionUri.withTransactionHelper(() -> TrustmarkDefinitionUri.findAllHelper()),
                (uriString, f) -> TrustmarkDefinitionUri.withTransactionHelper(() -> f.f(TrustmarkDefinitionUri.findByUriHelper(uriString))),
                () -> new TrustmarkDefinitionUri(),
                TrustmarkDefinitionUri::saveHelper);
    }
}
