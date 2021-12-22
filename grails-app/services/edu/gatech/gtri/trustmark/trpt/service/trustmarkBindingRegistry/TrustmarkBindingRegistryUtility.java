package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUtility.organizationResponse;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.LongUtility.longOrd;

public final class TrustmarkBindingRegistryUtility {
    private static final Log log = LogFactory.getLog(TrustmarkBindingRegistryUtility.class);

    private TrustmarkBindingRegistryUtility() {
    }

    public static TrustmarkBindingRegistryResponse trustmarkBindingRegistryResponse(final TrustmarkBindingRegistry trustmarkBindingRegistry) {

        return trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryUriTypeSetHelper().maximumOption(ord((o1, o2) ->
                        o1.getDocumentRequestLocalDateTime() == null && o2.getDocumentRequestLocalDateTime() == null ? Ordering.EQ :
                                o1.getDocumentRequestLocalDateTime() == null ? Ordering.LT :
                                        o2.getDocumentRequestLocalDateTime() == null ? Ordering.GT :
                                                Ordering.fromInt(o1.getDocumentRequestLocalDateTime().compareTo(o2.getDocumentRequestLocalDateTime()))))
                .map(trustmarkBindingRegistryUriType -> new TrustmarkBindingRegistryResponse(
                        trustmarkBindingRegistry.idHelper(),
                        trustmarkBindingRegistry.getName(),
                        trustmarkBindingRegistry.getDescription(),
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getUri(),
                        trustmarkBindingRegistryUriType.getDocumentRequestLocalDateTime(),
                        trustmarkBindingRegistryUriType.getDocumentSuccessLocalDateTime(),
                        trustmarkBindingRegistryUriType.getDocumentFailureLocalDateTime(),
                        trustmarkBindingRegistryUriType.getDocumentFailureMessage(),
                        trustmarkBindingRegistryUriType.getServerRequestLocalDateTime(),
                        trustmarkBindingRegistryUriType.getServerSuccessLocalDateTime(),
                        trustmarkBindingRegistryUriType.getServerFailureLocalDateTime(),
                        trustmarkBindingRegistryUriType.getServerFailureMessage(),
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryUriTypeSetHelper().foldLeft((sum, trustmarkBindingRegistryUriTypeInner) -> sum + trustmarkBindingRegistryUriTypeInner.partnerSystemCandidateSetHelper().length(), 0),
                        organizationResponse(trustmarkBindingRegistry.organizationHelper())))
                .orSome(() -> new TrustmarkBindingRegistryResponse(
                        trustmarkBindingRegistry.idHelper(),
                        trustmarkBindingRegistry.getName(),
                        trustmarkBindingRegistry.getDescription(),
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().getUri(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryUriTypeSetHelper().foldLeft((sum, trustmarkBindingRegistryUriType) -> sum + trustmarkBindingRegistryUriType.partnerSystemCandidateSetHelper().length(), 0),
                        organizationResponse(trustmarkBindingRegistry.organizationHelper())));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationName(final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistry.findByOrganizationAndNameHelper(organization, nameInner)),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationName(final long id, final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistry.findByOrganizationAndNameHelper(organization, nameInner))
                        .filter(trustmarkBindingRegistry -> trustmarkBindingRegistry.idHelper() != id),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationUri(final long organizationId, final String uri) {

        return mustBeNonNullAndUniqueAndLength(
                TrustmarkBindingRegistryField.uri,
                uriInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> TrustmarkBindingRegistryUri.findByUriHelper(uri)
                                .bind(trustmarkBindingRegistryUri -> TrustmarkBindingRegistry.findByOrganizationAndTrustmarkBindingRegistryUriHelper(organization, trustmarkBindingRegistryUri))),
                1,
                1000,
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
                1000,
                uri);
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, String> validationDescription(final String description) {

        return mustBeNullOr(TrustmarkBindingRegistryField.description, description, (field, value) -> mustBeNonNullAndLength(field, 1, 1000, value));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistry> validationId(final long id, final List<Organization> organizationList) {

        return mustBeReference(TrustmarkBindingRegistryField.id, TrustmarkBindingRegistry::findByIdHelper, id, trustmarkBindingRegistry -> organizationList.exists(organization -> trustmarkBindingRegistry.organizationHelper().idHelper() == organization.idHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, List<TrustmarkBindingRegistry>> validationIdList(final List<Long> idList, final List<Organization> organizationList) {

        return mustBeNonNullAndDistinctAndValid(TrustmarkBindingRegistryField.idList, idList, longOrd, id -> validationId(id, organizationList));
    }

    public static Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, Organization> validationOrganization(final long organizationId, final List<Organization> organizationList) {

        return mustBeReference(TrustmarkBindingRegistryField.organization, Organization::findByIdHelper, organizationId, organizationList, equal((o1, o2) -> o1.idHelper() == o2.idHelper()));
    }

}
