package edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkStatusReportUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UriSynchronizerForTrustmarkStatusReport extends UriSynchronizer<TrustmarkStatusReport, TrustmarkStatusReportUri, TrustmarkStatusReportUriHistory> {

    public static final UriSynchronizerForTrustmarkStatusReport INSTANCE = new UriSynchronizerForTrustmarkStatusReport();

    private UriSynchronizerForTrustmarkStatusReport() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkStatusReport.class),
                "Trustmark Status Report",
                FactoryLoader.getInstance(TrustmarkStatusReportResolver.class),
                FactoryLoader.getInstance(HashFactory.class)::hash,
                trustmarkStatusReport -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        SerializerXml serializerXml = new SerializerXml();
                        serializerXml.serialize(trustmarkStatusReport, byteArrayOutputStream);
                        return byteArrayOutputStream.toByteArray();
                    } catch (final IOException ioException) {
                        return new byte[]{};
                    }
                },
                () -> TrustmarkStatusReportUri.withTransactionHelper(() -> TrustmarkStatusReportUri.findAllHelper()),
                (uriString, f) -> TrustmarkStatusReportUri.withTransactionHelper(() -> f.f(TrustmarkStatusReportUri.findByUriHelper(uriString))),
                () -> new TrustmarkStatusReportUri(),
                TrustmarkStatusReportUri::saveHelper);
    }
}
