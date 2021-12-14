package edu.gatech.gtri.trustmark.trpt.service.password;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.plugin.springsecurity.SpringSecurityService;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndPattern;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndReference;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
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
            final Pattern passwordPattern,
            final String passwordPatternDescription,
            final String password1,
            final String password2) {

        return accumulate(
                mustBeNullOr(PasswordField.passwordNew1, password1, (field, value) -> mustBeNonNullAndPattern(field, passwordPattern, passwordPatternDescription, value)),
                mustBeNonNullAndEqual(PasswordField.passwordNew2, PasswordField.passwordNew1, stringEqual, password2, password1),
                (password1Inner, password2Inner) -> password1Inner);
    }
}
