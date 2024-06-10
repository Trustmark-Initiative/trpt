package edu.gatech.gtri.trustmark.grails.trpt.service.user;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;
import org.json.JSONArray;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static edu.gatech.gtri.trustmark.grails.trpt.domain.User.findAllByOrderByNameFamilyAscNameGivenAscHelper;
import static edu.gatech.gtri.trustmark.grails.trpt.domain.User.findAllByOrganizationIsNullOrderByNameFamilyAscNameGivenAscHelper;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.USER_DELETE;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.USER_INSERT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.USER_SELECT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.USER_SELECT_WITHOUT_ORGANIZATION;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.USER_UPDATE;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.userResponse;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationEditable;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationId;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationIdList;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationIdWithoutOrganization;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.validationUsername;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class UserService {

    public Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAll(
            final OAuth2AuthenticationToken requesterUsername,
            final UserFindAllRequest userFindAllRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOne(
            final OAuth2AuthenticationToken requesterUsername,
            final UserFindOneRequest userFindOneRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAllWithoutOrganization(
            final OAuth2AuthenticationToken requesterUsername,
            final UserFindAllRequest userFindAllRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_SELECT_WITHOUT_ORGANIZATION, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllWithoutOrganizationHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOneWithoutOrganization(
            final OAuth2AuthenticationToken requesterUsername,
            final UserFindOneRequest userFindOneRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_SELECT_WITHOUT_ORGANIZATION, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneWithoutOrganizationHelper(requesterUser, requesterOrganizationList, requesterRoleList, userFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> insert(
            final OAuth2AuthenticationToken requesterUsername,
            final UserInsertRequest userInsertRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, userInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> update(
            final OAuth2AuthenticationToken requesterUsername,
            final UserUpdateRequest userUpdateRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, userUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, Unit> delete(
            final OAuth2AuthenticationToken requesterUsername,
            final UserDeleteAllRequest userDeleteAllRequest) {

        return PermissionUtility.userMay(requesterUsername, USER_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, userDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserFindAllRequest userFindAllRequest) {

        return success(findAllByOrderByNameFamilyAscNameGivenAscHelper(requesterOrganizationList)
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

    private Validation<NonEmptyList<ValidationMessage<UserField>>, List<UserResponse>> findAllWithoutOrganizationHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserFindAllRequest userFindAllRequest) {

        return success(findAllByOrganizationIsNullOrderByNameFamilyAscNameGivenAscHelper()
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername()))));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOneWithoutOrganizationHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserFindOneRequest userFindOneRequest) {

        return validationIdWithoutOrganization(userFindOneRequest.getId())
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
    }

    public void upsertHelper(
            final String username,
            final String nameFamily,
            final String nameGiven,
            final String contactEmail,
            final List<Role> roleList) {

        User.withTransactionHelper( () -> {
            User user = User.findByUsernameHelper(username).orSome(new User());
            user.setUsername(username);
            user.setNameFamily(nameFamily);
            user.setNameGiven(nameGiven);
            user.setContactEmail(contactEmail);
            user.setRoleArrayJson(new JSONArray(roleList.map(role -> role.getValue())).toString());
            user.saveAndFlushHelper();
        });
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserInsertRequest userInsertRequest) {

        return accumulate(
                success(new User()),
                validationOrganization(userInsertRequest.getOrganization(), requesterOrganizationList),
                validationUsername(userInsertRequest.getUsername()),
                (user, organization, username) -> {
                    user.setUsername(username);
                    user.organizationHelper(organization);
                    return user.saveAndFlushHelper();
                })
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
    }

    private Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UserUpdateRequest userUpdateRequest) {

        return accumulate(
                validationId(userUpdateRequest.getId(), requesterOrganizationList),
                validationOrganization(userUpdateRequest.getOrganization(), requesterOrganizationList),
                validationEditable(userUpdateRequest.getId(), requesterUser.getUsername()),
                (user, organization, username) -> {
                    user.organizationHelper(organization);
                    return user.saveAndFlushHelper();
                })
                .map(user -> userResponse(user, !user.getUsername().equals(requesterUser.getUsername())));
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
