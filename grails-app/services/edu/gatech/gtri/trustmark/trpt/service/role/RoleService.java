package edu.gatech.gtri.trustmark.trpt.service.role;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ROLE_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class RoleService {

    public Validation<NonEmptyList<ValidationMessage<RoleField>>, List<RoleResponse>> findAll(
            final String requesterUsername,
            final RoleFindAllRequest roleFindAllRequest) {

        return userMay(requesterUsername, ROLE_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, roleFindAllRequest));
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
