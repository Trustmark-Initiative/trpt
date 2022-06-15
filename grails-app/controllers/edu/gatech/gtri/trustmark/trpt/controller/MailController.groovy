package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.mail.MailFindRequest
import edu.gatech.gtri.trustmark.trpt.service.mail.MailService
import edu.gatech.gtri.trustmark.trpt.service.mail.MailTestRequest
import edu.gatech.gtri.trustmark.trpt.service.mail.MailUpdateRequest
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR")')
@GrailsCompileStatic
class MailController {

    MailService mailService

    Object manage() {
    }

    Object changeWithAuthentication() {
    }

    Object find(final MailFindRequest mailFindRequest) {

        P2<Object, Integer> response = toResponse(mailService.find(((GrailsUser) getPrincipal()).username, mailFindRequest))

        respond response._1(), status: response._2()
    }

    Object update(final MailUpdateRequest mailUpdateRequest) {

        P2<Object, Integer> response = toResponse(mailService.update(((GrailsUser) getPrincipal()).username, mailUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object test(final MailTestRequest mailTestRequest) {

        P2<Object, Integer> response = toResponse(mailService.test(((GrailsUser) getPrincipal()).username, mailTestRequest))

        respond response._1(), status: response._2()
    }
}
