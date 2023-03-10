package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.mail.MailFindRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.mail.MailService
import edu.gatech.gtri.trustmark.grails.trpt.service.mail.MailTestRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.mail.MailUpdateRequest
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
class MailController {

    MailService mailService

    Object manage() {
    }

    Object changeWithAuthentication() {
    }

    Object find(final MailFindRequest mailFindRequest) {

        ValidationResponse validationResponse = validationResponse(mailService.find((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), mailFindRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object update(final MailUpdateRequest mailUpdateRequest) {

        ValidationResponse validationResponse = validationResponse(mailService.update((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), mailUpdateRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object test(final MailTestRequest mailTestRequest) {

        ValidationResponse validationResponse = validationResponse(mailService.test((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), mailTestRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
