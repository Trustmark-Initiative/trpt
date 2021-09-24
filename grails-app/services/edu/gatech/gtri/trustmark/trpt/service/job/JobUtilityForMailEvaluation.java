package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;

public class JobUtilityForMailEvaluation {

    private JobUtilityForMailEvaluation() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForMailEvaluation.class);

    public static void synchronizeMailEvaluation() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    }
}


