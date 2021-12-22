package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationDescription;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationUri;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_DELETE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_INSERT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_UPDATE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class OrganizationService {

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<OrganizationResponse>> findAll(
            final String requesterUsername,
            final OrganizationFindAllRequest organizationFindAllRequest) {

        return userMay(requesterUsername, ORGANIZATION_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> findOne(
            final String requesterUsername,
            final OrganizationFindOneRequest organizationFindOneRequest) {

        return userMay(requesterUsername, ORGANIZATION_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> insert(
            final String requesterUsername,
            final OrganizationInsertRequest organizationInsertRequest) {

        return userMay(requesterUsername, ORGANIZATION_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> update(
            final String requesterUsername,
            final OrganizationUpdateRequest organizationUpdateRequest) {

        return userMay(requesterUsername, ORGANIZATION_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Unit> delete(
            final String requesterUsername,
            final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        return userMay(requesterUsername, ORGANIZATION_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<OrganizationResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationFindAllRequest organizationFindAllRequest) {

        return success(requesterOrganizationList
                .map(OrganizationUtility::organizationResponse));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationFindOneRequest organizationFindOneRequest) {

        return validationId(organizationFindOneRequest.getId(), requesterOrganizationList)
                .map(OrganizationUtility::organizationResponse);
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationInsertRequest organizationInsertRequest) {

        return saveHelper(
                success(new Organization()),
                validationName(organizationInsertRequest.getName()),
                validationUri(organizationInsertRequest.getUri()),
                validationDescription(organizationInsertRequest.getDescription()));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationUpdateRequest organizationUpdateRequest) {

        return saveHelper(
                validationId(organizationUpdateRequest.getId(), requesterOrganizationList),
                validationName(organizationUpdateRequest.getId(), organizationUpdateRequest.getName()),
                validationUri(organizationUpdateRequest.getId(), organizationUpdateRequest.getUri()),
                validationDescription(organizationUpdateRequest.getDescription()));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> saveHelper(
            final Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Organization> validationId,
            final Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationName,
            final Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationUri,
            final Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationDescription) {

        return accumulate(
                validationId,
                validationName,
                validationUri,
                validationDescription,
                (organization, name, uri, description) -> {
                    organization.setName(name);
                    organization.setUri(uri);
                    organization.setDescription(description);
                    return organization.saveAndFlushHelper();
                })
                .map(OrganizationUtility::organizationResponse);
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Unit> deleteHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        return validationIdList(iterableList(organizationDeleteAllRequest.getIdList()), requesterOrganizationList)
                .map(list -> list.map(organization -> {
                    organization.deleteAndFlushHelper();

                    return organization.idHelper();
                }))
                .map(ignore -> unit());
    }
}
