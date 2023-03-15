package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemDeleteAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemFindOneRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemInsertRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemService
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem.ProtectedSystemUpdateRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystemType.ProtectedSystemTypeFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystemType.ProtectedSystemTypeService
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
class ProtectedSystemController {

    ProtectedSystemService protectedSystemService
    ProtectedSystemTypeService protectedSystemTypeService

    Object manage() {
    }

    Object findAll(final ProtectedSystemFindAllRequest protectedSystemServiceFindAllRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemServiceFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findOne(final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemFindOneRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object insert(final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemService.insert((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemInsertRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object update(final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemService.update((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemUpdateRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object delete(final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemService.delete((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemDeleteAllRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object typeFindAll(final ProtectedSystemTypeFindAllRequest protectedSystemTypeFindAllRequest) {

        final ValidationResponse validationResponse = validationResponse(protectedSystemTypeService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), protectedSystemTypeFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
