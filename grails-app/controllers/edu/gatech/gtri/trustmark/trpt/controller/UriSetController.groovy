package edu.gatech.gtri.trustmark.trpt.controller


import edu.gatech.gtri.trustmark.trpt.service.uri.UriSetFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.uri.UriSetService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic


@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class UriSetController {

    UriSetService uriSetService

    Object manage() {
    }

    Object findOne(final UriSetFindAllRequest uriFindAllRequest) {

        respond uriSetService.findOne(((GrailsUser) getPrincipal()).username, uriFindAllRequest)
    }
}
