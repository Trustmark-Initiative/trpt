package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationDeleteAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationFindOneRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationInsertRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationService
import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationUpdateRequest
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
class OrganizationController {

    OrganizationService organizationService

    Object manage() {
    }

    Object findAll(final OrganizationFindAllRequest organizationFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(organizationService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), organizationFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findOne(final OrganizationFindOneRequest organizationFindOneRequest) {

        ValidationResponse validationResponse = validationResponse(organizationService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), organizationFindOneRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object insert(final OrganizationInsertRequest organizationInsertRequest) {

        ValidationResponse validationResponse = validationResponse(organizationService.insert((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), organizationInsertRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object update(final OrganizationUpdateRequest organizationUpdateRequest) {

        ValidationResponse validationResponse = validationResponse(organizationService.update((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), organizationUpdateRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object delete(final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        ValidationResponse validationResponse = validationResponse(organizationService.delete((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), organizationDeleteAllRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

}
