package edu.gatech.gtri.trustmark.trpt.controller


import edu.gatech.gtri.trustmark.trpt.service.uri.UriSetFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.uri.UriSetService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse


@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR")')
class UriSetController {

    UriSetService uriSetService

    Object manage() {
    }

    Object findOne(final UriSetFindAllRequest uriFindAllRequest) {

        P2<Object, Integer> response = toResponse(uriSetService.findOne(((GrailsUser) getPrincipal()).username, uriFindAllRequest))

        respond response._1(), status: response._2()
    }
}
