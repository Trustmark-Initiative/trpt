package edu.gatech.gtri.trustmark.trpt.service.password;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.plugin.springsecurity.SpringSecurityService;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.crypto.password.PasswordEncoder;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndReference;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.lang.StringUtility.stringEqual;
import static org.gtri.fj.product.P.p;

public final class PasswordUtility {

    private PasswordUtility() {
    }

    public static Validation<NonEmptyList<ValidationMessage<PasswordField>>, User> validationUsername(
            final String username) {

        return mustBeNonNullAndReference(PasswordField.username, User::findByUsernameHelper, username);
    }

    public static Validation<NonEmptyList<ValidationMessage<PasswordField>>, MailPasswordReset> validationExternal(
            final String external) {

        return mustBeNonNull(PasswordField.external, external)
                .bind(nonNull -> mustBeReference(
                        PasswordField.external,
                        externalInner -> MailPasswordReset.findByExternalHelper(externalInner)
                                .filter(mailPasswordReset -> mailPasswordReset.getExpireDateTime() == null),
                        external));
    }

    public static Validation<NonEmptyList<ValidationMessage<PasswordField>>, User> validationOldPassword(
            final SpringSecurityService springSecurityService,
            final String username,
            final String password) {

        return accumulate(
                mustBeNonNull(PasswordField.passwordOld, password),
                mustBeNonNullAndReference(PasswordField.username, User::findByUsernameHelper, username),
                (passwordInner, user) -> p(passwordInner, user))
                .bind(p -> mustBeEqual(
                        PasswordField.passwordOld,
                        PasswordField.username,
                        equal(((PasswordEncoder) springSecurityService.getPasswordEncoder())::matches),
                        p._1(),
                        p._2().getPassword())
                        .map(passwordInner -> p._2()));
    }

    public static Validation<NonEmptyList<ValidationMessage<PasswordField>>, String> validationNewPassword(
            final String password1,
            final String password2) {

        return accumulate(
                mustBeNonNullAndLength(PasswordField.passwordNew1, 1, 1000, password1),
                mustBeNonNullAndLength(PasswordField.passwordNew2, 1, 1000, password2),
                (password1Inner, password2Inner) -> p(password1Inner, password2Inner))
                .bind(p -> mustBeEqual(PasswordField.passwordNew1, PasswordField.passwordNew2, stringEqual, p._1(), p._2()));
    }
}
