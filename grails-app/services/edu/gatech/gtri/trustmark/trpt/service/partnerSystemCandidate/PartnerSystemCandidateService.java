package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;

import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.organizationListAdministrator;

@Transactional
public class PartnerSystemCandidateService {

    public List<PartnerSystemCandidateResponse> findAll(
            final String requesterUsername,
            final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return PartnerSystemCandidate
                .findAllByTypeInHelper(organizationListAdministrator(requesterUsername), partnerSystemCandidateFindAllRequest.getProtectedSystemType().getPartnerSystemCandidateTypeList())
                .map(PartnerSystemCandidateUtility::partnerSystemCandidateResponse);
    }
}
