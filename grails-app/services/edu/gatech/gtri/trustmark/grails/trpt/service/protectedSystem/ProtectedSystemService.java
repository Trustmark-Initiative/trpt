package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemPartnerSystemCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.ApplicationProperties;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateFindOneRequest;
import edu.gatech.gtri.trustmark.grails.trpt.service.job.urisynchronizer.UriSynchronizerForTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.grails.trpt.service.trustInteroperabilityProfile.TrustInteroperabilityProfileUtility;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.grails.trpt.service.job.JobUtilityForPartnerSystemCandidateTrustInteroperabilityProfileUri.synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_DELETE;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_INSERT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_SELECT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PROTECTED_SYSTEM_UPDATE;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility.userMay;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationId;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationIdList;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationName;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationPartnerSystemCandidate;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationPartnerSystemCandidateList;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationProtectedSystemTrustInteroperabilityProfileList;
import static edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemValidationUtility.validationType;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.single;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class ProtectedSystemService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<ProtectedSystemResponse>> findAll(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemFindAllRequest protectedSystemFindAllRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> findOne(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> insert(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> update(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, Unit> delete(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, protectedSystemDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, List<ProtectedSystemResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemFindAllRequest protectedSystemFindAllRequest) {

        return success(ProtectedSystem.findAllByOrderByNameAscHelper(requesterOrganizationList)
                .map(protectedSystem -> ProtectedSystemResponseUtility.protectedSystemResponse(protectedSystem)));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        return validationId(protectedSystemFindOneRequest.getId(), requesterOrganizationList)
                .map(protectedSystem -> ProtectedSystemResponseUtility.protectedSystemResponse(protectedSystem));
    }

    private Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        return accumulate(
                success(new ProtectedSystem()),
                validationOrganization(protectedSystemInsertRequest.getOrganization(), requesterOrganizationList),
                validationProtectedSystemTrustInteroperabilityProfileList(iterableList(protectedSystemInsertRequest.getEntityTrustInteroperabilityProfileList())),
                validationPartnerSystemCandidateList(iterableList(protectedSystemInsertRequest.getPartnerCandidateList())),
                validationName(protectedSystemInsertRequest.getOrganization(), protectedSystemInsertRequest.getName()),
                validationType(protectedSystemInsertRequest.getType()),
                this::saveHelper)
                .map(this::synchronizeHelper);
    }

    private Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        return accumulate(
                validationId(protectedSystemUpdateRequest.getId(), requesterOrganizationList),
                validationOrganization(protectedSystemUpdateRequest.getOrganization(), requesterOrganizationList),
                validationProtectedSystemTrustInteroperabilityProfileList(iterableList(protectedSystemUpdateRequest.getEntityTrustInteroperabilityProfileList())),
                validationPartnerSystemCandidateList(iterableList(protectedSystemUpdateRequest.getPartnerCandidateList())),
                validationName(protectedSystemUpdateRequest.getId(), protectedSystemUpdateRequest.getOrganization(), protectedSystemUpdateRequest.getName()),
                validationType(protectedSystemUpdateRequest.getType()),
                this::saveHelper)
                .map(this::synchronizeHelper);
    }

    private ProtectedSystem saveHelper(
            final ProtectedSystem protectedSystem,
            final Organization organization,
            final List<P2<Either<String, TrustInteroperabilityProfileUri>, Boolean>> protectedSystemTrustInteroperabilityProfileList,
            final List<PartnerSystemCandidate> partnerSystemCandidateList,
            final String name,
            final ProtectedSystemType type) {

        // remove associations with trust interoperability profile uris
        protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper().forEach(protectedSystemTrustInteroperabilityProfileUri -> {
            protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(null);
            protectedSystemTrustInteroperabilityProfileUri.protectedSystemHelper(null);
            protectedSystemTrustInteroperabilityProfileUri.deleteHelper();
        });

        // remove associations with partner system candidates
        protectedSystem.protectedSystemPartnerSystemCandidateSetHelper().forEach(protectedSystemPartnerSystemCandidate -> {
            protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(null);
            protectedSystemPartnerSystemCandidate.protectedSystemHelper(null);
            protectedSystemPartnerSystemCandidate.deleteHelper();
        });

        // update the name and type
        protectedSystem.setName(name);
        protectedSystem.setType(type);

        // update the organization
        protectedSystem.organizationHelper(organization);

        // add the associations with trust interoperability profile uris
        protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper(protectedSystemTrustInteroperabilityProfileList.map(protectedSystemTrustInteroperabilityProfile -> {
            final ProtectedSystemTrustInteroperabilityProfileUri protectedSystemTrustInteroperabilityProfileUri = new ProtectedSystemTrustInteroperabilityProfileUri();
            protectedSystemTrustInteroperabilityProfileUri.setMandatory(protectedSystemTrustInteroperabilityProfile._2());
            protectedSystemTrustInteroperabilityProfileUri.protectedSystemHelper(protectedSystem);
            protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(TrustInteroperabilityProfileUtility.upsertTrustInteroperabilityProfileUri(protectedSystemTrustInteroperabilityProfile._1()));
            return protectedSystemTrustInteroperabilityProfileUri.saveHelper();
        }));

        // add the associations with partner system candidates
        protectedSystem.protectedSystemPartnerSystemCandidateSetHelper(partnerSystemCandidateList
                .filter(partnerSystemCandidate -> protectedSystem
                        .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                        .bind(protectedSystemTrustInteroperabilityProfileUri -> protectedSystemTrustInteroperabilityProfileUri
                                .trustInteroperabilityProfileUriHelper()
                                .partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper()
                                .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper() == partnerSystemCandidate.idHelper())
                                .map(partnerSystemCandidateTrustInteroperabilityProfileUri -> p(protectedSystemTrustInteroperabilityProfileUri.isMandatory(), partnerSystemCandidateTrustInteroperabilityProfileUri)))
                        .foldLeft((trustable, p) -> trustable && (!p._1() || fromNull(p._2().getEvaluationTrustExpressionSatisfied()).orSome(false)), true))
                .map(partnerSystemCandidate -> {
                    final ProtectedSystemPartnerSystemCandidate protectedSystemPartnerSystemCandidate = new ProtectedSystemPartnerSystemCandidate();
                    protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(partnerSystemCandidate);
                    protectedSystemPartnerSystemCandidate.protectedSystemHelper(protectedSystem);
                    return protectedSystemPartnerSystemCandidate.saveHelper();
                }));

        return protectedSystem.saveAndFlushHelper();
    }

    private ProtectedSystemResponse synchronizeHelper(
            final ProtectedSystem protectedSystem) {

        final ProtectedSystemResponse protectedSystemResponse = ProtectedSystemResponseUtility.protectedSystemResponse(protectedSystem);

        // synchronize evaluations, if necessary
        synchronizePartnerSystemCandidateTrustInteroperabilityProfileUri(
                Duration.parse(applicationProperties.getProperty(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum)),
                nil(),
                nil(),
                single(protectedSystem));

        // synchronize the trust interoperability profiles, if the system has not yet requested them
        protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper()
                .map(protectedSystemTrustInteroperabilityProfileUri -> protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())
                .filter(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri.getDocumentRequestLocalDateTime() == null)
                .map(trustInteroperabilityProfileUri -> trustInteroperabilityProfileUri.getUri())
                .forEach(uri -> (new Thread(() -> UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), uri))).start());

        return protectedSystemResponse;
    }

    private Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, Unit> deleteHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        return validationIdList(iterableList(protectedSystemDeleteAllRequest.getIdList()), requesterOrganizationList)
                .map(list -> list.map(protectedSystem -> {
                    protectedSystem.deleteAndFlushHelper();

                    return protectedSystem.idHelper();
                }))
                .map(ignore -> unit());
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponseWithTrustExpressionEvaluation> findOne(
            final OAuth2AuthenticationToken requesterUsername,
            final ProtectedEntityPartnerCandidateFindOneRequest protectedEntityPartnerCandidateFindOneRequest) {

        return userMay(requesterUsername, PROTECTED_SYSTEM_SELECT, (requesterUser, requesterProtectedSystemList, requesterRoleList) -> findOneHelper(requesterUser, requesterProtectedSystemList, requesterRoleList, protectedEntityPartnerCandidateFindOneRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponseWithTrustExpressionEvaluation> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final ProtectedEntityPartnerCandidateFindOneRequest protectedEntityPartnerCandidateFindOneRequest) {

        return accumulate(
                validationId(protectedEntityPartnerCandidateFindOneRequest.getId(), requesterOrganizationList),
                validationPartnerSystemCandidate(protectedEntityPartnerCandidateFindOneRequest.getPartnerCandidate()),
                (protectedSystem, partnerSystemCandidate) -> p(protectedSystem, partnerSystemCandidate))
                .map(p -> ProtectedSystemResponseUtility.protectedSystemResponseWithTrustExpressionEvaluation(p._1(), p._2()));
    }
}
