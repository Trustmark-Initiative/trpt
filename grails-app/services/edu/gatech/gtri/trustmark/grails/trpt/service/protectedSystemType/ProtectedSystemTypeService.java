package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystemType;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemResponseUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemTypeResponse;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_TYPE_SELECT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class ProtectedSystemTypeService {

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemTypeField>>, List<ProtectedSystemTypeResponse>> findAll(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemTypeFindAllRequest protectedSystemTypeFindAllRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_TYPE_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemTypeFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemTypeField>>, List<ProtectedSystemTypeResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemTypeFindAllRequest protectedSystemTypeFindAllRequest) {

        return success(arrayList(ProtectedSystemType.values())
                .map(ProtectedSystemResponseUtility::protectedSystemTypeResponse));
    }
}
