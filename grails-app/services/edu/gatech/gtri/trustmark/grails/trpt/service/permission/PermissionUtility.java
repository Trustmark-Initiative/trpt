package edu.gatech.gtri.trustmark.grails.trpt.service.permission;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F3;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage.validationMessageMustHavePermission;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.data.Validation.fail;

public class PermissionUtility {

    private PermissionUtility() {
    }

    public static <T1, T2> Validation<NonEmptyList<ValidationMessage<T1>>, T2> userMay(final OAuth2AuthenticationToken requester, final Permission permissionName, final F3<User, List<Organization>, List<Role>, Validation<NonEmptyList<ValidationMessage<T1>>, T2>> f) {

        return User
                .findByUsernameHelper(requester.getName())
                .filter(user -> somes(iterableList(requester.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue)).exists(role -> permissionName.getRoleList().isEmpty() || permissionName.getRoleList().exists(roleInner -> role == roleInner)))
                .map(user -> f.f(user, organizationListAdministrator(requester), roleListAdministrator(requester)))
                .orSome(fail(nel(validationMessageMustHavePermission(null))));
    }

    private static List<Organization> organizationListAdministrator(final OAuth2AuthenticationToken requester) {

        return User
                .findByUsernameHelper(requester.getName())
                .map(user ->
                        somes(iterableList(requester.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue)).exists(role -> role == Role.ROLE_ADMINISTRATOR) ?
                                Organization
                                        .findAllByOrderByNameAscHelper() :
                                somes(iterableList(requester.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue)).exists(role -> role == Role.ROLE_ADMINISTRATOR_ORGANIZATION) ?
                                        user.organizationHelper().toList() :
                                        List.<Organization>nil())
                .orSome(nil());
    }

    private static List<Role> roleListAdministrator(final OAuth2AuthenticationToken requester) {

        return somes(iterableList(requester.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue));
    }
}
