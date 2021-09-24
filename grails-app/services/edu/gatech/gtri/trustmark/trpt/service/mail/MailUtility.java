package edu.gatech.gtri.trustmark.trpt.service.mail;

import edu.gatech.gtri.trustmark.trpt.domain.Mail;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import edu.gatech.gtri.trustmark.v1_0.impl.util.TrustmarkMailClientImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndNumeric;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static java.lang.String.format;

public class MailUtility {

    public static MailResponse mailResponse(final Mail mail) {
        return new MailResponse(
                mail.getHost(),
                mail.getPort(),
                mail.getUsername(),
                mail.getPassword(),
                mail.getAuthor());
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, String> validationUsername(
            final String username) {

        return mustBeNullOr(MailField.username, username, (field, value) -> mustBeLength(field, 1, 1000, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, String> validationPassword(
            final String password) {

        return mustBeNullOr(MailField.password, password, (field, value) -> mustBeLength(field, 1, 1000, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, String> validationHost(
            final String host) {

        return mustBeNonNullAndLength(MailField.host, 1, 1000, host);
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, Long> validationPort(
            final String port) {

        return mustBeNonNullAndNumeric(MailField.port, port);
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, String> validationAuthor(
            final String author) {

        return mustBeNonNullAndLength(MailField.author, 1, 1000, author);
    }

    public static Validation<NonEmptyList<ValidationMessage<MailField>>, String> validationRecipient(
            final String recipient) {

        return mustBeNonNullAndLength(MailField.recipient, 1, 1000, recipient);
    }

    private static final Log log = LogFactory.getLog(MailUtility.class);

    public static void send(final String recipient, final String subject, final String body) {

        final Mail mail = Mail.findAllHelper().head();

        if ((mail.getUsername() == null || mail.getUsername().trim().isEmpty()) && (mail.getPassword() == null || mail.getPassword().trim().isEmpty())) {

            log.info(format("Mail Settings: '%s', '%s'", mail.getHost(), mail.getPort()));
            log.info(format("From: %s%nTo: %s%nSubject: %s%n%n%s", mail.getAuthor(), recipient, subject, body));

            new TrustmarkMailClientImpl()
                    .setSmtpHost(mail.getHost())
                    .setSmtpPort(String.valueOf(mail.getPort()))
                    .setFromAddress(mail.getAuthor())
                    .setSmtpAuthorization(false)
                    .setSmtpTimeout(1000)
                    .setSubject(subject)
                    .addRecipient(recipient)
                    .setText(body)
                    .sendMail();

        } else {

            log.info(format("Mail Settings: '%s', '%s', '%s'", mail.getHost(), mail.getPort(), mail.getUsername()));
            log.info(format("From: %s%nTo: %s%nSubject: %s%n%n%s", mail.getAuthor(), recipient, subject, body));

            new TrustmarkMailClientImpl()
                    .setUser(mail.getUsername())
                    .setPswd(mail.getPassword())
                    .setSmtpHost(mail.getHost())
                    .setSmtpPort(String.valueOf(mail.getPort()))
                    .setFromAddress(mail.getAuthor())
                    .setSmtpAuthorization(true)
                    .setSmtpTimeout(1000)
                    .setSubject(subject)
                    .addRecipient(recipient)
                    .setText(body)
                    .sendMail();
        }
    }
}
