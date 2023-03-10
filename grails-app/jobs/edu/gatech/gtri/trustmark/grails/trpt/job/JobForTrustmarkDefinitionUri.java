package edu.gatech.gtri.trustmark.grails.trpt.job;

import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustmarkDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static java.lang.String.format;

@DisallowConcurrentExecution
public class JobForTrustmarkDefinitionUri implements Job {

    private static final Log log = LogFactory.getLog(JobForTrustmarkDefinitionUri.class);

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        log.info(format("%s: previous at %s; next at %s.", getClass().getSimpleName(), context.getPreviousFireTime(), context.getNextFireTime()));

        UriSynchronizerForTrustmarkDefinition.INSTANCE.synchronizeUri();
    }
}
