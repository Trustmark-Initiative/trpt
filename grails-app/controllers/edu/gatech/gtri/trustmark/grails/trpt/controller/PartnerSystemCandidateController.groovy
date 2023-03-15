package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate.PartnerSystemCandidateFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate.PartnerSystemCandidateService
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationResponse
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationResponseUtility.validationResponse

@CompileStatic
@Transactional
@GrailsCompileStatic
class PartnerSystemCandidateController {

    PartnerSystemCandidateService partnerSystemCandidateService

    Object findAll(final PartnerSystemCandidateFindAllRequest partnerSystemCandidateFindAllRequest) {

        final ValidationResponse validationResponse = validationResponse(partnerSystemCandidateService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), partnerSystemCandidateFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
