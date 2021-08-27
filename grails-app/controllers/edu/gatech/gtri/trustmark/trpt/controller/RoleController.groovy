package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.role.RoleFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.role.RoleService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class RoleController {

    RoleService roleService

    Object manage() {
    }

    Object findAll(final RoleFindAllRequest roleFindAllRequest) {

        respond roleService.findAll(roleFindAllRequest).toJavaList()
    }
}
