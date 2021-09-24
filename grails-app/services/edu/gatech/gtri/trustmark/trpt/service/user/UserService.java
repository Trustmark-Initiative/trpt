package edu.gatech.gtri.trustmark.trpt.service.user;

import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.domain.UserRole;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.domain.User.findAllByOrderByNameFamilyAscNameGivenAscHelper;
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
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class UserService {

    public List<UserResponse> findAll(
            final String requesterUsername,
            final UserFindAllRequest userFindAllRequest) {

        return findAllByOrderByNameFamilyAscNameGivenAscHelper()
                .map(UserUtility::userResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> findOne(
            final String requesterUsername,
            final UserFindOneRequest userFindOneRequest) {

        return validationId(userFindOneRequest.getId())
                .map(UserUtility::userResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> insert(
            final String requesterUsername,
            final UserInsertRequest userInsertRequest) {

        return accumulate(
                validationUsername(userInsertRequest.getUsername()),
                validationNameFamily(userInsertRequest.getNameFamily()),
                validationNameGiven(userInsertRequest.getNameGiven()),
                validationTelephone(userInsertRequest.getTelephone()),
                validationOrganization(userInsertRequest.getOrganization()),
                validationRole(userInsertRequest.getRole()),
                (username, nameFamily, nameGiven, telephone, organization, role) -> {

                    final User user = new User();
                    final UserRole userRole = new UserRole();

                    user.setUsername(userInsertRequest.getUsername());
                    user.setNameFamily(userInsertRequest.getNameFamily());
                    user.setNameGiven(userInsertRequest.getNameGiven());
                    user.setTelephone(userInsertRequest.getTelephone());
                    user.setUserEnabled(userInsertRequest.isUserEnabled());
                    user.setUserLocked(userInsertRequest.isUserLocked());
                    user.setUserExpired(userInsertRequest.isUserExpired());
                    user.setPasswordExpired(userInsertRequest.isPasswordExpired());
                    user.organizationHelper(organization);
                    user.userRoleHelper(arrayList(userRole));

                    userRole.userHelper(user);
                    userRole.roleHelper(role);

                    user.saveAndFlushHelper();

                    return user;
                })
                .map(UserUtility::userResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, UserResponse> update(
            final String requesterUsername,
            final UserUpdateRequest userUpdateRequest) {

        return accumulate(
                validationId(userUpdateRequest.getId()),
                validationUsername(userUpdateRequest.getId(), userUpdateRequest.getUsername()),
                validationNameFamily(userUpdateRequest.getNameFamily()),
                validationNameGiven(userUpdateRequest.getNameGiven()),
                validationTelephone(userUpdateRequest.getTelephone()),
                validationOrganization(userUpdateRequest.getOrganization()),
                validationRole(userUpdateRequest.getRole()),
                (user, username, nameFamily, nameGiven, telephone, organization, role) -> {

                    user.userRoleHelper().forEach(userRole -> {
                        userRole.userHelper(null);
                        userRole.roleHelper(null);
                        userRole.deleteHelper();
                    });

                    final UserRole userRole = new UserRole();

                    user.setUsername(userUpdateRequest.getUsername());
                    user.setNameFamily(userUpdateRequest.getNameFamily());
                    user.setNameGiven(userUpdateRequest.getNameGiven());
                    user.setTelephone(userUpdateRequest.getTelephone());
                    user.setUserEnabled(userUpdateRequest.isUserEnabled());
                    user.setUserLocked(userUpdateRequest.isUserLocked());
                    user.setUserExpired(userUpdateRequest.isUserExpired());
                    user.setPasswordExpired(userUpdateRequest.isPasswordExpired());
                    user.organizationHelper(organization);
                    user.userRoleHelper(arrayList(userRole));

                    userRole.userHelper(user);
                    userRole.roleHelper(role);

                    user.saveAndFlushHelper();

                    return user;
                })
                .map(UserUtility::userResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<UserField>>, Unit> delete(
            final String requesterUsername,
            final UserDeleteAllRequest userDeleteAllRequest) {

        return validationIdList(iterableList(userDeleteAllRequest.getIdList()))
                .map(list -> list.map(user -> {
                    user.deleteAndFlushHelper();

                    return user.idHelper();
                }))
                .map(ignore -> unit());
    }
}
