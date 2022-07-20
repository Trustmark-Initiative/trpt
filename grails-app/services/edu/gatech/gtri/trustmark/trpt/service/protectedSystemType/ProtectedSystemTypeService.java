package edu.gatech.gtri.trustmark.trpt.service.protectedSystemType;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemResponseUtility;
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemTypeResponse;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_TYPE_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class ProtectedSystemTypeService {

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemTypeField>>, List<ProtectedSystemTypeResponse>> findAll(
            final String requesterUsername,
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
