package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class PartnerSystemCandidateController {

    PartnerSystemCandidateService partnerSystemCandidateService

    Object findAll(final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        respond partnerSystemCandidateService.findAll(partnerSystemCandidateFindAllRequest).toJavaList()
    }
}
