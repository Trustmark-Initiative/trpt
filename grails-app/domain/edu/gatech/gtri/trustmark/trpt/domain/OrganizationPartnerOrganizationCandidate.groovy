package edu.gatech.gtri.trustmark.trpt.domain

import grails.compiler.GrailsCompileStatic
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

@GrailsCompileStatic
class OrganizationPartnerOrganizationCandidate {

    boolean trust

    static constraints = {
        trust nullable: true
    }

    static mapping = {
        table 'organization_partner_organization_candidate'
    }

    static belongsTo = [
            partnerOrganizationCandidate: PartnerOrganizationCandidate,
            organization                : Organization
    ]

    PartnerOrganizationCandidate partnerOrganizationCandidateHelper() { getPartnerOrganizationCandidate() }

    void partnerOrganizationCandidateHelper(final PartnerOrganizationCandidate partnerOrganizationCandidate) { setPartnerOrganizationCandidate(partnerOrganizationCandidate) }

    Organization organizationHelper() { getOrganization() }

    void organizationHelper(final Organization organization) { setOrganization(organization) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    OrganizationPartnerOrganizationCandidate saveHelper() {
        save(failOnError: true)
    }

    OrganizationPartnerOrganizationCandidate saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<OrganizationPartnerOrganizationCandidate> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<OrganizationPartnerOrganizationCandidate> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<OrganizationPartnerOrganizationCandidate> nil());
    }

    static Integer executeUpdateHelper(final String query) {
        executeUpdate(query);
    }
}
