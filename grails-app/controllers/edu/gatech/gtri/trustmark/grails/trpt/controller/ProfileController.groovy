package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.profile.ProfileService
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
class ProfileController {

    ProfileService profileService

    @PreAuthorize('permitAll()')
    Object findOne() {

        ValidationResponse validationResponse = validationResponse(profileService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
