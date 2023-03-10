package edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName.PARTNER_SYSTEM_CANDIDATE_SELECT;
import static edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility.userMay;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class PartnerSystemCandidateService {

    public Validation<NonEmptyList<ValidationMessage<PartnerSystemCandidateField>>, List<PartnerSystemCandidateResponse>> findAll(
            final OAuth2AuthenticationToken requesterUsername,
            final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return userMay(requesterUsername, PARTNER_SYSTEM_CANDIDATE_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findAllHelper(requesterUser, requesterOrganizationList, requesterRoleList, partnerSystemCandidateFindAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<PartnerSystemCandidateField>>, List<PartnerSystemCandidateResponse>> findAllHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return success(PartnerSystemCandidate
                .findAllByOrganizationInAndTypeInHelper(requesterOrganizationList, partnerSystemCandidateFindAllRequest.getProtectedSystemType().getPartnerSystemCandidateTypeList())
                .map(PartnerSystemCandidateUtility::partnerSystemCandidateResponse));
    }
}
