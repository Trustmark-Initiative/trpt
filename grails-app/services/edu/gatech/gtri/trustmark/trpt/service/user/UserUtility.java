package edu.gatech.gtri.trustmark.trpt.service.user;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.domain.User.findByUsernameHelper;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.organizationResponse;
import static edu.gatech.gtri.trustmark.trpt.service.role.RoleUtility.roleResponse;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.lang.LongUtility.longOrd;

public final class UserUtility {

    private UserUtility() {
    }

    public static UserResponse userResponse(final User user) {

        return new UserResponse(
                user.idHelper(),
                user.getUsername(),
                user.getNameFamily(),
                user.getNameGiven(),
                user.getTelephone(),
                user.getUserEnabled(),
                user.getUserLocked(),
                user.getUserExpired(),
                user.getPasswordExpired(),
                organizationResponse(user.organizationHelper()),
                roleResponse(user.userRoleHelper().head().roleHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, User> validationId(final long id) {

        return mustBeReference(UserField.id, User::findByIdHelper, id);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationUsername(final String username) {

        return mustBeNonNullAndUniqueAndLength(UserField.username, User::findByUsernameHelper, 1, 1000, username);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationUsername(final long id, final String username) {

        return mustBeNonNullAndUniqueAndLength(UserField.username, usernameInner -> findByUsernameHelper(usernameInner).filter(user -> user.idHelper() != id), 1, 1000, username);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationNameFamily(final String nameFamily) {

        return mustBeNonNullAndLength(UserField.nameFamily, 1, 1000, nameFamily);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationNameGiven(final String nameGiven) {

        return mustBeNonNullAndLength(UserField.nameGiven, 1, 1000, nameGiven);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationTelephone(final String telephone) {

        return mustBeNonNullAndLength(UserField.telephone, 1, 1000, telephone);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, Organization> validationOrganization(final long organization) {

        return mustBeReference(UserField.organization, Organization::findByIdHelper, organization);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, Role> validationRole(final long role) {

        return mustBeReference(UserField.role, Role::findByIdHelper, role);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, List<User>> validationIdList(final List<Long> idList) {

        return mustBeNonNullAndDistinctAndValid(UserField.idList, idList, longOrd, UserUtility::validationId);
    }
}
