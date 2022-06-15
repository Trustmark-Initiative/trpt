package edu.gatech.gtri.trustmark.trpt.service.mail;

import edu.gatech.gtri.trustmark.trpt.domain.Mail;
import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.mailResponse;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.send;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationAuthor;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationHost;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationPassword;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationPort;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationRecipient;
import static edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility.validationUsername;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.MAIL_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.MAIL_TEST;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.MAIL_UPDATE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class MailService {

    public Validation<NonEmptyList<ValidationMessage<MailField>>, MailResponse> find(
            final String requesterUsername,
            final MailFindRequest mailFindRequest) {

        return userMay(requesterUsername, MAIL_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findHelper(requesterUser, requesterOrganizationList, requesterRoleList, mailFindRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<MailField>>, MailResponse> update(
            final String requesterUsername,
            final MailUpdateRequest mailUpdateRequest) {

        return userMay(requesterUsername, MAIL_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, mailUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<MailField>>, Unit> test(
            final String requesterUsername,
            final MailTestRequest mailTestRequest) {

        return userMay(requesterUsername, MAIL_TEST, (requesterUser, requesterOrganizationList, requesterRoleList) -> testHelper(requesterUser, requesterOrganizationList, requesterRoleList, mailTestRequest));
    }


    public Validation<NonEmptyList<ValidationMessage<MailField>>, MailResponse> findHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final MailFindRequest mailFindRequest) {

        return success(mailResponse(Mail.findAllHelper().head()));
    }

    public Validation<NonEmptyList<ValidationMessage<MailField>>, MailResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final MailUpdateRequest mailUpdateRequest) {

        return accumulate(
                validationHost(mailUpdateRequest.getHost()),
                validationPort(mailUpdateRequest.getPort()),
                validationUsername(mailUpdateRequest.getUsername()),
                validationPassword(mailUpdateRequest.getPassword()),
                validationAuthor(mailUpdateRequest.getAuthor()),
                (host, port, username, password, author) -> {

                    final Mail mail = Mail.findAllHelper().head();

                    mail.setHost(mailUpdateRequest.getHost());
                    mail.setPort(port);
                    mail.setUsername(mailUpdateRequest.getUsername());
                    mail.setPassword(mailUpdateRequest.getPassword());
                    mail.setAuthor(mailUpdateRequest.getAuthor());

                    return mail.saveAndFlushHelper();
                })
                .map(MailUtility::mailResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<MailField>>, Unit> testHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final MailTestRequest mailTestRequest) {

        return validationRecipient(mailTestRequest.getRecipient())
                .map(recipient -> {

                    send(nel(mailTestRequest.getRecipient()), "Relying Party Tool Mail Relay Test", "This is a test of the relying party tool mail relay.");

                    return unit();
                });
    }

}
