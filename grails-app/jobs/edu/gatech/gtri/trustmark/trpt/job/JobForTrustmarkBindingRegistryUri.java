package edu.gatech.gtri.trustmark.trpt.job;

import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkBindingRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidate.synchronizePartnerSystemCandidate;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustmarkBindingRegistry.synchronizeTrustmarkBindingRegistryUri;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustmarkBindingRegistry.synchronizeTrustmarkBindingRegistryUriAndDependencies;
import static java.lang.String.format;

@DisallowConcurrentExecution
public class JobForTrustmarkBindingRegistryUri implements Job {

    private static final Log log = LogFactory.getLog(JobForTrustmarkBindingRegistryUri.class);

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        log.info(format("%s: previous at %s; next at %s.", getClass().getSimpleName(), context.getPreviousFireTime(), context.getNextFireTime()));

        synchronizeTrustmarkBindingRegistryUriAndDependencies();
    }
}
