package edu.gatech.gtri.trustmark.grails.trpt.domain

import org.gtri.fj.data.Option
import org.gtri.fj.function.Effect0
import org.gtri.fj.function.F0

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class File {

    byte[] data

    static constraints = {
        data nullable: true
    }

    static mapping = {
        table 'file'
        data sqlType: 'mediumblob'
    }

    static hasMany = [
            partnerOrganizationCandidateTrustInteroperabilityProfileUriSet: PartnerOrganizationCandidateTrustInteroperabilityProfileUri,
            partnerSystemCandidateSet                                     : PartnerSystemCandidate,
            partnerSystemCandidateTrustInteroperabilityProfileUriSet      : PartnerSystemCandidateTrustInteroperabilityProfileUri,
            trustInteroperabilityProfileUriHistorySet                     : TrustInteroperabilityProfileUriHistory,
            trustInteroperabilityProfileUriSet                            : TrustInteroperabilityProfileUri,
            trustmarkBindingRegistryOrganizationMapUriHistorySet          : TrustmarkBindingRegistryOrganizationMapUriHistory,
            trustmarkBindingRegistryOrganizationMapUriSet                 : TrustmarkBindingRegistryOrganizationMapUri,
            trustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySet : TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory,
            trustmarkBindingRegistryOrganizationTrustmarkMapUriSet        : TrustmarkBindingRegistryOrganizationTrustmarkMapUri,
            trustmarkBindingRegistrySystemMapUriTypeHistorySet            : TrustmarkBindingRegistrySystemMapUriTypeHistory,
            trustmarkBindingRegistrySystemMapUriTypeSet                   : TrustmarkBindingRegistrySystemMapUriType,
            trustmarkDefinitionUriHistorySet                              : TrustmarkDefinitionUriHistory,
            trustmarkDefinitionUriSet                                     : TrustmarkDefinitionUri,
            trustmarkStatusReportUriHistorySet                            : TrustmarkStatusReportUriHistory,
            trustmarkStatusReportUriSet                                   : TrustmarkStatusReportUri,
            trustmarkUriHistorySet                                        : TrustmarkUriHistory,
            trustmarkUriSet                                               : TrustmarkUri,
    ]

    org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriSet) { setPartnerOrganizationCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerOrganizationCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper() { fromNull(getPartnerSystemCandidateTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<PartnerSystemCandidateTrustInteroperabilityProfileUri> nil()) }

    void partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriSet) { setPartnerSystemCandidateTrustInteroperabilityProfileUriSet(new HashSet<>(partnerSystemCandidateTrustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriSetHelper() { fromNull(getTrustInteroperabilityProfileUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUri> nil()) }

    void trustInteroperabilityProfileUriSetHelper(final org.gtri.fj.data.List<TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriSet) { setTrustInteroperabilityProfileUriSet(new HashSet<>(trustInteroperabilityProfileUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustInteroperabilityProfileUriHistory> trustInteroperabilityProfileUriHistorySetHelper() { fromNull(getTrustInteroperabilityProfileUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustInteroperabilityProfileUriHistory> nil()) }

    void trustInteroperabilityProfileUriHistorySetHelper(final org.gtri.fj.data.List<TrustInteroperabilityProfileUriHistory> trustInteroperabilityProfileUriHistorySet) { setTrustInteroperabilityProfileUriHistorySet(new HashSet<>(trustInteroperabilityProfileUriHistorySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> trustmarkBindingRegistryOrganizationMapUriSetHelper() { fromNull(getTrustmarkBindingRegistryOrganizationMapUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationMapUri> nil()) }

    void trustmarkBindingRegistryOrganizationMapUriSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUri> trustmarkBindingRegistryOrganizationMapUriSet) { setTrustmarkBindingRegistryOrganizationMapUriSet(new HashSet<>(trustmarkBindingRegistryOrganizationMapUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUriHistory> trustmarkBindingRegistryOrganizationMapUriHistorySetHelper() { fromNull(getTrustmarkBindingRegistryOrganizationMapUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationMapUriHistory> nil()) }

    void trustmarkBindingRegistryOrganizationMapUriHistorySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationMapUriHistory> trustmarkBindingRegistryOrganizationMapUriHistorySet) { setTrustmarkBindingRegistryOrganizationMapUriHistorySet(new HashSet<>(trustmarkBindingRegistryOrganizationMapUriHistorySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> trustmarkBindingRegistryOrganizationTrustmarkMapUriSetHelper() { fromNull(getTrustmarkBindingRegistryOrganizationTrustmarkMapUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> nil()) }

    void trustmarkBindingRegistryOrganizationTrustmarkMapUriSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUri> trustmarkBindingRegistryOrganizationTrustmarkMapUriSet) { setTrustmarkBindingRegistryOrganizationTrustmarkMapUriSet(new HashSet<>(trustmarkBindingRegistryOrganizationTrustmarkMapUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> trustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySetHelper() { fromNull(getTrustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> nil()) }

    void trustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> trustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySet) { setTrustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySet(new HashSet<>(trustmarkBindingRegistryOrganizationTrustmarkMapUriHistorySet.toJavaList())) }
    
    org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> PartnerSystemCandidateSetHelper() { fromNull(getPartnerSystemCandidateSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> nil()) }

    void PartnerSystemCandidateSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistryOrganizationTrustmarkMapUriHistory> PartnerSystemCandidateSet) { setPartnerSystemCandidateSet(new HashSet<>(PartnerSystemCandidateSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriType> trustmarkBindingRegistrySystemMapUriTypeSetHelper() { fromNull(getTrustmarkBindingRegistrySystemMapUriTypeSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistrySystemMapUriType> nil()) }

    void trustmarkBindingRegistrySystemMapUriTypeSetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriType> trustmarkBindingRegistrySystemMapUriTypeSet) { setTrustmarkBindingRegistrySystemMapUriTypeSet(new HashSet<>(trustmarkBindingRegistrySystemMapUriTypeSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriTypeHistory> trustmarkBindingRegistrySystemMapUriTypeHistorySetHelper() { fromNull(getTrustmarkBindingRegistrySystemMapUriTypeHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkBindingRegistrySystemMapUriTypeHistory> nil()) }

    void trustmarkBindingRegistrySystemMapUriTypeHistorySetHelper(final org.gtri.fj.data.List<TrustmarkBindingRegistrySystemMapUriTypeHistory> trustmarkBindingRegistrySystemMapUriTypeHistorySet) { setTrustmarkBindingRegistrySystemMapUriTypeHistorySet(new HashSet<>(trustmarkBindingRegistrySystemMapUriTypeHistorySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkDefinitionUriHistory> trustmarkDefinitionUriHistorySetHelper() { fromNull(getTrustmarkDefinitionUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkDefinitionUriHistory> nil()) }

    void trustmarkDefinitionUriHistorySetHelper(final org.gtri.fj.data.List<TrustmarkDefinitionUriHistory> trustmarkDefinitionUriHistorySet) { setTrustmarkDefinitionUriHistorySet(new HashSet<>(trustmarkDefinitionUriHistorySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkDefinitionUri> trustmarkDefinitionUriSetHelper() { fromNull(getTrustmarkDefinitionUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkDefinitionUri> nil()) }

    void trustmarkDefinitionUriSetHelper(final org.gtri.fj.data.List<TrustmarkDefinitionUri> trustmarkDefinitionUriSet) { setTrustmarkDefinitionUriSet(new HashSet<>(trustmarkDefinitionUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkStatusReportUri> trustmarkStatusReportUriSetHelper() { fromNull(getTrustmarkStatusReportUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkStatusReportUri> nil()) }

    void trustmarkStatusReportUriSetHelper(final org.gtri.fj.data.List<TrustmarkStatusReportUri> trustmarkStatusReportUriSet) { setTrustmarkStatusReportUriSet(new HashSet<>(trustmarkStatusReportUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkStatusReportUriHistory> trustmarkStatusReportUriHistorySetHelper() { fromNull(getTrustmarkStatusReportUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkStatusReportUriHistory> nil()) }

    void trustmarkStatusReportUriHistorySetHelper(final org.gtri.fj.data.List<TrustmarkStatusReportUriHistory> trustmarkStatusReportUriHistorySet) { setTrustmarkStatusReportUriHistorySet(new HashSet<>(trustmarkStatusReportUriHistorySet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkUri> trustmarkUriSetHelper() { fromNull(getTrustmarkUriSet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkUri> nil()) }

    void trustmarkUriSetHelper(final org.gtri.fj.data.List<TrustmarkUri> trustmarkUriSet) { setTrustmarkUriSet(new HashSet<>(trustmarkUriSet.toJavaList())) }

    org.gtri.fj.data.List<TrustmarkUriHistory> trustmarkUriHistorySetHelper() { fromNull(getTrustmarkUriHistorySet()).map({ collection -> iterableList(collection) }).orSome(org.gtri.fj.data.List.<TrustmarkUriHistory> nil()) }

    void trustmarkUriHistorySetHelper(final org.gtri.fj.data.List<TrustmarkUriHistory> trustmarkUriHistorySet) { setTrustmarkUriHistorySet(new HashSet<>(trustmarkUriHistorySet.toJavaList())) }

    Long idHelper() {
        id
    }

    void deleteHelper() {
        delete(failOnError: true);
    }

    void deleteAndFlushHelper() {
        delete(flush: true, failOnError: true)
    }

    File saveHelper() {
        save(failOnError: true);
    }

    File saveAndFlushHelper() {
        save(flush: true, failOnError: true)
    }

    static final <T> T withTransactionHelper(final F0<T> f0) {
        return withTransaction({ return f0.f() })
    }

    static final void withTransactionHelper(final Effect0 effect0) {
        withTransaction({ return effect0.f() })
    }

    static Option<File> findByIdHelper(final long id) {
        fromNull(findById(id))
    }

    static org.gtri.fj.data.List<File> findAllHelper() {
        fromNull(findAll())
                .map({ collection -> iterableList(collection) })
                .orSome(org.gtri.fj.data.List.<File> nil());
    }
}
