package edu.gatech.gtri.trustmark.grails.trpt.service.role;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static org.gtri.fj.data.Validation.success;

@Transactional
public class RoleService {

    public Validation<NonEmptyList<ValidationMessage<RoleField>>, List<RoleResponse>> findAll(
            final OAuth2AuthenticationToken requesterUsername,
            final RoleFindAllRequest roleFindAllRequest) {

        return PermissionUtility.userMay(requesterUsername, PermissionName.ROLE_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, roleFindAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<RoleField>>, List<RoleResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final RoleFindAllRequest roleFindAllRequest) {

        return success(requesterRoleList
                .map(RoleUtility::roleResponse));
    }
}
