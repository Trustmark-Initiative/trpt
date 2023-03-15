package edu.gatech.gtri.trustmark.grails.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class TrustmarkBindingRegistryUri {

    String uri

    static constraints = {
        uri nullable: true
    }

    static mapping = {
        table 'trustmark_binding_registry_uri'
        uri length: 1000
    }

    static hasMany = [
            trustmarkBindingRegistrySet                  : TrustmarkBindingRegistry,
            trustmarkBindingRegistrySystemMapUriTypeSet  : TrustmarkBindingRegistrySystemMapUriType,
            trustmarkBindingRegistryOrganizationMapUriSet: TrustmarkBindingRegistryOrganizationMapUri
    ]

    org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySetHelper() { fromNull(getTrustmarkBindingRegistrySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistry> nil()) }

    void trustmarkBindingRegistrySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistry> trustmarkBindingRegistrySet) { setTrustmarkBindingRegistrySet(new HashSet<>(trustmarkBindingRegistrySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriType> trustmarkBindingRegistrySystemMapUriTypeSetHelper() { fromNull(getTrustmarkBindingRegistrySystemMapUriTypeSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistrySystemMapUriType> nil()) }

    void trustmarkBindingRegistrySystemMapUriTypeSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriType> trustmarkBindingRegistrySystemMapUriTypeSet) { this.setTrustmarkBindingRegistrySystemMapUriTypeSet(new HashSet<>(trustmarkBindingRegistrySystemMapUriTypeSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> trustmarkBindingRegistryOrganizationMapUriSetHelper() { fromNull(getTrustmarkBindingRegistryOrganizationMapUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationMapUri> nil()) }

    void trustmarkBindingRegistryOrganizationMapUriSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> trustmarkBindingRegistryOrganizationMapUriSet) { this.setTrustmarkBindingRegistryOrganizationMapUriSet(new HashSet<>(trustmarkBindingRegistryOrganizationMapUriSet.toJavaList())) }

    static final org.gtri.fj.data.List<TrustmarkBindingRegistryUri> findAllByOrderByUriAscHelper() {

        fromNull(findAll(sort: 'uri', order: 'asc'))
                .map({ collection -> iterableList((Iterable<TrustmarkBindingRegistryUri>) collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUri> nil());
    }

    static final Option<TrustmarkBindingRegistryUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustmarkBindingRegistryUri saveHelper() {
        save(failOnError: true)
    }

    TrustmarkBindingRegistryUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustmarkBindingRegistryUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustmarkBindingRegistryUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryUri> nil());
    }
}
