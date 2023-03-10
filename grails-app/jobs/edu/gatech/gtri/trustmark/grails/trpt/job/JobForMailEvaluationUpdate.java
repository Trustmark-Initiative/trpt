package edu.gatech.gtri.trustmark.grails.trpt.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static edu.gatech.gtri.trustmark.grails.trpt.service.job.JobUtilityForMailEvaluationUpdate.synchronizeMailEvaluationUpdate;
import static java.lang.String.format;

@DisallowConcurrentExecution
public class JobForMailEvaluationUpdate implements Job {

    private static final Log log = LogFactory.getLog(JobForMailEvaluationUpdate.class);

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        log.info(format("%s: previous at %s; next at %s.", getClass().getSimpleName(), context.getPreviousFireTime(), context.getNextFireTime()));

        synchronizeMailEvaluationUpdate();
    }
}
