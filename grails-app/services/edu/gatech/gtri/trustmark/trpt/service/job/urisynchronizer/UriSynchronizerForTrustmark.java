package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmark extends UriSynchronizer<Trustmark, TrustmarkUri, TrustmarkUriHistory> {

    public static final UriSynchronizerForTrustmark INSTANCE = new UriSynchronizerForTrustmark();

    private UriSynchronizerForTrustmark() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmark.class),
                "Trustmark",
                FactoryLoader.getInstance(TrustmarkResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                TrustmarkUri::withTransactionHelper,
                () -> TrustmarkUri.withTransactionHelper(() -> TrustmarkUri.findAllHelper()),
                TrustmarkUri::findByUriHelper,
                () -> new TrustmarkUri(),
                TrustmarkUri::saveHelper,
                () -> new TrustmarkUriHistory(),
                TrustmarkUriHistory::saveHelper,
                true);
    }
}
