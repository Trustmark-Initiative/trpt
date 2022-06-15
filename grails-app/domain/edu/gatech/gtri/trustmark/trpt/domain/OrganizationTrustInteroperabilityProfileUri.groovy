package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class OrganizationTrustInteroperabilityProfileUri {

    boolean mandatory

    static constraints = {
        mandatory nullable: true
    }

    static mapping = {
        table 'organization_trust_interoperability_profile_uri'
    }

    static belongsTo = [
            organization                   : Organization,
            trustInteroperabilityProfileUri: TrustInteroperabilityProfileUri
    ]

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    TrustInteroperabilityProfileUri trustInteroperabilityProfileUriHelper() { getTrustInteroperabilityProfileUri() }

    void trustInteroperabilityProfileUriHelper(final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) { setTrustInteroperabilityProfileUri(trustInteroperabilityProfileUri) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    OrganizationTrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    OrganizationTrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<OrganizationTrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<OrganizationTrustInteroperabilityProfileUri> nil());
    }
}
