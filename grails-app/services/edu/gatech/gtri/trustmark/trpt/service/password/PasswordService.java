package edu.gatech.gtri.trustmark.trpt.service.password;

import edu.gatech.gtri.trustmark.trpt.domain.MailPasswordReset;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.services.Service;
import grails.gorm.transactions.Transactional;
import grails.plugin.springsecurity.SpringSecurityService;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static edu.gatech.gtri.trustmark.trpt.service.password.PasswordUtility.validationExternal;
import static edu.gatech.gtri.trustmark.trpt.service.password.PasswordUtility.validationNewPassword;
import static edu.gatech.gtri.trustmark.trpt.service.password.PasswordUtility.validationOldPassword;
import static edu.gatech.gtri.trustmark.trpt.service.password.PasswordUtility.validationUsername;
import static org.gtri.fj.data.Validation.accumulate;

@Transactional
@Service
public class PasswordService {

    @Autowired
    private SpringSecurityService springSecurityService;

    public Validation<NonEmptyList<ValidationMessage<PasswordField>>, PasswordResetResponse> resetSubmit(
            final PasswordResetRequest passwordResetRequest) {

        return validationUsername(passwordResetRequest.getUsername())
                .map(user -> {

                    final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

                    MailPasswordReset.findAllByUserHelper(user).forEach(mailPasswordReset -> {
                        mailPasswordReset.setExpireDateTime(now);
                        mailPasswordReset.saveHelper();
                    });

                    final MailPasswordReset mailPasswordReset = new MailPasswordReset();
                    mailPasswordReset.setExternal(UUID.randomUUID().toString());
                    mailPasswordReset.setRequestDateTime(now);
                    mailPasswordReset.userHelper(user);
                    mailPasswordReset.saveAndFlushHelper();

                    return mailPasswordReset;
                })
                .map(ignore -> new PasswordResetResponse());
    }

    public Validation<NonEmptyList<ValidationMessage<PasswordField>>, PasswordResetStatusResponse> resetStatusSubmit(
            final PasswordResetStatusRequest passwordResetStatusRequest) {

        return validationExternal(passwordResetStatusRequest.getExternal())
                .map(ignore -> new PasswordResetStatusResponse());
    }

    public Validation<NonEmptyList<ValidationMessage<PasswordField>>, PasswordChangeResponseWithoutAuthentication> changeWithoutAuthenticationSubmit(
            final PasswordChangeRequestWithoutAuthentication passwordChangeRequestWithoutAuthentication) {

        return accumulate(
                validationExternal(passwordChangeRequestWithoutAuthentication.getExternal()),
                validationNewPassword(passwordChangeRequestWithoutAuthentication.getPasswordNew1(), passwordChangeRequestWithoutAuthentication.getPasswordNew2()),
                (mailPasswordReset, password) -> {

                    final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

                    mailPasswordReset.setResetDateTime(now);
                    mailPasswordReset.setExpireDateTime(now);
                    mailPasswordReset.saveAndFlushHelper();

                    mailPasswordReset.userHelper().setPassword(password);
                    mailPasswordReset.userHelper().saveAndFlushHelper();

                    return mailPasswordReset;
                })
                .map(ignore -> new PasswordChangeResponseWithoutAuthentication());
    }

    public Validation<NonEmptyList<ValidationMessage<PasswordField>>, PasswordChangeResponseWithAuthentication> changeWithAuthenticationSubmit(
            final String requesterUsername,
            final PasswordChangeRequestWithAuthentication passwordChangeRequestWithAuthentication) {

        return accumulate(
                validationOldPassword(springSecurityService, requesterUsername, passwordChangeRequestWithAuthentication.getPasswordOld()),
                validationNewPassword(passwordChangeRequestWithAuthentication.getPasswordNew1(), passwordChangeRequestWithAuthentication.getPasswordNew2()),
                (user, password) -> {

                    user.setPassword(password);
                    user.saveAndFlushHelper();

                    return user;
                })
                .map(ignore -> new PasswordChangeResponseWithAuthentication());
    }
}
