package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryDeleteAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryFindOneRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryInsertRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryService
import edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUpdateRequest
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
class TrustmarkBindingRegistryController {

    TrustmarkBindingRegistryService trustmarkBindingRegistryService

    Object manage() {
    }

    Object findAll(final TrustmarkBindingRegistryFindAllRequest trustmarkBindingRegistryFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(trustmarkBindingRegistryService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), trustmarkBindingRegistryFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findOne(final TrustmarkBindingRegistryFindOneRequest trustmarkBindingRegistryFindOneRequest) {

        ValidationResponse validationResponse = validationResponse(trustmarkBindingRegistryService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), trustmarkBindingRegistryFindOneRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object insert(final TrustmarkBindingRegistryInsertRequest trustmarkBindingRegistryInsertRequest) {

        ValidationResponse validationResponse = validationResponse(trustmarkBindingRegistryService.insert((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), trustmarkBindingRegistryInsertRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object update(final TrustmarkBindingRegistryUpdateRequest trustmarkBindingRegistryUpdateRequest) {

        ValidationResponse validationResponse = validationResponse(trustmarkBindingRegistryService.update((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), trustmarkBindingRegistryUpdateRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object delete(final TrustmarkBindingRegistryDeleteAllRequest trustmarkBindingRegistryDeleteAllRequest) {

        ValidationResponse validationResponse = validationResponse(trustmarkBindingRegistryService.delete((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), trustmarkBindingRegistryDeleteAllRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
