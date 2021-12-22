package edu.gatech.gtri.trustmark.trpt.service.user;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.domain.UserRole;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.domain.User.findAllByOrderByNameFamilyAscNameGivenAscHelper;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.USER_DELETE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.USER_INSERT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.USER_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.USER_UPDATE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.userResponse;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationEditable;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationNameFamily;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationNameGiven;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationRole;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationTelephone;
import static edu.gatech.gtri.trustmark.trpt.service.user.UserUtility.validationUsername;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class UserService {

    public Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAll(
            final String requesterUsername,
            final UserFindAllRequest userFindAllRequest) {

        return userMay(requesterUsername, USER_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOne(
            final String requesterUsername,
            final UserFindOneRequest userFindOneRequest) {

        return userMay(requesterUsername, USER_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> insert(
            final String requesterUsername,
            final UserInsertRequest userInsertRequest) {

        return userMay(requesterUsername, USER_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, userInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> update(
            final String requesterUsername,
            final UserUpdateRequest userUpdateRequest) {

        return userMay(requesterUsername, USER_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, userUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, Unit> delete(
            final String requesterUsername,
            final UserDeleteAllRequest userDeleteAllRequest) {

        return userMay(requesterUsername, USER_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, userDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserFindAllRequest userFindAllRequest) {

        return success(findAllByOrderByNameFamilyAscNameGivenAscHelper(requesterOrganizationList, requesterRoleList)
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername()))));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserFindOneRequest userFindOneRequest) {

        return validationId(userFindOneRequest.getId(), requesterOrganizationList)
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserInsertRequest userInsertRequest) {

        return accumulate(
                success(new User()),
                validationRole(userInsertRequest.getRole(), requesterRoleList),
                validationOrganization(userInsertRequest.getOrganization(), requesterOrganizationList),
                validationUsername(userInsertRequest.getUsername()),
                validationNameFamily(userInsertRequest.getNameFamily()),
                validationNameGiven(userInsertRequest.getNameGiven()),
                validationTelephone(userInsertRequest.getTelephone()),
                (user, role, organization, username, nameFamily, nameGiven, telephone) -> saveHelper(
                        user,
                        role,
                        organization,
                        username,
                        nameFamily,
                        nameGiven,
                        telephone,
                        userInsertRequest.isUserEnabled(),
                        userInsertRequest.isUserLocked(),
                        userInsertRequest.isUserExpired(),
                        userInsertRequest.isPasswordExpired()))
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserUpdateRequest userUpdateRequest) {

        return accumulate(
                validationId(userUpdateRequest.getId(), requesterOrganizationList),
                validationRole(userUpdateRequest.getRole(), requesterRoleList),
                validationOrganization(userUpdateRequest.getOrganization(), requesterOrganizationList),
                validationEditable(userUpdateRequest.getId(), requesterUser.getUsername()),
                validationUsername(userUpdateRequest.getId(), userUpdateRequest.getUsername()),
                validationNameFamily(userUpdateRequest.getNameFamily()),
                validationNameGiven(userUpdateRequest.getNameGiven()),
                validationTelephone(userUpdateRequest.getTelephone()),
                (user, role, organization, editable, username, nameFamily, nameGiven, telephone) -> saveHelper(
                        user,
                        role,
                        organization,
                        username,
                        nameFamily,
                        nameGiven,
                        telephone,
                        userUpdateRequest.isUserEnabled(),
                        userUpdateRequest.isUserLocked(),
                        userUpdateRequest.isUserExpired(),
                        userUpdateRequest.isPasswordExpired()))
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
    }

    private User saveHelper(
            final User user,
            final Role role,
            final Organization organization,
            final String username,
            final String nameFamily,
            final String nameGiven,
            final String telephone,
            final boolean userEnabled,
            final boolean userLocked,
            final boolean userExpired,
            final boolean passwordExpired) {

        user.userRoleSetHelper().forEach(userRoleInner -> {
            userRoleInner.userHelper(null);
            userRoleInner.roleHelper(null);
            userRoleInner.deleteHelper();
        });

        final UserRole userRole = new UserRole();
        userRole.userHelper(user);
        userRole.roleHelper(role);

        user.setUsername(username);
        user.setNameFamily(nameFamily);
        user.setNameGiven(nameGiven);
        user.setTelephone(telephone);
        user.setUserEnabled(userEnabled);
        user.setUserLocked(userLocked);
        user.setUserExpired(userExpired);
        user.setPasswordExpired(passwordExpired);
        user.organizationHelper(organization);
        user.userRoleSetHelper(arrayList(userRole));

        user.saveAndFlushHelper();

        return user;
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, Unit> deleteHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserDeleteAllRequest userDeleteAllRequest) {

        return validationIdList(iterableList(userDeleteAllRequest.getIdList()), requesterOrganizationList)
                .map(list -> list.map(user -> {
                    user.deleteAndFlushHelper();

                    return user.idHelper();
                }))
                .map(ignore -> unit());
    }
}
