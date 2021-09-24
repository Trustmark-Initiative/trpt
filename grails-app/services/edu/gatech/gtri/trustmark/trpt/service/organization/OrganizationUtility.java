package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByNameHelper;
import static edu.gatech.gtri.trustmark.trpt.domain.Organization.findByUriHelper;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeEmpty;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.lang.LongUtility.longOrd;

public final class OrganizationUtility {

    private OrganizationUtility() {
    }

    public static OrganizationResponse organizationResponse(final Organization organization) {
        return new OrganizationResponse(
                organization.idHelper(),
                organization.getName(),
                organization.getDescription(),
                organization.getUri());
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationName(final String name) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.name, Organization::findByNameHelper, 1, 1000, name);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationName(final long id, final String name) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.name, nameInner -> findByNameHelper(nameInner).filter(organization -> organization.idHelper() != id), 1, 1000, name);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationUri(final String uri) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.uri, Organization::findByUriHelper, 1, 1000, uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationUri(final long id, final String uri) {

        return mustBeNonNullAndUniqueAndLength(OrganizationField.uri, uriInner -> findByUriHelper(uriInner).filter(organization -> organization.idHelper() != id), 1, 1000, uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, String> validationDescription(final String description) {

        return mustBeNullOr(OrganizationField.description, description, (field, value) -> mustBeNonNullAndLength(field, 1, 1000, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Organization> validationId(final long id) {

        return mustBeReference(OrganizationField.id, Organization::findByIdHelper, id);
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<Organization>> validationIdList(final List<Long> idList) {

        return mustBeNonNullAndDistinctAndValid(OrganizationField.idList, idList, longOrd, id -> validationId(id)
                .bind(organization -> accumulate(
                        mustBeEmpty(OrganizationField.userSet, organization.userSetHelper()),
                        mustBeEmpty(OrganizationField.protectedSystemSet, organization.protectedSystemSetHelper()),
                        mustBeEmpty(OrganizationField.trustmarkBindingRegistrySet, organization.trustmarkBindingRegistrySetHelper()),
                        (userSet, protectedSystemSet, trustmarkBindingRegistrySet) -> organization)));
    }
}
