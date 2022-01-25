package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.domain.OrganizationPartnerOrganizationCandidate
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationPartnerOrganizationCandidateFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationService
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemPartnerSystemCandidateFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class OrganizationDashboardPartnerOrganizationCandidateController {

    OrganizationService organizationService

    Object manage(final OrganizationPartnerOrganizationCandidateFindOneRequest organizationPartnerOrganizationCandidateFindOneRequest) {

        final P2<Object, Integer> response = toResponse(organizationService.findOne(((GrailsUser) getPrincipal()).username, organizationPartnerOrganizationCandidateFindOneRequest))

        [organization: response._1()]
    }
}
