package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriTypeHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmarkBindingRegistrySystemMap extends UriSynchronizer<TrustmarkBindingRegistrySystemMap, TrustmarkBindingRegistrySystemMapUriType, TrustmarkBindingRegistrySystemMapUriTypeHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistrySystemMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistrySystemMap();

    private UriSynchronizerForTrustmarkBindingRegistrySystemMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistrySystemMap.class),
                "Trustmark Binding Registry System Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistrySystemMapResolver.class),
                FactoryLoader.getInstance(HashFactory.class)::hash,
                trustmarkBindingRegistryOrganizationMap -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        SerializerJson serializerJson = new SerializerJson();
                        serializerJson.serialize(trustmarkBindingRegistryOrganizationMap, byteArrayOutputStream);
                        return byteArrayOutputStream.toByteArray();
                    } catch (final IOException ioException) {
                        return new byte[]{};
                    }
                },
                () -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> TrustmarkBindingRegistrySystemMapUriType.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistrySystemMapUriType.withTransactionHelper(() -> f.f(TrustmarkBindingRegistrySystemMapUriType.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistrySystemMapUriType(),
                (hasSource, uri) -> {
                    uri.setType(hasSource.getSystemMap().values().headOption().map(TrustmarkBindingRegistrySystem::getSystemType).toNull());
                },
                TrustmarkBindingRegistrySystemMapUriType::saveHelper);
    }
}
