package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.PARTNER_SYSTEM_CANDIDATE_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class PartnerSystemCandidateService {

    public Validation<NonEmptyList<ValidationMessage<PartnerSystemCandidateField>>, List<PartnerSystemCandidateResponse>> findAll(
            final String requesterUsername,
            final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return userMay(requesterUsername, PARTNER_SYSTEM_CANDIDATE_SELECT, (requesterUser, requesterProtectedSystemList, requesterRoleList) -> findAllHelper(requesterUser, requesterProtectedSystemList, requesterRoleList, partnerSystemCandidateFindAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<PartnerSystemCandidateField>>, List<PartnerSystemCandidateResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return success(PartnerSystemCandidate
                .findAllByTypeInHelper(requesterOrganizationList, partnerSystemCandidateFindAllRequest.getProtectedSystemType().getPartnerSystemCandidateTypeList())
                .map(PartnerSystemCandidateUtility::partnerSystemCandidateResponse));
    }
}
