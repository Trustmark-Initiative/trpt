package edu.gatech.gtri.trustmark.grails.trpt.service.partnerOrganizationCandidate;

import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidate;

import static edu.gatech.gtri.trustmark.grails.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.trustmarkBindingRegistryResponse;

public final class PartnerOrganizationCandidateUtility {

    public static PartnerOrganizationCandidateResponse partnerOrganizationCandidateResponse(final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return new PartnerOrganizationCandidateResponse(
                partnerOrganizationCandidate.idHelper(),
                partnerOrganizationCandidate.getIdentifier(),
                partnerOrganizationCandidate.getName(),
                partnerOrganizationCandidate.getNameLong(),
                partnerOrganizationCandidate.getDescription(),
                partnerOrganizationCandidate.getRequestLocalDateTime(),
                partnerOrganizationCandidate.getSuccessLocalDateTime(),
                partnerOrganizationCandidate.getFailureLocalDateTime(),
                partnerOrganizationCandidate.getFailureMessage(),
                partnerOrganizationCandidate.trustmarkBindingRegistryOrganizationMapUriHelper().trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper().isEmpty() ?
                        null :
                        trustmarkBindingRegistryResponse(partnerOrganizationCandidate.trustmarkBindingRegistryOrganizationMapUriHelper().trustmarkBindingRegistryUriHelper().trustmarkBindingRegistrySetHelper().head()));
    }
}
