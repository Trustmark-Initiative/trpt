package edu.gatech.gtri.trustmark.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.List.nil
import static org.gtri.fj.data.Option.fromNull

class TrustmarkBindingRegistryUri {

    String uri

    static constraints = {
        uri nullable: true
    }

    static hasMany = [
            trustmarkBindingRegistrySet: TrustmarkBindingRegistry,
            trustmarkBindingRegistryUriTypeSet: TrustmarkBindingRegistryUriType
    ]

    static mapping = {
        table 'trustmark_binding_registry_uri'
        uri length: 1000
    }

    long idHelper() { id }

    org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySetHelper() { fromNull(getTrustmarkBindingRegistrySet()).map({ set -> iterableList(set) }).orSome(nil()) }

    void trustmarkBindingRegistrySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySet) { setTrustmarkBindingRegistrySet(new HashSet<>(trustmarkBindingRegistrySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryUriType> trustmarkBindingRegistryUriTypeSetHelper() { fromNull(getTrustmarkBindingRegistryUriTypeSet()).map({ set -> iterableList(set) }).orSome(nil()) }

    void trustmarkBindingRegistryUriTypeSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryUriType> trustmarkBindingRegistryUriTypeSet) { setTrustmarkBindingRegistryUriTypeSet(new HashSet<>(trustmarkBindingRegistryUriTypeSet.toJavaList())) }

    void deleteHelper() { delete(failOnError: true) }

    void deleteAndFlushHelper() { delete(flush: true, failOnError: true) }

    TrustmarkBindingRegistryUri saveHelper() { save(failOnError: true) }

    TrustmarkBindingRegistryUri saveAndFlushHelper() { save(flush: true, failOnError: true) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryUri> findAllHelper() {

        fromNull(findAll())
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUri> nil());
    }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryUri> findAllByOrderByUriAscHelper() {

        fromNull(findAll(sort: 'uri', order: 'asc'))
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUri> nil());
    }

    static final Option<TrustmarkBindingRegistryUri> findByIdHelper(long id) { fromNull(findById(id)) }

    static final Option<TrustmarkBindingRegistryUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }
}
