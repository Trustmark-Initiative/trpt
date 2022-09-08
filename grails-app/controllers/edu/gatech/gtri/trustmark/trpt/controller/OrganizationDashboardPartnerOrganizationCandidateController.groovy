package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationPartnerOrganizationCandidateFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationService
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
class OrganizationDashboardPartnerOrganizationCandidateController {

    OrganizationService organizationService

    Object manage() {
    }

    Object findOne(final OrganizationPartnerOrganizationCandidateFindOneRequest organizationPartnerOrganizationCandidateFindOneRequest) {

        final P2<Object, Integer> response = toResponse(organizationService.findOne(((GrailsUser) getPrincipal()).username, organizationPartnerOrganizationCandidateFindOneRequest))

        respond response._1(), status: response._2()
    }
}
