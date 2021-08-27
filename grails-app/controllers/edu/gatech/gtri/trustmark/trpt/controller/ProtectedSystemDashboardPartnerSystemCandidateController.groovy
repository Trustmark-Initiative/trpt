package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemPartnerSystemCandidateFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.protectedSystem.ProtectedSystemService
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class ProtectedSystemDashboardPartnerSystemCandidateController {

    ProtectedSystemService protectedSystemService

    Object manage(final ProtectedSystemPartnerSystemCandidateFindOneRequest protectedSystemPartnerSystemCandidateFindOneRequest) {

        final P2<Object, Integer> response = toResponse(protectedSystemService.findOne(protectedSystemPartnerSystemCandidateFindOneRequest))

        [protectedSystemPartnerSystemCandidate: response._1()]
    }
}
