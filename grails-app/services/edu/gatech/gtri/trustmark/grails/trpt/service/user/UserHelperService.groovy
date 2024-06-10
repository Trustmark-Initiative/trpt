package edu.gatech.gtri.trustmark.grails.trpt.service.user

import grails.gorm.transactions.Transactional
import org.gtri.fj.function.F0

@Transactional
class UserHelperService {

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }
}
