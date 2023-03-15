package edu.gatech.gtri.trustmark.grails.trpt.controller

import edu.gatech.gtri.trustmark.grails.trpt.service.role.RoleFindAllRequest
import edu.gatech.gtri.trustmark.grails.trpt.service.role.RoleService
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
class RoleController {

    RoleService roleService

    Object manage() {
    }

    Object findAll(final RoleFindAllRequest roleFindAllRequest) {

        ValidationResponse validationResponse = validationResponse(roleService.findAll((OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication(), roleFindAllRequest).map(list -> list.toJavaList()))

        respond validationResponse.getResponseBody(), status: validationResponse.getResponseStatus()
    }
}
