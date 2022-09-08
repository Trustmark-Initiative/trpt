package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.Ordering;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndDistinctAndValid;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeNonNullAndUniqueAndLength;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationUtility.mustBeReference;
import static org.gtri.fj.Equal.equal;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.LongUtility.longOrd;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class ProtectedSystemValidationUtility {
    private ProtectedSystemValidationUtility() {
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, Organization> validationOrganization(final long organizationId, final List<Organization> organizationList) {

        return mustBeReference(ProtectedSystemField.organization, Organization::findByIdHelper, organizationId, organizationList, equal((o1, o2) -> o1.idHelper() == o2.idHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, String> validationName(final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                ProtectedSystemField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> ProtectedSystem.findByOrganizationAndNameHelper(organization, nameInner)),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, String> validationName(final long id, final long organizationId, final String name) {

        return mustBeNonNullAndUniqueAndLength(
                ProtectedSystemField.name,
                nameInner -> Organization.findByIdHelper(organizationId)
                        .bind(organization -> ProtectedSystem.findByOrganizationAndNameHelper(organization, nameInner))
                        .filter(protectedSystem -> protectedSystem.idHelper() != id),
                1,
                1000,
                name);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>>> validationProtectedSystemTrustInteroperabilityProfileList(final List<ProtectedSystemTrustInteroperabilityProfileUpsertRequest> protectedSystemTrustInteroperabilityProfileList) {

        return mustBeNonNullAndDistinctAndValid(
                ProtectedSystemField.protectedSystemTrustInteroperabilityProfileList,
                protectedSystemTrustInteroperabilityProfileList,
                ord((o1, o2) -> o1.getUri() == null && o2.getUri() == null ?
                        Ordering.EQ :
                        o1.getUri() == null ?
                                Ordering.LT :
                                o2.getUri() == null ?
                                        Ordering.GT :
                                        stringOrd.compare(o1.getUri(), o2.getUri())),
                nonNullAndDistinct -> mustBeNonNullAndLength(
                        ProtectedSystemField.uri,
                        1,
                        1000,
                        nonNullAndDistinct.getUri())
                        .map(uri -> p(TrustInteroperabilityProfileUri.findByUriHelper(uri).toEither(uri), nonNullAndDistinct.isMandatory())));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<PartnerSystemCandidate>> validationPartnerSystemCandidateList(final List<Long> partnerSystemCandidateList) {

        return mustBeNonNullAndDistinctAndValid(
                ProtectedSystemField.partnerSystemCandidateList,
                partnerSystemCandidateList,
                longOrd,
                nonNullAndDistinct -> validationPartnerSystemCandidate(nonNullAndDistinct));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, PartnerSystemCandidate> validationPartnerSystemCandidate(final long partnerSystemCandidate) {

        return mustBeReference(
                ProtectedSystemField.partnerSystemCandidate,
                PartnerSystemCandidate::findByIdHelper,
                partnerSystemCandidate);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemType> validationType(final ProtectedSystemType protectedSystemType) {

        return mustBeNonNull(ProtectedSystemField.type, protectedSystemType);
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystem> validationId(final long id, final List<Organization> organizationList) {

        return mustBeReference(ProtectedSystemField.id, ProtectedSystem::findByIdHelper, id, protectedSystem -> organizationList.exists(organization -> protectedSystem.organizationHelper().idHelper() == organization.idHelper()));
    }

    public static Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<ProtectedSystem>> validationIdList(final List<Long> idList, final List<Organization> organizationList) {

        return mustBeNonNullAndDistinctAndValid(ProtectedSystemField.idList, idList, longOrd, id -> validationId(id, organizationList));
    }
}
