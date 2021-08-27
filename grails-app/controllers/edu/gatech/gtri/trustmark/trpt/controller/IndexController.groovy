package edu.gatech.gtri.trustmark.trpt.controller

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic

/**
 * TODO: Remove this, if possible; go directly to controller: "protectedSystem", action: "manage".
 */
@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class IndexController {

    Object index() {
        redirect(controller: "protectedSystem", action: "manage")
    }
}
