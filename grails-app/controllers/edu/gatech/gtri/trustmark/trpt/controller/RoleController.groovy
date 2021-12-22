package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.role.RoleFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.role.RoleService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class RoleController {

    RoleService roleService

    Object manage() {
    }

    Object findAll(final RoleFindAllRequest roleFindAllRequest) {

        P2<Object, Integer> response = toResponse(roleService.findAll(((GrailsUser) getPrincipal()).username, roleFindAllRequest).map(list -> list.toJavaList()))

        respond response._1(), status: response._2()
    }
}
