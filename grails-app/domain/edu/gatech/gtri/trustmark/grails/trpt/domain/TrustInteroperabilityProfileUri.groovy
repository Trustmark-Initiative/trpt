package edu.gatech.gtri.trustmark.grails.trpt.domain

import grails.gorm.PagedResultList
import org.gtri.fj.data.Either
import org.gtri.fj.data.List
import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.Effect2
import org.gtri.fj.function.F0
import org.gtri.fj.function.F2

import java.time.LocalDateTime

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull
import static org.gtri.fj.data.Option.somes

class TrustInteroperabilityProfileUri implements Uri {

    String uri
    String name
    String description
    LocalDateTime publicationLocalDateTime
    String issuerName
    String issuerIdentifier
    String hash
    File document
    LocalDateTime documentRequestLocalDateTime
    LocalDateTime documentSuccessLocalDateTime
    LocalDateTime documentFailureLocalDateTime
    LocalDateTime documentChangeLocalDateTime
    String documentFailureMessage
    LocalDateTime serverRequestLocalDateTime
    LocalDateTime serverSuccessLocalDateTime
    LocalDateTime serverFailureLocalDateTime
    LocalDateTime serverChangeLocalDateTime
    String serverFailureMessage

    static constraints = {
        uri nullable: true
        name nullable: true
        description nullable: true
        publicationLocalDateTime nullable: true
        issuerName nullable: true
        issuerIdentifier nullable: true
        hash nullable: true
        document nullable: true
        documentRequestLocalDateTime nullable: true
        documentSuccessLocalDateTime nullable: true
        documentFailureLocalDateTime nullable: true
        documentChangeLocalDateTime nullable: true
        documentFailureMessage nullable: true
        serverRequestLocalDateTime nullable: true
        serverSuccessLocalDateTime nullable: true
        serverFailureLocalDateTime nullable: true
        serverChangeLocalDateTime nullable: true
        serverFailureMessage nullable: true
    }

    static mapping = {
        table 'trust_interoperability_profile_uri'
        uri length: 1000
        name length: 1000
        description length: 1000
        issuerName length: 1000
        issuerIdentifier length: 1000
        hash length: 1000
        documentFailureMessage length: 1000
        serverFailureMessage length: 1000
    }

    static hasMany = [
            partnerSystemCandidateTrustInteroperabilityProfileUriSet      : PartnerSystemCandidateTrustInteroperabilityProfileUri,
            partnerOrganizationCandidateTrustInteroperabilityProfileUriSet: PartnerOrganizationCandidateTrustInteroperabilityProfileUri,
            protectedSystemTrustInteroperabilityProfileUriSet             : ProtectedSystemTrustInteroperabilityProfileUri,
            organizationTrustInteroperabilityProfileUriSet                : OrganizationTrustInteroperabilityProfileUri
    ]

    File fileHelper() { getDocument() }

