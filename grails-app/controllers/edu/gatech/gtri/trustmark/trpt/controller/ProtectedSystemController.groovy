package edu.gatech.gtri.trustmark.trpt.controller


import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemDeleteAllRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemInsertRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemService
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemUpdateRequest
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class ProtectedSystemController {

    ProtectedSystemService protectedSystemService

    Object manage() {
    }

    Object findAll(final ProtectedSystemFindAllRequest protectedSystemServiceFindAllRequest) {

        respond protectedSystemService.findAll(protectedSystemServiceFindAllRequest).toJavaList()
    }

    Object findOne(final ProtectedSystemFindOneRequest protectedSystemFindOneRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.findOne(protectedSystemFindOneRequest))

        respond response._1(), status: response._2()
    }

    Object insert(final ProtectedSystemInsertRequest protectedSystemInsertRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.insert(protectedSystemInsertRequest))

        respond response._1(), status: response._2()
    }

    Object update(final ProtectedSystemUpdateRequest protectedSystemUpdateRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.update(protectedSystemUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object delete(final ProtectedSystemDeleteAllRequest protectedSystemDeleteAllRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.delete(protectedSystemDeleteAllRequest))

        respond response._1(), status: response._2()
    }

    Object typeFindAll() {

        respond protectedSystemService.typeFindAll().toJavaList()
    }
}
