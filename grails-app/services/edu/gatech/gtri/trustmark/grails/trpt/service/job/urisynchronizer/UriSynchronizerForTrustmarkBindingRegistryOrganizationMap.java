package edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationMapUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmarkBindingRegistryOrganizationMap extends UriSynchronizer<TrustmarkBindingRegistryOrganizationMap, TrustmarkBindingRegistryOrganizationMapUri, TrustmarkBindingRegistryOrganizationMapUriHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistryOrganizationMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistryOrganizationMap();

    private UriSynchronizerForTrustmarkBindingRegistryOrganizationMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistryOrganizationMap.class),
                "Trustmark Binding Registry Organization Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationMapResolver.class),
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
                () -> TrustmarkBindingRegistryOrganizationMapUri.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationMapUri.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistryOrganizationMapUri.withTransactionHelper(() -> f.f(TrustmarkBindingRegistryOrganizationMapUri.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistryOrganizationMapUri(),
                TrustmarkBindingRegistryOrganizationMapUri::saveHelper);
    }
}
