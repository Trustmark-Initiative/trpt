package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.password.PasswordChangeRequestWithAuthentication
import edu.gatech.gtri.trustmark.trpt.service.password.PasswordChangeRequestWithoutAuthentication
import edu.gatech.gtri.trustmark.trpt.service.password.PasswordResetRequest
import edu.gatech.gtri.trustmark.trpt.service.password.PasswordResetStatusRequest
import edu.gatech.gtri.trustmark.trpt.service.password.PasswordService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
class PasswordController {

    PasswordService passwordService

    def reset() {
    }

    def resetSubmit(final PasswordResetRequest passwordResetRequest) {

        P2<Object, Integer> response = toResponse(passwordService.resetSubmit(passwordResetRequest))

        // The service returns whether the given username is valid; do not provide that information to the requester.

        respond new HashMap<>(), status: 200
    }

    def resetStatusSubmit(final PasswordResetStatusRequest passwordResetStatusRequest) {

        P2<Object, Integer> response = toResponse(passwordService.resetStatusSubmit(passwordResetStatusRequest))

        respond response._1(), status: response._2()
    }

    def changeWithoutAuthentication() {
    }

    def changeWithoutAuthenticationSubmit(final PasswordChangeRequestWithoutAuthentication passwordChangeRequestWithoutAuthentication) {

        P2<Object, Integer> response = toResponse(passwordService.changeWithoutAuthenticationSubmit(passwordChangeRequestWithoutAuthentication))

        respond response._1(), status: response._2()
    }

    @Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
    def changeWithAuthentication() {
    }

    @Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
    def changeWithAuthenticationSubmit(final PasswordChangeRequestWithAuthentication passwordChangeRequestWithAuthentication) {

        P2<Object, Integer> response = toResponse(passwordService.changeWithAuthenticationSubmit(((GrailsUser) getPrincipal()).username, passwordChangeRequestWithAuthentication))

        respond response._1(), status: response._2()
    }
}
