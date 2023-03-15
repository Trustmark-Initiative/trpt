package edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap extends UriSynchronizer<TrustmarkBindingRegistryOrganizationTrustmarkMap, TrustmarkBindingRegistryOrganizationTrustmarkMapUri, TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> {

    public static final UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap INSTANCE = new UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap();

    private UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkBindingRegistryOrganizationTrustmarkMap.class),
                "Trustmark Binding Registry Organization Trustmark Map",
                FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationTrustmarkMapResolver.class),
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
                () -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.withTransactionHelper(() -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findAllHelper()),
                (uriString, f) -> TrustmarkBindingRegistryOrganizationTrustmarkMapUri.withTransactionHelper(() -> f.f(TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findByUriHelper(uriString))),
                () -> new TrustmarkBindingRegistryOrganizationTrustmarkMapUri(),
                TrustmarkBindingRegistryOrganizationTrustmarkMapUri::saveHelper);
    }
}
