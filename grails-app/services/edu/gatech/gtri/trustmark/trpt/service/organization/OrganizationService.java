package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.domain.PermissionName.ORGANIZATION_INSERT;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationDescription;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.validationUri;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.organizationListAdministrator;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class OrganizationService {

    public List<OrganizationResponse> findAll(
            final String requesterUsername,
            final OrganizationFindAllRequest organizationFindAllRequest) {

        return organizationListAdministrator(requesterUsername)
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> findOne(
            final String requesterUsername,
            final OrganizationFindOneRequest organizationFindOneRequest) {

        return validationId(organizationFindOneRequest.getId(), organizationListAdministrator(requesterUsername))
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> insert(
            final String requesterUsername,
            final OrganizationInsertRequest organizationInsertRequest) {

        return userMay(requesterUsername, ORGANIZATION_INSERT, user -> accumulate(
                validationName(organizationInsertRequest.getName()),
                validationUri(organizationInsertRequest.getUri()),
                validationDescription(organizationInsertRequest.getDescription()),
                (name, uri, description) -> {

                    final Organization organization = new Organization();

                    organization.setName(name);
                    organization.setUri(uri);
                    organization.setDescription(description);
                    organization.saveAndFlushHelper();

                    return organization;
                })
                .map(OrganizationUtility::organizationResponse));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> update(
            final String requesterUsername,
            final OrganizationUpdateRequest organizationUpdateRequest) {

        return accumulate(
                validationId(organizationUpdateRequest.getId(), organizationListAdministrator(requesterUsername)),
                validationName(organizationUpdateRequest.getId(), organizationUpdateRequest.getName()),
                validationUri(organizationUpdateRequest.getId(), organizationUpdateRequest.getUri()),
                validationDescription(organizationUpdateRequest.getDescription()),
                (organization, name, uri, description) -> {

                    organization.setName(name);
                    organization.setUri(uri);
                    organization.setDescription(description);
                    organization.saveAndFlushHelper();

                    return organization;
                })
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Unit> delete(
            final String requesterUsername,
            final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        return validationIdList(iterableList(organizationDeleteAllRequest.getIdList()), organizationListAdministrator(requesterUsername))
                .map(list -> list.map(organization -> {
                    organization.deleteAndFlushHelper();

                    return organization.idHelper();
                }))
                .map(ignore -> unit());
    }
}
