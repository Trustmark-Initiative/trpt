package edu.gatech.gtri.trustmark.grails.trpt.controller

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.security.access.prepost.PreAuthorize

@CompileStatic
@Transactional
@GrailsCompileStatic
class ProtectedSystemDashboardController {

    Object manage() {
    }
}
