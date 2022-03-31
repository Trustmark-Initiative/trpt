package edu.gatech.gtri.trustmark.trpt.controller

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityUtils
import groovy.transform.CompileStatic
import org.springframework.security.access.annotation.Secured

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
@GrailsCompileStatic
class LogoutController {

    def index() {
        redirect uri: ((ConfigObject) SpringSecurityUtils.securityConfig.get('logout')).get('filterProcessesUrl')
    }
}
