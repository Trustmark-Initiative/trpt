package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustmarkBindingRegistry.synchronizeTrustmarkBindingRegistryUriAndDependencies;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.TRUSTMARK_BINDING_REGISTRY_DELETE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.TRUSTMARK_BINDING_REGISTRY_INSERT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.TRUSTMARK_BINDING_REGISTRY_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.TRUSTMARK_BINDING_REGISTRY_UPDATE;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationDescription;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationUri;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class TrustmarkBindingRegistryService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, List<TrustmarkBindingRegistryResponse>> findAll(
            final String requesterUsername,
            final TrustmarkBindingRegistryFindAllRequest trustmarkBindingRegistryFindAllRequest) {

        return userMay(requesterUsername, TRUSTMARK_BINDING_REGISTRY_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, trustmarkBindingRegistryFindAllRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> findOne(
            final String requesterUsername,
            final TrustmarkBindingRegistryFindOneRequest trustmarkBindingRegistryFindOneRequest) {

        return userMay(requesterUsername, TRUSTMARK_BINDING_REGISTRY_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, trustmarkBindingRegistryFindOneRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> insert(
            final String requesterUsername,
            final TrustmarkBindingRegistryInsertRequest trustmarkBindingRegistryInsertRequest) {

        return userMay(requesterUsername, TRUSTMARK_BINDING_REGISTRY_INSERT, (requesterUser, requesterOrganizationList, requesterRoleList) -> insertHelper(requesterUser, requesterOrganizationList, requesterRoleList, trustmarkBindingRegistryInsertRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> update(
            final String requesterUsername,
            final TrustmarkBindingRegistryUpdateRequest trustmarkBindingRegistryUpdateRequest) {

        return userMay(requesterUsername, TRUSTMARK_BINDING_REGISTRY_UPDATE, (requesterUser, requesterOrganizationList, requesterRoleList) -> updateHelper(requesterUser, requesterOrganizationList, requesterRoleList, trustmarkBindingRegistryUpdateRequest));
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, Unit> delete(
            final String requesterUsername,
            final TrustmarkBindingRegistryDeleteAllRequest trustmarkBindingRegistryDeleteAllRequest) {

        return userMay(requesterUsername, TRUSTMARK_BINDING_REGISTRY_DELETE, (requesterUser, requesterOrganizationList, requesterRoleList) -> deleteHelper(requesterUser, requesterOrganizationList, requesterRoleList, trustmarkBindingRegistryDeleteAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, List<TrustmarkBindingRegistryResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final TrustmarkBindingRegistryFindAllRequest trustmarkBindingRegistryFindAllRequest) {

        return success(TrustmarkBindingRegistry
                .findAllByOrderByNameAscHelper(requesterOrganizationList)
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse));
    }

    private Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final TrustmarkBindingRegistryFindOneRequest trustmarkBindingRegistryFindOneRequest) {

        return validationId(trustmarkBindingRegistryFindOneRequest.getId(), requesterOrganizationList)
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    private Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> insertHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final TrustmarkBindingRegistryInsertRequest trustmarkBindingRegistryInsertRequest) {

        return accumulate(
                success(new TrustmarkBindingRegistry()),
                validationOrganization(trustmarkBindingRegistryInsertRequest.getOrganization(), requesterOrganizationList),
                validationName(trustmarkBindingRegistryInsertRequest.getOrganization(), trustmarkBindingRegistryInsertRequest.getName()),
                validationUri(trustmarkBindingRegistryInsertRequest.getOrganization(), trustmarkBindingRegistryInsertRequest.getUri()),
                validationDescription(trustmarkBindingRegistryInsertRequest.getDescription()),
                this::saveHelper)
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    private Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> updateHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final TrustmarkBindingRegistryUpdateRequest trustmarkBindingRegistryUpdateRequest) {

        return accumulate(
                validationId(trustmarkBindingRegistryUpdateRequest.getId(), requesterOrganizationList),
                validationOrganization(trustmarkBindingRegistryUpdateRequest.getOrganization(), requesterOrganizationList),
                validationName(trustmarkBindingRegistryUpdateRequest.getId(), trustmarkBindingRegistryUpdateRequest.getOrganization(), trustmarkBindingRegistryUpdateRequest.getName()),
                validationUri(trustmarkBindingRegistryUpdateRequest.getId(), trustmarkBindingRegistryUpdateRequest.getOrganization(), trustmarkBindingRegistryUpdateRequest.getUri()),
                validationDescription(trustmarkBindingRegistryUpdateRequest.getDescription()),
                this::saveHelper)
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    private TrustmarkBindingRegistry saveHelper(
            final TrustmarkBindingRegistry trustmarkBindingRegistry,
            final Organization organization,
            final String name,
            final String uri,
            final String description) {

        trustmarkBindingRegistry.setName(name);
        trustmarkBindingRegistry.setDescription(description);
        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper(upsertTrustmarkBindingRegistryUri(uri));
        trustmarkBindingRegistry.organizationHelper(organization);
        trustmarkBindingRegistry.saveAndFlushHelper();

        (new Thread(() -> synchronizeTrustmarkBindingRegistryUriAndDependencies(
                Duration.parse(applicationProperties.getProperty(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum)),
                LocalDateTime.now(ZoneOffset.UTC),
                uri))).start();

        return trustmarkBindingRegistry;
    }

    private TrustmarkBindingRegistryUri upsertTrustmarkBindingRegistryUri(
            final String uri) {

        return TrustmarkBindingRegistryUri.findByUriHelper(uri).orSome(() -> {
            final TrustmarkBindingRegistryUri trustmarkBindingRegistryUriInner = new TrustmarkBindingRegistryUri();
            trustmarkBindingRegistryUriInner.setUri(uri);
            return trustmarkBindingRegistryUriInner.saveHelper();
        });
    }

    private Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, Unit> deleteHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final TrustmarkBindingRegistryDeleteAllRequest trustmarkBindingRegistryDeleteAllRequest) {

        return validationIdList(iterableList(trustmarkBindingRegistryDeleteAllRequest.getIdList()), requesterOrganizationList)
                .map(list -> list.map(trustmarkBindingRegistry -> {

                    trustmarkBindingRegistry.deleteHelper();

                    if (trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper().length() == 1) {

                        // if the trustmark binding registry is the last trustmark binding registry associated with the uri, delete the dependent entities

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySystemMapUriTypeSetHelper().forEach(trustmarkBindingRegistrySystemMapUriType -> {
                            trustmarkBindingRegistrySystemMapUriType.partnerSystemCandidateSetHelper().forEach(partnerSystemCandidate -> {

                                partnerSystemCandidate.partnerSystemCandidateTrustmarkUriSetHelper().forEach(partnerSystemCandidateTrustmarkUri -> {
                                    partnerSystemCandidateTrustmarkUri.partnerSystemCandidateHelper(null);
                                    partnerSystemCandidateTrustmarkUri.trustmarkUriHelper(null);
                                    partnerSystemCandidateTrustmarkUri.deleteHelper();
                                });

                                partnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper().forEach(partnerSystemCandidateTrustInteroperabilityProfileUri -> {
                                    partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(null);
                                    partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(null);
                                    partnerSystemCandidateTrustInteroperabilityProfileUri.deleteHelper();
                                });

                                partnerSystemCandidate.protectedSystemPartnerSystemCandidateSetHelper().forEach(protectedSystemPartnerSystemCandidate -> {
                                    protectedSystemPartnerSystemCandidate.protectedSystemHelper(null);
                                    protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(null);
                                    protectedSystemPartnerSystemCandidate.deleteHelper();
                                });

                                partnerSystemCandidate.partnerSystemCandidateTrustmarkUriSetHelper(nil());
                                partnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(nil());
                                partnerSystemCandidate.protectedSystemPartnerSystemCandidateSetHelper(nil());
                                partnerSystemCandidate.deleteHelper();
                            });

                            trustmarkBindingRegistrySystemMapUriType.partnerSystemCandidateSetHelper(nil());
                            trustmarkBindingRegistrySystemMapUriType.deleteHelper();
                        });

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryOrganizationMapUriSetHelper().forEach(trustmarkBindingRegistryOrganizationMapUri -> {
                            trustmarkBindingRegistryOrganizationMapUri.partnerOrganizationCandidateSetHelper().forEach(partnerOrganizationCandidate -> {

                                partnerOrganizationCandidate.partnerOrganizationCandidateTrustmarkUriSetHelper().forEach(partnerOrganizationCandidateTrustmarkUri -> {
                                    partnerOrganizationCandidateTrustmarkUri.partnerOrganizationCandidateHelper(null);
                                    partnerOrganizationCandidateTrustmarkUri.trustmarkUriHelper(null);
                                    partnerOrganizationCandidateTrustmarkUri.deleteHelper();
                                });

                                partnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper().forEach(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> {
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper(null);
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(null);
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUri.deleteHelper();
                                });

                                partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSetHelper().forEach(organizationPartnerOrganizationCandidate -> {
                                    organizationPartnerOrganizationCandidate.organizationHelper(null);
                                    organizationPartnerOrganizationCandidate.partnerOrganizationCandidateHelper(null);
                                    organizationPartnerOrganizationCandidate.deleteHelper();
                                });

                                partnerOrganizationCandidate.partnerOrganizationCandidateTrustmarkUriSetHelper(nil());
                                partnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(nil());
                                partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSetHelper(nil());
                                partnerOrganizationCandidate.deleteHelper();
                            });

                            trustmarkBindingRegistryOrganizationMapUri.partnerOrganizationCandidateSetHelper(nil());
                            trustmarkBindingRegistryOrganizationMapUri.deleteHelper();
                        });

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySystemMapUriTypeSetHelper(nil());
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryOrganizationMapUriSetHelper(nil());
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper(nil());
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().deleteAndFlushHelper();

                    } else {

                        // if the trustmark binding registry is not the last trustmark binding registry associated with the uri, only delete the associations between the trustmark binding registry and the protected systems

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySystemMapUriTypeSetHelper().forEach(trustmarkBindingRegistrySystemMapUriType -> {
                            trustmarkBindingRegistrySystemMapUriType.partnerSystemCandidateSetHelper().forEach(partnerSystemCandidate -> {

                                partnerSystemCandidate.protectedSystemPartnerSystemCandidateSetHelper()
                                        .filter(protectedSystemPartnerSystemCandidate -> protectedSystemPartnerSystemCandidate.protectedSystemHelper().organizationHelper().equals(trustmarkBindingRegistry.organizationHelper()))
                                        .forEach(protectedSystemPartnerSystemCandidate -> {
                                            protectedSystemPartnerSystemCandidate.protectedSystemHelper(null);
                                            protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(null);
                                            protectedSystemPartnerSystemCandidate.deleteHelper();
                                        });

                                partnerSystemCandidate.protectedSystemPartnerSystemCandidateSetHelper(
                                        partnerSystemCandidate.protectedSystemPartnerSystemCandidateSetHelper()
                                                .filter(protectedSystemPartnerSystemCandidate -> protectedSystemPartnerSystemCandidate.protectedSystemHelper() != null));

                                partnerSystemCandidate.saveHelper();
                            });
                        });

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistryOrganizationMapUriSetHelper().forEach(trustmarkBindingRegistryOrganizationMapUri -> {
                            trustmarkBindingRegistryOrganizationMapUri.partnerOrganizationCandidateSetHelper().forEach(partnerOrganizationCandidate -> {

                                partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSetHelper()
                                        .filter(organizationPartnerOrganizationCandidate -> organizationPartnerOrganizationCandidate.organizationHelper().equals(trustmarkBindingRegistry.organizationHelper()))
                                        .forEach(organizationPartnerOrganizationCandidate -> {
                                            organizationPartnerOrganizationCandidate.organizationHelper(null);
                                            organizationPartnerOrganizationCandidate.partnerOrganizationCandidateHelper(null);
                                            organizationPartnerOrganizationCandidate.deleteHelper();
                                        });

                                partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSetHelper(
                                        partnerOrganizationCandidate.organizationPartnerOrganizationCandidateSetHelper()
                                                .filter(organizationPartnerOrganizationCandidate -> organizationPartnerOrganizationCandidate.organizationHelper() != null));

                                partnerOrganizationCandidate.saveHelper();
                            });
                        });

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper(
                                trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper()
                                        .filter(trustmarkBindingRegistryInner -> !trustmarkBindingRegistryInner.equals(trustmarkBindingRegistry)));

                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper().saveAndFlushHelper();
                    }

                    return trustmarkBindingRegistry.idHelper();
                }))
                .map(ignore -> unit());
    }
}
