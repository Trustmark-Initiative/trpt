package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkStatusReportUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmarkStatusReport extends UriSynchronizer<TrustmarkStatusReport, TrustmarkStatusReportUri, TrustmarkStatusReportUriHistory> {

    public static final UriSynchronizerForTrustmarkStatusReport INSTANCE = new UriSynchronizerForTrustmarkStatusReport();

    private UriSynchronizerForTrustmarkStatusReport() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkStatusReport.class),
                "Trustmark Status Report",
                FactoryLoader.getInstance(TrustmarkStatusReportResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                TrustmarkStatusReportUri::withTransactionHelper,
                () -> TrustmarkStatusReportUri.withTransactionHelper(() -> TrustmarkStatusReportUri.findAllHelper()),
                TrustmarkStatusReportUri::findByUriHelper,
                () -> new TrustmarkStatusReportUri(),
                TrustmarkStatusReportUri::saveHelper,
                () -> new TrustmarkStatusReportUriHistory(),
                TrustmarkStatusReportUriHistory::saveHelper,
                false);
    }
}
