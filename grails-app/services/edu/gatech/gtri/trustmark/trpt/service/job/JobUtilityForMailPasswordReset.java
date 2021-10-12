package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.domain.Server;
import edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;
import static org.gtri.fj.data.NonEmptyList.nel;

public class JobUtilityForMailPasswordReset {

    private JobUtilityForMailPasswordReset() {
    }

    public static void synchronizeMailPasswordReset() {

        MailPasswordReset.withTransactionHelper(() -> MailPasswordReset.findAllByMailDateTimeIsNullHelper()
                .forEach(mailPasswordReset -> {
                    MailUtility.send(
                            nel(mailPasswordReset.userHelper().getUsername()),
                            "The system has reset your password.",
                            format(
                                    "The system has reset your password.%n%n" +
                                            "You may change your password here:%n%n" +
                                            "%spassword/changeWithoutAuthentication?external=%s", Server.findAllHelper().head().getUrl(), mailPasswordReset.getExternal()));

                    mailPasswordReset.setMailDateTime(LocalDateTime.now(ZoneOffset.UTC));
                    mailPasswordReset.saveHelper();
                }));
    }
}
