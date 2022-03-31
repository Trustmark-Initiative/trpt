package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.partnerSystemCandidate.PartnerSystemCandidateService
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
@GrailsCompileStatic
class PartnerSystemCandidateController {

    PartnerSystemCandidateService partnerSystemCandidateService

    Object findAll(final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        final P2<Object, Integer> response = toResponse(partnerSystemCandidateService.findAll(((GrailsUser) getPrincipal()).username, partnerSystemCandidateFindAllRequest).map(list -> list.toJavaList()))

        respond response._1(), status: response._2()
    }
}
