package edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate;

import edu.gatech.gtri.trustmark.trpt.domain.PartnerSystemCandidate;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;

@Transactional
public class PartnerSystemCandidateService {

    public List<PartnerSystemCandidateResponse> findAll(final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        return PartnerSystemCandidate
                .findAllByTypeInHelper(partnerSystemCandidateFindAllRequest.getProtectedSystemType().getPartnerSystemCandidateTypeList())
                .map(PartnerSystemCandidateUtility::partnerSystemCandidateResponse);
    }
}
