package edu.gatech.gtri.trustmark.trpt.service.organization;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.OrganizationTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties;
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.Unit;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForPartnerOrganizationCandidateTrustInteroperabilityProfileUri.synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponseUtility.organizationResponseWithDetail;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponseUtility.organizationResponseWithTrustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationDescription;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationOrganizationTrustInteroperabilityProfileList;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationPartnerOrganizationCandidate;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationPartnerOrganizationCandidateList;
import static edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationValidationUtility.validationUri;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_DELETE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_INSERT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.ORGANIZATION_UPDATE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility.upsertTrustInteroperabilityProfileUri;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.single;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class OrganizationService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<OrganizationResponseWithDetail>> findAll(
            final String requesterUsername,
            final OrganizationFindAllRequest organizationFindAllRequest) {

        return userMay(requesterUsername, ORGANIZATION_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> findOne(
            final String requesterUsername,
            final OrganizationFindOneRequest organizationFindOneRequest) {

        return userMay(requesterUsername, ORGANIZATION_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> insert(
            final String requesterUsername,
            final OrganizationInsertRequest organizationInsertRequest) {

        return userMay(requesterUsername, ORGANIZATION_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> update(
            final String requesterUsername,
            final OrganizationUpdateRequest organizationUpdateRequest) {

        return userMay(requesterUsername, ORGANIZATION_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, Unit> delete(
            final String requesterUsername,
            final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        return userMay(requesterUsername, ORGANIZATION_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, List<OrganizationResponseWithDetail>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationFindAllRequest organizationFindAllRequest) {

        return success(requesterOrganizationList
                .map(OrganizationResponseUtility::organizationResponseWithDetail));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationFindOneRequest organizationFindOneRequest) {

        return validationId(organizationFindOneRequest.getId(), requesterOrganizationList)
                .map(OrganizationResponseUtility::organizationResponseWithDetail);
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationInsertRequest organizationInsertRequest) {

        return accumulate(
                success(new Organization()),
                validationOrganizationTrustInteroperabilityProfileList(iterableList(organizationInsertRequest.getEntityTrustInteroperabilityProfileList())),
                validationPartnerOrganizationCandidateList(iterableList(organizationInsertRequest.getPartnerCandidateList())),
                validationName(organizationInsertRequest.getName()),
                validationUri(organizationInsertRequest.getUri()),
                validationDescription(organizationInsertRequest.getDescription()),
                this::saveHelper)
                .map(this::synchronizeHelper);
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithDetail> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationUpdateRequest organizationUpdateRequest) {

        return accumulate(
                validationId(organizationUpdateRequest.getId(), requesterOrganizationList),
                validationOrganizationTrustInteroperabilityProfileList(iterableList(organizationUpdateRequest.getEntityTrustInteroperabilityProfileList())),
                validationPartnerOrganizationCandidateList(iterableList(organizationUpdateRequest.getPartnerCandidateList())),
                validationName(organizationUpdateRequest.getId(), organizationUpdateRequest.getName()),
                validationUri(organizationUpdateRequest.getId(), organizationUpdateRequest.getUri()),
                validationDescription(organizationUpdateRequest.getDescription()),
                this::saveHelper)
                .map(this::synchronizeHelper);
    }

    private Organization saveHelper(
            final Organization organization,
            final List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>> organizationTrustInteroperabilityProfileList,
            final List<PartnerOrganizationCandidate> partnerOrganizationCandidateList,
            final String name,
            final String uri,
            final String description) {

        // remove associations with trust interoperability profile uris
        organization.organizationTrustInteroperabilityProfileUriSetHelper().forEach(organizationTrustInteroperabilityProfileUri -> {
            organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(null);
            organizationTrustInteroperabilityProfileUri.organizationHelper(null);
            organizationTrustInteroperabilityProfileUri.deleteHelper();
        });

        // remove associations with partner system candidates
        organization.organizationPartnerOrganizationCandidateSetHelper().forEach(organizationPartnerOrganizationCandidate -> {
            organizationPartnerOrganizationCandidate.partnerOrganizationCandidateHelper(null);
            organizationPartnerOrganizationCandidate.organizationHelper(null);
            organizationPartnerOrganizationCandidate.deleteHelper();
        });

        organization.setName(name);
        organization.setUri(uri);
        organization.setDescription(description);

        // add the associations with trust interoperability profile uris
        organization.organizationTrustInteroperabilityProfileUriSetHelper(organizationTrustInteroperabilityProfileList.map(organizationTrustInteroperabilityProfile -> {
            final OrganizationTrustInteroperabilityProfileUri organizationTrustInteroperabilityProfileUri = new OrganizationTrustInteroperabilityProfileUri();
            organizationTrustInteroperabilityProfileUri.setMandatory(organizationTrustInteroperabilityProfile._2());
            organizationTrustInteroperabilityProfileUri.organizationHelper(organization);
            organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(upsertTrustInteroperabilityProfileUri(organizationTrustInteroperabilityProfile._1()));
            return organizationTrustInteroperabilityProfileUri.saveHelper();
        }));

        // add the associations with partner system candidates
        organization.organizationPartnerOrganizationCandidateSetHelper(partnerOrganizationCandidateList
                .filter(partnerOrganizationCandidate -> organization
                        .organizationTrustInteroperabilityProfileUriSetHelper()
                        .bind(organizationTrustInteroperabilityProfileUri -> organizationTrustInteroperabilityProfileUri
                                .trustInteroperabilityProfileUriHelper()
                                .partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper()
                                .filter(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().idHelper() == partnerOrganizationCandidate.idHelper())
                                .map(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> p(organizationTrustInteroperabilityProfileUri.isMandatory(), partnerOrganizationCandidateTrustInteroperabilityProfileUri)))
                        .foldLeft((trustable, p) -> trustable && (!p._1() || fromNull(p._2().getEvaluationTrustExpressionSatisfied()).orSome(false)), true))
                .map(partnerOrganizationCandidate -> {
                    final OrganizationPartnerOrganizationCandidate organizationPartnerOrganizationCandidate = new OrganizationPartnerOrganizationCandidate();
                    organizationPartnerOrganizationCandidate.partnerOrganizationCandidateHelper(partnerOrganizationCandidate);
                    organizationPartnerOrganizationCandidate.organizationHelper(organization);
                    return organizationPartnerOrganizationCandidate.saveHelper();
                }));

        return organization.saveAndFlushHelper();
    }

    private OrganizationResponseWithDetail synchronizeHelper(
            final Organization organization) {

        final OrganizationResponseWithDetail organizationResponse = organizationResponseWithDetail(organization);

        // synchronize evaluations, if necessary
        synchronizePartnerOrganizationCandidateTrustInteroperabilityProfileUri(
                Duration.parse(applicationProperties.getProperty(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum)),
                nil(),
                nil(),
                single(organization));

        // synchronize the trust interoperability profiles, if the system has not yet requested them
        organization.organizationTrustInteroperabilityProfileUriSetHelper()
                .map(organizationTrustInteroperabilityProfileUri -> organizationTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                .filter(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri.getDocumentRequestLocalDateTime() == null)
                .map(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri.getUri())
                .forEach(trustInteroperabilityProfileUriUri -> (new Thread(() -> UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), trustInteroperabilityProfileUriUri))).start());

        return organizationResponse;
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

    public Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithTrustExpressionEvaluation> findOne(
            final String requesterUsername,
            final OrganizationPartnerOrganizationCandidateFindOneRequest organizationPartnerOrganizationCandidateFindOneRequest) {

        return userMay(requesterUsername, ORGANIZATION_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, organizationPartnerOrganizationCandidateFindOneRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<OrganizationField>>, OrganizationResponseWithTrustExpressionEvaluation> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final OrganizationPartnerOrganizationCandidateFindOneRequest organizationPartnerOrganizationCandidateFindOneRequest) {

        return accumulate(
                OrganizationValidationUtility.validationId(organizationPartnerOrganizationCandidateFindOneRequest.getId(), requesterOrganizationList),
                validationPartnerOrganizationCandidate(organizationPartnerOrganizationCandidateFindOneRequest.getPartnerCandidate()),
                (organization, partnerOrganizationCandidate) -> p(organization, partnerOrganizationCandidate))
                .map(p -> organizationResponseWithTrustExpressionEvaluation(p._1(), p._2()));
    }
}
