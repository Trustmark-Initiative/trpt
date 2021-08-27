package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationDeleteAllRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationInsertRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationService
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationUpdateRequest
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class OrganizationController {

    OrganizationService organizationService

    Object manage() {
    }

    Object findAll(final OrganizationFindAllRequest organizationFindAllRequest) {

        respond organizationService.findAll(organizationFindAllRequest).toJavaList()
    }

    Object findOne(final OrganizationFindOneRequest organizationFindOneRequest) {

        P2<Object, Integer> response = toResponse(organizationService.findOne(organizationFindOneRequest))

        respond response._1(), status: response._2()
    }

    Object insert(final OrganizationInsertRequest organizationInsertRequest) {

        P2<Object, Integer> response = toResponse(organizationService.insert(organizationInsertRequest))

        respond response._1(), status: response._2()
    }

    Object update(final OrganizationUpdateRequest organizationUpdateRequest) {

        P2<Object, Integer> response = toResponse(organizationService.update(organizationUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object delete(final OrganizationDeleteAllRequest organizationDeleteAllRequest) {

        P2<Object, Integer> response = toResponse(organizationService.delete(organizationDeleteAllRequest))

        respond response._1(), status: response._2()
    }

}
