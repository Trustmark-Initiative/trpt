package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserDeleteAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserFindOneRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserInsertRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserService
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUpdateRequest
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
class UserController {

    UserService userService

    Object manage() {}

    Object findAll(final UserFindAllRequest userFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(userService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findOne(final UserFindOneRequest userFindOneRequest) {

        ValidationResponse validationResponse = validationResponse(userService.findOne((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userFindOneRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findAllWithoutOrganization(final UserFindAllRequest userFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(userService.findAllWithoutOrganization((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object findOneWithoutOrganization(final UserFindOneRequest userFindOneRequest) {

        ValidationResponse validationResponse = validationResponse(userService.findOneWithoutOrganization((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userFindOneRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object insert(final UserInsertRequest userInsertRequest) {

        ValidationResponse validationResponse = validationResponse(userService.insert((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userInsertRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object update(final UserUpdateRequest userUpdateRequest) {

        ValidationResponse validationResponse = validationResponse(userService.update((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userUpdateRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }

    Object delete(final UserDeleteAllRequest userDeleteAllRequest) {

        ValidationResponse validationResponse = validationResponse(userService.delete((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), userDeleteAllRequest))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
