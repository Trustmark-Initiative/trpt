package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.*;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class OrganizationService {

    public List<OrganizationResponse> findAll(final OrganizationFindAllRequest organizationFindAllRequest) {

        return Organization
                .findAllByOrderByNameAscHelper()
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> findOne(final OrganizationFindOneRequest organizationFindOneRequest) {

        return validationId(organizationFindOneRequest.getId())
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> insert(final OrganizationInsertRequest organizationInsertRequest) {

        return accumulate(
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
                .map(OrganizationUtility::organizationResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponse> update(final OrganizationUpdateRequest organizationUpdateRequest) {

        return accumulate(
                validationId(organizationUpdateRequest.getId()),
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

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Unit> delete(final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        return validationIdList(iterableList(organizationDeleteAllRequest.getIdList()))
                .map(list -> list.map(organization -> {
                    organization.deleteAndFlushHelper();

                    return organization.idHelper();
                }))
                .map(ignore -> unit());
    }
}
