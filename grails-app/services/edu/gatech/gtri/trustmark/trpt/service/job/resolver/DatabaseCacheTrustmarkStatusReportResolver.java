package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.trpt.domain.File;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.trpt.service.file.FileUtility;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.gtri.fj.data.Option.fromNull;

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

