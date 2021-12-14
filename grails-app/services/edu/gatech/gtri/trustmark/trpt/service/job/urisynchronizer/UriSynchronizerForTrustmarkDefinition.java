package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUriHistory;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.logging.LogFactory;

public class UriSynchronizerForTrustmarkDefinition extends UriSynchronizer<TrustmarkDefinition, TrustmarkDefinitionUri, TrustmarkDefinitionUriHistory> {

    public static final UriSynchronizerForTrustmarkDefinition INSTANCE = new UriSynchronizerForTrustmarkDefinition();

    private UriSynchronizerForTrustmarkDefinition() {
        super(
                LogFactory.getLog(UriSynchronizerForTrustmarkDefinition.class),
                "Trustmark Definition",
                FactoryLoader.getInstance(TrustmarkDefinitionResolver.class)::resolve,
                FactoryLoader.getInstance(HashFactory.class)::hash,
                TrustmarkDefinitionUri::withTransactionHelper,
                () -> TrustmarkDefinitionUri.withTransactionHelper(() -> TrustmarkDefinitionUri.findAllHelper()),
                TrustmarkDefinitionUri::findByUriHelper,
                () -> new TrustmarkDefinitionUri(),
                TrustmarkDefinitionUri::saveHelper,
                () -> new TrustmarkDefinitionUriHistory(),
                TrustmarkDefinitionUriHistory::saveHelper,
                true);
    }
}
