package edu.gatech.gtri.trustmark.trpt.controller

import edu.gatech.gtri.trustmark.trpt.service.profile.ProfileService
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2
import org.springframework.security.core.userdetails.User

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@GrailsCompileStatic
class ProfileController {

    ProfileService profileService

    @Secured('permitAll()')
    Object findOne() {

        P2<Object, Integer> response = toResponse(profileService.findOne(((User) getPrincipal())?.username))

        respond response._1(), status: response._2()
    }
}
