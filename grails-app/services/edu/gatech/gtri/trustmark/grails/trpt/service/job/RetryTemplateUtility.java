package edu.gatech.gtri.trustmark.grails.trpt.service.job;

import org.apache.commons.logging.Log;
import org.gtri.fj.function.Effect0;
import org.gtri.fj.function.F0;
import org.springframework.retry.support.RetryTemplate;

import static java.lang.String.format;
import static org.gtri.fj.product.Unit.unit;

public class RetryTemplateUtility {

    private RetryTemplateUtility() {
    }

    private static final RetryTemplate retryTemplate = RetryTemplate.builder()
            .retryOn(Exception.class)
            .maxAttempts(10)
            .fixedBackoff(100)
            .build();

    public static final <T1> T1 retry(
            final F0<T1> f,
            final Log log) {

        return retryTemplate.execute(context -> {

            if (context.getRetryCount() > 0) {
                log.info(format("Retry %s ...", context.getRetryCount()));
            }

            return f.f();
        });
    }

    public static final void retry(
            final Effect0 f,
            final Log log) {

        retry(() -> {
            f.f();
            return unit();
        }, log);
    }
}
