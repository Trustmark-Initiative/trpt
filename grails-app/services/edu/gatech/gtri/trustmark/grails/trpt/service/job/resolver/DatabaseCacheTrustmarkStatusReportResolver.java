package edu.gatech.gtri.trustmark.grails.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkStatusReport;
import edu.gatech.gtri.trustmark.grails.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DatabaseCacheTrustmarkStatusReportResolver extends DatabaseCacheResolver<TrustmarkStatusReport> implements TrustmarkStatusReportResolver {

    public DatabaseCacheTrustmarkStatusReportResolver() {
        super(
                FactoryLoader.getInstance(TrustmarkStatusReportResolver.class),
                (uriString) -> TrustmarkStatusReportUri.withTransactionHelper(() -> TrustmarkStatusReportUri.findByUriHelper(uriString).map(TrustmarkStatusReportUri::fileHelper).map(FileUtility::stringFor)),
                (uriString) -> UriSynchronizerForTrustmarkStatusReport.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uriString));
    }

    @Override
    public TrustmarkStatusReport resolve(final Trustmark trustmark) throws ResolveException {
        return resolve(trustmark.getStatusURL());
    }
}

