package edu.gatech.gtri.trustmark.grails.trpt.service.user;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationResponseUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.grails.trpt.service.role.RoleUtility;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.json.JSONArray;

import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.iif;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.lang.LongUtility.longOrd;

public final class UserUtility {

    private UserUtility() {
    }

    public static UserResponse userResponse(final User user, final boolean editable) {

        return new UserResponse(
                user.idHelper(),
                user.getUsername(),
                user.getNameFamily(),
                user.getNameGiven(),
                user.getContactEmail(),
                user.organizationHelper().map(OrganizationResponseUtility::organizationResponse).toNull(),
                somes(fromNull(user.getRoleArrayJson())
                        .map(JSONArray::new)
                        .map(JSONArray::toList)
                        .map(List::iterableList)
                        .orSome(List.nil())
                        .map(object -> Role.fromValue(object.toString())))
                        .map(RoleUtility::roleResponse)
                        .headOption()
                        .toNull(),
                editable);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, String> validationUsername(final String username) {

        return ValidationUtility.mustBeNonNullAndUniqueAndLength(UserField.username, User::findByUsernameHelper, 1, 1000, username);
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, User> validationEditable(final long id, final String requesterUsername) {

        return ValidationUtility.mustBeReference(UserField.id, User::findByIdHelper, id, value -> !value.getUsername().equals(requesterUsername));
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, User> validationId(final long id, final List<Organization> organizationList) {

        return ValidationUtility.mustBeReference(UserField.id, User::findByIdHelper, id, user -> organizationList.exists(organization -> user.organizationHelper().map(organizationInner -> organizationInner.idHelper() == organization.idHelper()).orSome(true)));
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, User> validationIdWithoutOrganization(final long id) {

        return ValidationUtility.mustBeReference(UserField.id, User::findByIdHelper, id, user -> user.organizationHelper().isNone());
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, Option<Organization>> validationOrganization(final long organization, final List<Organization> organizationList) {

        return ValidationUtility.mustBeNoneOr(UserField.organization, iif(organization != 0, organization), (field, value) -> ValidationUtility.mustBeReference(field, Organization::findByIdHelper, value, organizationList, equal((o1, o2) -> o1.idHelper() == o2.idHelper())));
    }

    public static Validation<NonEmptyList<ValidationMessage<UserField>>, List<User>> validationIdList(final List<Long> idList, final List<Organization> organizationList) {

        return ValidationUtility.mustBeNonNullAndDistinctAndValid(UserField.idList, idList, longOrd, id -> validationId(id, organizationList));
    }
}
