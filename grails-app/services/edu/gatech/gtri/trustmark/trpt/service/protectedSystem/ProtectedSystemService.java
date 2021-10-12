package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemPartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustInteroperabilityProfileUri.synchronizeTrustInteroperabilityProfileUri;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.organizationListAdministrator;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.protectedSystemPartnerSystemCandidateResponse;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.protectedSystemResponse;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationPartnerSystemCandidate;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationPartnerSystemCandidateList;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationProtectedSystemTrustInteroperabilityProfileList;
import static edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUtility.validationType;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class ProtectedSystemService {

    public List<ProtectedSystemTypeResponse> typeFindAll(
            final String requesterUsername) {

        return arrayList(ProtectedSystemType.values())
                .map(ProtectedSystemUtility::protectedSystemTypeResponse);
    }

    public List<ProtectedSystemResponse> findAll(
            final String requesterUsername,
            final ProtectedSystemFindAllRequest protectedSystemFindAllRequest) {

        return ProtectedSystem
                .findAllByOrderByNameAscHelper(organizationListAdministrator(requesterUsername))
                .map(protectedSystem -> protectedSystemResponse(protectedSystem, organizationListAdministrator(requesterUsername)));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> findOne(
            final String requesterUsername,
            final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        return validationId(protectedSystemFindOneRequest.getId(), organizationListAdministrator(requesterUsername))
                .map(protectedSystem -> protectedSystemResponse(protectedSystem, organizationListAdministrator(requesterUsername)));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> insert(
            final String requesterUsername,
            final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        return accumulate(
                validationOrganization(protectedSystemInsertRequest.getOrganization(), organizationListAdministrator(requesterUsername)),
                validationProtectedSystemTrustInteroperabilityProfileList(iterableList(protectedSystemInsertRequest.getProtectedSystemTrustInteroperabilityProfileList())),
                validationPartnerSystemCandidateList(iterableList(protectedSystemInsertRequest.getPartnerSystemCandidateList())),
                validationName(protectedSystemInsertRequest.getOrganization(), protectedSystemInsertRequest.getName()),
                validationType(protectedSystemInsertRequest.getType()),
                (organization, protectedSystemTrustInteroperabilityProfileList, partnerSystemCandidateList, name, type) -> {

                    final ProtectedSystem protectedSystem = new ProtectedSystem();

                    protectedSystem.setType(type);
                    protectedSystem.setName(name);

                    protectedSystem.organizationHelper(organization);

                    protectedSystem.protectedSystemPartnerSystemCandidateSetHelper(partnerSystemCandidateList.map(partnerSystemCandidate -> {

                        final ProtectedSystemPartnerSystemCandidate protectedSystemPartnerSystemCandidate = new ProtectedSystemPartnerSystemCandidate();
                        protectedSystemPartnerSystemCandidate.protectedSystemHelper(protectedSystem);
                        protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(partnerSystemCandidate);

                        return protectedSystemPartnerSystemCandidate.saveHelper();
                    }));

                    protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper(protectedSystemTrustInteroperabilityProfileList.map(protectedSystemTrustInteroperabilityProfile -> {

                        final ProtectedSystemTrustInteroperabilityProfileUri protectedSystemTrustInteroperabilityProfileUri = new ProtectedSystemTrustInteroperabilityProfileUri();
                        protectedSystemTrustInteroperabilityProfileUri.setMandatory(protectedSystemTrustInteroperabilityProfile._2());
                        protectedSystemTrustInteroperabilityProfileUri.protectedSystemHelper(protectedSystem);
                        protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(reduce(protectedSystemTrustInteroperabilityProfile._1().leftMap(
                                uri -> {
                                    final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri();
                                    trustInteroperabilityProfileUri.setUri(uri);
                                    trustInteroperabilityProfileUri.saveHelper();

                                    (new Thread(() -> synchronizeTrustInteroperabilityProfileUri(LocalDateTime.now(ZoneOffset.UTC), uri))).start();

                                    return trustInteroperabilityProfileUri;
                                })));

                        return protectedSystemTrustInteroperabilityProfileUri.saveHelper();
                    }));

                    return protectedSystem.saveAndFlushHelper();
                })
                .map(protectedSystem -> protectedSystemResponse(protectedSystem, organizationListAdministrator(requesterUsername)));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemResponse> update(
            final String requesterUsername,
            final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        return accumulate(
                validationId(protectedSystemUpdateRequest.getId(), organizationListAdministrator(requesterUsername)),
                validationProtectedSystemTrustInteroperabilityProfileList(iterableList(protectedSystemUpdateRequest.getProtectedSystemTrustInteroperabilityProfileList())),
                validationPartnerSystemCandidateList(iterableList(protectedSystemUpdateRequest.getPartnerSystemCandidateList())),
                validationOrganization(protectedSystemUpdateRequest.getOrganization(), organizationListAdministrator(requesterUsername)),
                validationName(protectedSystemUpdateRequest.getId(), protectedSystemUpdateRequest.getOrganization(), protectedSystemUpdateRequest.getName()),
                validationType(protectedSystemUpdateRequest.getType()),
                (protectedSystem, protectedSystemTrustInteroperabilityProfileList, partnerSystemCandidateList, organization, name, type) -> {

                    protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper().forEach(protectedSystemTrustInteroperabilityProfileUri -> {
                        protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(null);
                        protectedSystemTrustInteroperabilityProfileUri.protectedSystemHelper(null);
                        protectedSystemTrustInteroperabilityProfileUri.deleteHelper();
                    });
                    protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper(nil());
                    protectedSystem.saveAndFlushHelper();

                    protectedSystem.protectedSystemPartnerSystemCandidateSetHelper().forEach(protectedSystemPartnerSystemCandidate -> {
                        protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(null);
                        protectedSystemPartnerSystemCandidate.protectedSystemHelper(null);
                        protectedSystemPartnerSystemCandidate.deleteHelper();
                    });
                    protectedSystem.protectedSystemPartnerSystemCandidateSetHelper(nil());
                    protectedSystem.saveAndFlushHelper();

                    protectedSystem.setName(name);
                    protectedSystem.setType(type);

                    protectedSystem.organizationHelper(organization);

                    protectedSystem.protectedSystemPartnerSystemCandidateSetHelper(partnerSystemCandidateList.map(partnerSystemCandidate -> {

                        final ProtectedSystemPartnerSystemCandidate protectedSystemPartnerSystemCandidate = new ProtectedSystemPartnerSystemCandidate();
                        protectedSystemPartnerSystemCandidate.partnerSystemCandidateHelper(partnerSystemCandidate);
                        protectedSystemPartnerSystemCandidate.protectedSystemHelper(protectedSystem);

                        return protectedSystemPartnerSystemCandidate.saveAndFlushHelper();
                    }));

                    protectedSystem.protectedSystemTrustInteroperabilityProfileUriSetHelper(protectedSystemTrustInteroperabilityProfileList.map(protectedSystemTrustInteroperabilityProfile -> {

                        final ProtectedSystemTrustInteroperabilityProfileUri protectedSystemTrustInteroperabilityProfileUri = new ProtectedSystemTrustInteroperabilityProfileUri();
                        protectedSystemTrustInteroperabilityProfileUri.setMandatory(protectedSystemTrustInteroperabilityProfile._2());
                        protectedSystemTrustInteroperabilityProfileUri.protectedSystemHelper(protectedSystem);
                        protectedSystemTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper(reduce(protectedSystemTrustInteroperabilityProfile._1().leftMap(
                                uri -> {
                                    final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri();
                                    trustInteroperabilityProfileUri.setUri(uri);

                                    (new Thread(() -> synchronizeTrustInteroperabilityProfileUri(LocalDateTime.now(ZoneOffset.UTC), uri))).start();

                                    return trustInteroperabilityProfileUri.saveAndFlushHelper();
                                })));

                        return protectedSystemTrustInteroperabilityProfileUri.saveAndFlushHelper();
                    }));

                    return protectedSystem.saveAndFlushHelper();
                })
                .map(protectedSystem -> protectedSystemResponse(protectedSystem, organizationListAdministrator(requesterUsername)));
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, Unit> delete(
            final String requesterUsername,
            final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        return validationIdList(iterableList(protectedSystemDeleteAllRequest.getIdList()), organizationListAdministrator(requesterUsername))
                .map(list -> list.map(ProtectedSystem -> {
                    ProtectedSystem.deleteAndFlushHelper();

                    return ProtectedSystem.idHelper();
                }))
                .map(ignore -> unit());
    }

    public Validation<NonEmptyList<ValidationMessage<ProtectedSystemField>>, ProtectedSystemPartnerSystemCandidateResponse> findOne(
            final String requesterUsername,
            final ProtectedSystemPartnerSystemCandidateFindOneRequest protectedSystemPartnerSystemCandidateFindOneRequest) {

        return accumulate(
                validationId(protectedSystemPartnerSystemCandidateFindOneRequest.getId(), organizationListAdministrator(requesterUsername)),
                validationPartnerSystemCandidate(protectedSystemPartnerSystemCandidateFindOneRequest.getPartnerSystemCandidate()),
                (protectedSystem, partnerSystemCandidate) -> p(protectedSystem, partnerSystemCandidate))
                .map(p -> protectedSystemPartnerSystemCandidateResponse(p._1(), p._2()));
    }
}
