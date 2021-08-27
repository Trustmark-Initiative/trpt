package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.organizationResponse;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.lang.LongUtility.longOrd;

public final class TrustmarkBindingRegistryUtility {
    private static final Log log = LogFactory.getLog(TrustmarkBindingRegistryUtility.class);

    private TrustmarkBindingRegistryUtility() {
    }

    public static TrustmarkBindingRegistryResponse trustmarkBindingRegistryResponse(final TrustmarkBindingRegistry trustmarkBindingRegistry) {

        return new TrustmarkBindingRegistryResponse(
                trustmarkBindingRegistry.idHelper(),
                trustmarkBindingRegistry.getName(),
                trustmarkBindingRegistry.getDescription(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getUri(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getRequestLocalDateTime(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getSuccessLocalDateTime(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getFailureLocalDateTime(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getFailureMessage(),
                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().partnerSystemCandidateSetHelper().length(),
                organizationResponse(trustmarkBindingRegistry.organizationHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationName(final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistry.findByOrganizationAndNameHelper(organization, nameInner)),
                1,
                200,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationName(final long id, final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistry.findByOrganizationAndNameHelper(organization, nameInner))
                        .filter(trustmarkBindingRegistry -> trustmarkBindingRegistry.idHelper() != id),
                1,
                200,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationUri(final long organizationId, final String uri) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.uri,
                uriInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistryUri.findByUriHelper(uri)
                                .bind(trustmarkBindingRegistryUri -> TrustmarkBindingRegistry.findByOrganizationAndTrustmarkBindingRegistryUriHelper(organization, trustmarkBindingRegistryUri))),
                1,
                200,
                uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationUri(final long id, final long organizationId, final String uri) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.uri,
                uriInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistryUri.findByUriHelper(uri)
                                .bind(trustmarkBindingRegistryUri -> TrustmarkBindingRegistry.findByOrganizationAndTrustmarkBindingRegistryUriHelper(organization, trustmarkBindingRegistryUri)))
                        .filter(trustmarkBindingRegistry -> trustmarkBindingRegistry.idHelper() != id),
                1,
                200,
                uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationDescription(final String description) {

        return mustBeNullOr(TrustmarkBindingRegistryField.description, description, (field, value) -> mustBeNonNullAndLength(field, 1, 200, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistry> validationId(final long id) {

        return mustBeReference(TrustmarkBindingRegistryField.id, TrustmarkBindingRegistry::findByIdHelper, id);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, List<TrustmarkBindingRegistry>> validationIdList(final List<Long> idList) {

        return mustBeNonNullAndDistinctAndValid(TrustmarkBindingRegistryField.idList, idList, longOrd, TrustmarkBindingRegistryUtility::validationId);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, Organization> validationOrganization(final long organizationId) {

        return mustBeReference(TrustmarkBindingRegistryField.organization, Organization::findByIdHelper, organizationId);
    }

}
