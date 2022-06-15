package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.domain.Server;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.function.TryEffect;
import org.gtri.fj.product.P4;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility.retry;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.send;
import static java.lang.String.format;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.product.P.p;

public final class JobUtilityForMailPasswordReset {

    private static final Log log = LogFactory.getLog(JobUtilityForMailPasswordReset.class);

    private JobUtilityForMailPasswordReset() {
    }

    public static void synchronizeMailPasswordReset() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        somes(retry(() -> select(), log)
                .map(mailPasswordReset -> reduce(TryEffect.f(() -> send(mailPasswordReset._2(), mailPasswordReset._3(), mailPasswordReset._4()))._1().toEither().bimap(
                        exception -> {
                            log.error(exception.getMessage(), exception);
                            return none();
                        },
                        unit -> some(mailPasswordReset._1())))))
                .forEach(mailPasswordReset -> retry(() -> update(now, mailPasswordReset), log));
    }

    private static List<P4<MailPasswordReset, String, String, String>> select() {

        return MailPasswordReset.withTransactionHelper(() ->
                Server.findAllHelper().headOption().toList().bind(server -> MailPasswordReset.findAllByMailDateTimeIsNullHelper()
                        .map(mailPasswordReset ->
                                p(mailPasswordReset, mailPasswordReset.userHelper().getUsername(), subject(), body(server, mailPasswordReset)))));
    }

    private static MailPasswordReset update(
            final LocalDateTime now,
            final MailPasswordReset mailPasswordReset) {

        return MailPasswordReset.withTransactionHelper(() -> {
            mailPasswordReset.setMailDateTime(now);
            return mailPasswordReset.saveHelper();
        });
    }

    private static String subject() {

        return "The system has reset your password.";
    }

    private static String body(
            final Server server,
            final MailPasswordReset mailPasswordReset) {

        return format(
                "The system has reset your password.%n%n" +
                        "You may change your password here:%n%n" +
                        "%spassword/changeWithoutAuthentication?external=%s", server.getUrl(), mailPasswordReset.getExternal());
    }
}
