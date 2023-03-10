package edu.gatech.gtri.trustmark.grails.trpt.controller

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.security.access.prepost.PreAuthorize

/**
 * TODO: Remove this, if possible; go directly to controller: "protectedSystem", action: "manage".
 */
@CompileStatic
@Transactional
@GrailsCompileStatic
class IndexController {

    Object index() {
        redirect(controller: "protectedSystem", action: "manage")
    }
}
