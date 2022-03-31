package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryDeleteAllRequest
import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryInsertRequest
import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryService
import edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUpdateRequest
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
class TrustmarkBindingRegistryController {

    TrustmarkBindingRegistryService trustmarkBindingRegistryService

    Object manage() {
    }

    Object findAll(final TrustmarkBindingRegistryFindAllRequest trustmarkBindingRegistryFindAllRequest) {

        P2<Object, Integer> response = toResponse(trustmarkBindingRegistryService.findAll(((GrailsUser) getPrincipal()).username, trustmarkBindingRegistryFindAllRequest).map(list -> list.toJavaList()))

        respond response._1(), status: response._2()
    }

    Object findOne(final TrustmarkBindingRegistryFindOneRequest trustmarkBindingRegistryFindOneRequest) {

        P2<Object, Integer> response = toResponse(trustmarkBindingRegistryService.findOne(((GrailsUser) getPrincipal()).username, trustmarkBindingRegistryFindOneRequest))

        respond response._1(), status: response._2()
    }

    Object insert(final TrustmarkBindingRegistryInsertRequest trustmarkBindingRegistryInsertRequest) {

        P2<Object, Integer> response = toResponse(trustmarkBindingRegistryService.insert(((GrailsUser) getPrincipal()).username, trustmarkBindingRegistryInsertRequest))

        respond response._1(), status: response._2()
    }

    Object update(final TrustmarkBindingRegistryUpdateRequest trustmarkBindingRegistryUpdateRequest) {

        P2<Object, Integer> response = toResponse(trustmarkBindingRegistryService.update(((GrailsUser) getPrincipal()).username, trustmarkBindingRegistryUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object delete(final TrustmarkBindingRegistryDeleteAllRequest trustmarkBindingRegistryDeleteAllRequest) {

        P2<Object, Integer> response = toResponse(trustmarkBindingRegistryService.delete(((GrailsUser) getPrincipal()).username, trustmarkBindingRegistryDeleteAllRequest))

        respond response._1(), status: response._2()
    }
}
