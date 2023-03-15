package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.v1_0.util.OrdUtility;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import org.gtri.fj.Ord;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;

import static edu.gatech.gtri.trustmark.grails.trpt.domain.Organization.findByNameHelper;
import static edu.gatech.gtri.trustmark.grails.trpt.domain.Organization.findByUriHelper;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeEmpty;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeNullOr;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.lang.LongUtility.longOrd;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class OrganizationValidationUtility {

    private OrganizationValidationUtility() {
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

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Organization> validationId(final long id, final List<Organization> organizationList) {

        return mustBeReference(OrganizationField.id, Organization::findByIdHelper, id, organizationList, equal((o1, o2) -> o1.idHelper() == o2.idHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<Organization>> validationIdList(final List<Long> idList, final List<Organization> organizationList) {

        return mustBeNonNullAndDistinctAndValid(OrganizationField.idList, idList, longOrd, id -> validationId(id, organizationList)
                .bind(organization -> accumulate(
                        mustBeEmpty(OrganizationField.userSet, organization.userSetHelper()),
                        mustBeEmpty(OrganizationField.protectedSystemSet, organization.protectedSystemSetHelper()),
                        mustBeEmpty(OrganizationField.trustmarkBindingRegistrySet, organization.trustmarkBindingRegistrySetHelper()),
                        (userSet, protectedSystemSet, trustmarkBindingRegistrySet) -> organization)));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>>> validationOrganizationTrustInteroperabilityProfileList(final List<OrganizationTrustInteroperabilityProfileUpsertRequest> organizationTrustInteroperabilityProfileList) {

        return mustBeNonNullAndDistinctAndValid(
                OrganizationField.organizationTrustInteroperabilityProfileList,
                organizationTrustInteroperabilityProfileList,
                Ord.contramap(OrganizationTrustInteroperabilityProfileUpsertRequest::getUri, OrdUtility.nullableOrd(stringOrd)),
                nonNullAndDistinct -> mustBeNonNullAndLength(
                        OrganizationField.uri,
                        1,
                        1000,
                        nonNullAndDistinct.getUri())
                        .map(uri -> p(TrustInteroperabilityProfileUri.findByUriHelper(uri).toEither(uri), nonNullAndDistinct.isMandatory())));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<PartnerOrganizationCandidate>> validationPartnerOrganizationCandidateList(final List<Long> partnerOrganizationCandidateList) {

        return mustBeNonNullAndDistinctAndValid(
                OrganizationField.partnerCandidateList,
                partnerOrganizationCandidateList,
                longOrd,
                nonNullAndDistinct -> validationPartnerOrganizationCandidate(nonNullAndDistinct));
    }

    public static Validation<NonEmptyList<ValidationMessage<OrganizationField>>, PartnerOrganizationCandidate> validationPartnerOrganizationCandidate(final long partnerOrganizationCandidate) {

        return mustBeReference(
                OrganizationField.partnerOrganizationCandidate,
                PartnerOrganizationCandidate::findByIdHelper,
                partnerOrganizationCandidate);
    }
}
