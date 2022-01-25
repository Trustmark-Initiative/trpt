package edu.gatech.gtri.trustmark.trpt.controller


import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class OrganizationDashboardController {

    Object manage() {
    }
}
