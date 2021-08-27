package edu.gatech.gtri.trustmark.trpt.controller


import edu.gatech.gtri.trustmark.trpt.service.user.UserDeleteAllRequest
import edu.gatech.gtri.trustmark.trpt.service.user.UserFindAllRequest
import edu.gatech.gtri.trustmark.trpt.service.user.UserFindOneRequest
import edu.gatech.gtri.trustmark.trpt.service.user.UserInsertRequest
import edu.gatech.gtri.trustmark.trpt.service.user.UserService
import edu.gatech.gtri.trustmark.trpt.service.user.UserUpdateRequest
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.gtri.fj.product.P2

import static edu.gatech.gtri.trustmark.trpt.controller.ResponseUtility.toResponse

@CompileStatic
@Transactional
@Secured('hasAnyRole("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR_ORGANIZATION")')
class UserController {

    UserService userService

    Object manage() {
    }

    Object findAll(final UserFindAllRequest userFindAllRequest) {

        respond userService.findAll(userFindAllRequest).toJavaList()
    }

    Object findOne(final UserFindOneRequest userFindOneRequest) {

        P2<Object, Integer> response = toResponse(userService.findOne(userFindOneRequest))

        respond response._1(), status: response._2()
    }

    Object insert(final UserInsertRequest userInsertRequest) {

        P2<Object, Integer> response = toResponse(userService.insert(userInsertRequest))

        respond response._1(), status: response._2()
    }

    Object update(final UserUpdateRequest userUpdateRequest) {

        P2<Object, Integer> response = toResponse(userService.update(userUpdateRequest))

        respond response._1(), status: response._2()
    }

    Object delete(final UserDeleteAllRequest userDeleteAllRequest) {

        P2<Object, Integer> response = toResponse(userService.delete(userDeleteAllRequest))

        respond response._1(), status: response._2()
    }
}
