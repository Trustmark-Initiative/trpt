package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.uri.UriSetFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.uri.UriSetService
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
class UriSetController {

    UriSetService uriSetService

    Object manage() {
    }

    Object findOne(final UriSetFindAllRequest uriFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(uriSetService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), uriFindAllRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
