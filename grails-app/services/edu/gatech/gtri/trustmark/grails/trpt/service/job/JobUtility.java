package edu.gatech.gtri.trustmark.grails.trpt.service.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JobUtility {

    private JobUtility() {
    }

    private static final Log log = LogFactory.getLog(JobUtility.class);

    public static String truncate(String string, int length) {

        return string == null ? null : string.substring(0, Math.min(string.length(), length));
    }
}