    void fileHelper(final File file) { setDocument(file) }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSet) { setPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerOrganizationCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSetHelper() { fromNull(getProtectedSystemTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<ProtectedSystemTrustInteroperabilityProfileUri> nil()) }

    void protectedSystemTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<ProtectedSystemTrustInteroperabilityProfileUri> protectedSystemTrustInteroperabilityProfileUriSet) { setProtectedSystemTrustInteroperabilityProfileUriSet(new HashSet<>(protectedSystemTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSetHelper() { fromNull(getOrganizationTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<OrganizationTrustInteroperabilityProfileUri> nil()) }

    void organizationTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<OrganizationTrustInteroperabilityProfileUri> organizationTrustInteroperabilityProfileUriSet) { setOrganizationTrustInteroperabilityProfileUriSet(new HashSet<>(organizationTrustInteroperabilityProfileUriSet.toJavaList())) }

    static final Option<TrustInteroperabilityProfileUri> findByUriHelper(final String uri) { fromNull(findByUri(uri)) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    TrustInteroperabilityProfileUri saveHelper() {
        save(failOnError: true)
    }

    TrustInteroperabilityProfileUri saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<TrustInteroperabilityProfileUri> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<TrustInteroperabilityProfileUri> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUri> nil());
    }

    static PagedResultList<TrustInteroperabilityProfileUri> findAllHelper(final long offset, final long max) {
        fromNull((PagedResultList<TrustInteroperabilityProfileUri>) TrustInteroperabilityProfileUri.createCriteria().list(offset: offset, max: max) {})
                .orSome(new PagedResultList<TrustInteroperabilityProfileUri>(null));
    }

    static TrustInteroperabilityProfileUri coalesce(
            final TrustInteroperabilityProfileUri uri,
            final TrustInteroperabilityProfileUri uriRemove) {

        set(
                uri,
                uriRemove,
                split(
                        uri.partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(),
                        uriRemove.partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(),
                        (element, elementInner) -> element.partnerSystemCandidateHelper().idHelper() == elementInner.partnerSystemCandidateHelper().idHelper()),
                (PartnerSystemCandidateTrustInteroperabilityProfileUri child, TrustInteroperabilityProfileUri parent) -> child.trustInteroperabilityProfileUriHelper(parent),
                (TrustInteroperabilityProfileUri parent, List<PartnerSystemCandidateTrustInteroperabilityProfileUri> childList) -> parent.partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(childList));

        set(
                uri,
                uriRemove,
                split(
                        uri.partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(),
                        uriRemove.partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(),
                        (element, elementInner) -> element.partnerOrganizationCandidateHelper().idHelper() == elementInner.partnerOrganizationCandidateHelper().idHelper()),
                (PartnerOrganizationCandidateTrustInteroperabilityProfileUri child, TrustInteroperabilityProfileUri parent) -> child.trustInteroperabilityProfileUriHelper(parent),
                (TrustInteroperabilityProfileUri parent, List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> childList) -> parent.partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(childList));

        set(
                uri,
                uriRemove,
                split(
                        uri.protectedSystemTrustInteroperabilityProfileUriSetHelper(),
                        uriRemove.protectedSystemTrustInteroperabilityProfileUriSetHelper(),
                        (element, elementInner) -> element.protectedSystemHelper().idHelper() == elementInner.protectedSystemHelper().idHelper()),
                (ProtectedSystemTrustInteroperabilityProfileUri child, TrustInteroperabilityProfileUri parent) -> ProtectedSystemTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper,
                (TrustInteroperabilityProfileUri parent, List<ProtectedSystemTrustInteroperabilityProfileUri> childList) -> TrustInteroperabilityProfileUri::protectedSystemTrustInteroperabilityProfileUriSetHelper);

        set(
                uri,
                uriRemove,
                split(
                        uri.organizationTrustInteroperabilityProfileUriSetHelper(),
                        uriRemove.organizationTrustInteroperabilityProfileUriSetHelper(),
                        (element, elementInner) -> element.organizationHelper().idHelper() == elementInner.organizationHelper().idHelper()),
                (OrganizationTrustInteroperabilityProfileUri child, TrustInteroperabilityProfileUri parent) -> OrganizationTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper,
                (TrustInteroperabilityProfileUri parent, List<OrganizationTrustInteroperabilityProfileUri> childList) -> TrustInteroperabilityProfileUri::organizationTrustInteroperabilityProfileUriSetHelper);

        uriRemove.deleteHelper();

        return uri.saveAndFlushHelper();
    }

    private static <T1> List<Either<T1, T1>> split(List<T1> list, List<T1> listRemove, F2<T1, T1, Boolean> exists) {
        return listRemove
                .map(element ->
                        list.exists(elementInner -> exists.f(element, elementInner)) ?
                                Either.<T1, T1> left(element) :
                                Either.<T1, T1> right(element));
    }

    private static <T1> void set(
            final TrustInteroperabilityProfileUri uri,
            final TrustInteroperabilityProfileUri uriRemove,
            final List<Either<T1, T1>> list,
            final Effect2<T1, TrustInteroperabilityProfileUri> setParent,
            final Effect2<TrustInteroperabilityProfileUri, List<T1>> setChildList) {

        list.forEach(either -> either.right().forEach(right -> setParent.f(right, uri)));
        setChildList.f(uriRemove, somes(list.map(either -> either.left().toOption())));
        setChildList.f(uri, somes(list.map(either -> either.right().toOption())));
    }
}
