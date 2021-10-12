package edu.gatech.gtri.trustmark.trpt.service.permission;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.PermissionName;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.RoleName;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustHavePermission;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.fail;

public class PermissionUtility {

    private PermissionUtility() {
    }

    public static List<Organization> organizationListAdministrator(final String requesterUsername) {

        return User
                .findByUsernameHelper(requesterUsername)
                .map(user ->
                        user.userRoleSetHelper().exists(userRole -> RoleName.valueOf(userRole.roleHelper().getName()).equals(RoleName.ROLE_ADMINISTRATOR)) ?
                                Organization
                                        .findAllByOrderByNameAscHelper() :
                                user
                                        .userRoleSetHelper()
                                        .filter(userRole -> RoleName.valueOf(userRole.roleHelper().getName()).equals(RoleName.ROLE_ADMINISTRATOR_ORGANIZATION))
                                        .headOption()
                                        .map(userRole -> arrayList(user.organizationHelper()))
                                        .orSome(nil()))
                .orSome(nil());
    }

    public static List<Role> roleListAdministrator(final String requesterUsername) {

        return User
                .findByUsernameHelper(requesterUsername)
                .map(user ->
                        user.userRoleSetHelper().exists(userRole -> RoleName.valueOf(userRole.roleHelper().getName()).equals(RoleName.ROLE_ADMINISTRATOR)) ?
                                Role
                                        .findAllByOrderByNameAscHelper() :
                                user
                                        .userRoleSetHelper()
                                        .filter(userRole -> RoleName.valueOf(userRole.roleHelper().getName()).equals(RoleName.ROLE_ADMINISTRATOR_ORGANIZATION))
                                        .headOption()
                                        .map(userRole -> arrayList(userRole.roleHelper()))
                                        .orSome(nil()))
                .orSome(nil());
    }

    public static <T1, T2> Validation<NonEmptyList<ValidationMessage<T1>>, T2> userMay(final String requesterUserName, final PermissionName permissionName, final F1<User, Validation<NonEmptyList<ValidationMessage<T1>>, T2>> f) {

        return User
                .findByUsernameHelper(requesterUserName)
                .filter(user -> user.userRoleSetHelper().exists(userRole -> RoleName.valueOf(userRole.roleHelper().getName()).equals(RoleName.ROLE_ADMINISTRATOR)))
                .map(user -> f.f(user))
                .orSome(fail(nel(validationMessageMustHavePermission(null))));
    }
}
