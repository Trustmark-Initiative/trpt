package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemDeleteAllRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemInsertRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemService
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUpdateRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystemType.ProtectedSystemTypeFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystemType.ProtectedSystemTypeService
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
@GrailsCompileStatic
class ProtectedSystemController {

    ProtectedSystemService protectedSystemService
    ProtectedSystemTypeService protectedSystemTypeService

    Object manage() {
    }

    Object findAll(final ProtectedSystemFindAllRequest protectedSystemServiceFindAllRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.findAll(((GrailsUser) getPrincipal()).username, protectedSystemServiceFindAllRequest).map(list -> list.toJavaList()))

        respond response._1(), status: response._2()
    }

    Object findOne(final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.findOne(((GrailsUser) getPrincipal()).username, protectedSystemFindOneRequest))

        respond response._1(), status: response._2()
    }

    Object insert(final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.insert(((GrailsUser) getPrincipal()).username, protectedSystemInsertRequest))

        respond response._1(), status: response._2()
    }

    Object update(final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.update(((GrailsUser) getPrincipal()).username, protectedSystemUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object delete(final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.delete(((GrailsUser) getPrincipal()).username, protectedSystemDeleteAllRequest))

        respond response._1(), status: response._2()
    }

    Object typeFindAll(final ProtectedSystemTypeFindAllRequest protectedSystemTypeFindAllRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemTypeService.findAll(((GrailsUser) getPrincipal()).username, protectedSystemTypeFindAllRequest).map(list -> list.toJavaList()))

        respond response._1(), status: response._2()
    }
}
