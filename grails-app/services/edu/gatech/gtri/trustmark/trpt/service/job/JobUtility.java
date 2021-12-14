package edu.gatech.gtri.trustmark.trpt.service.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.lang.String.format;
import static org.gtri.fj.product.P.p;

public class JobUtility {

    private JobUtility() {
    }

    private static final Log log = LogFactory.getLog(JobUtility.class);

    public static String truncate(String string, int length) {

        return string == null ? null : string.substring(0, Math.min(string.length(), length));
    }
}
