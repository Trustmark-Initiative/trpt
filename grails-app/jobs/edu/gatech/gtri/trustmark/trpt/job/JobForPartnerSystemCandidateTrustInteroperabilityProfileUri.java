package edu.gatech.gtri.trustmark.trpt.job;

import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Duration;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerOrganizationCandidateTrustInteroperabilityProfileUri.synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri;
import static java.lang.String.format;
import static org.gtri.fj.data.List.nil;

@DisallowConcurrentExecution
public class JobForPartnerSystemCandidateTrustInteroperabilityProfileUri implements Job {

    private static final Log log = LogFactory.getLog(JobForPartnerSystemCandidateTrustInteroperabilityProfileUri.class);

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        final Duration duration = Duration.parse(context.getJobDetail().getJobDataMap().get(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum).toString());

        log.info(format("%s: previous at %s; next at %s; duration %s", getClass().getSimpleName(), context.getPreviousFireTime(), context.getNextFireTime(), duration));

        synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(duration, nil(), nil(), nil());
        synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(duration, nil(), nil(), nil());
    }
}
