package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Evaluation;
import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.EvaluationResponseUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.partnerOrganizationCandidate.PartnerOrganizationCandidateUtility;
import org.gtri.fj.data.List;

import static org.gtri.fj.data.List.arrayList;

public final class OrganizationResponseUtility {

    private OrganizationResponseUtility() {
    }

    public static OrganizationResponse organizationResponse(
            final Organization organization) {
        return new OrganizationResponse(
                organization.idHelper(),
                organization.getName(),
                organization.getDescription(),
                organization.getUri());
    }

    public static OrganizationResponseWithDetail organizationResponseWithDetail(
            final Organization organization) {

        return new OrganizationResponseWithDetail(
                organization.idHelper(),
                organization.getName(),
                organization.getDescription(),
                organization.getUri(),
                organization
                        .organizationTrustInteroperabilityProfileUriSetHelper()
                        .map(EvaluationResponseUtility::entityTrustInteroperabilityProfileResponse)
                        .sort(ProtectedEntityTrustInteroperabilityProfileResponse.protectedEntityTrustInteroperabilityProfileResponseOrd)
                        .toJavaList(),
                PartnerOrganizationCandidate
                        .findAllByOrganizationInHelper(arrayList(organization))
                        .map(partnerOrganizationCandidate -> EvaluationResponseUtility.entityPartnerCandidateResponse(
                                organization,
                                partnerOrganizationCandidate,
                                PartnerOrganizationCandidateUtility::partnerOrganizationCandidateResponse,
                                OrganizationResponseUtility::evaluationList))
                        .sort(ProtectedEntityPartnerCandidateResponse.protectedEntityPartnerCandidateResponseOrd)
                        .toJavaList());
    }

    public static OrganizationResponseWithTrustExpressionEvaluation organizationResponseWithTrustExpressionEvaluation(
            final Organization organization,
            final PartnerOrganizationCandidate partnerOrganizationCandidate) {

        return new OrganizationResponseWithTrustExpressionEvaluation(
                organizationResponse(organization),
                organization.idHelper(),
                organization.getName(),
                organization
                        .organizationTrustInteroperabilityProfileUriSetHelper()
                        .map(EvaluationResponseUtility::entityTrustInteroperabilityProfileResponse)
                        .sort(ProtectedEntityTrustInteroperabilityProfileResponse.protectedEntityTrustInteroperabilityProfileResponseOrd)
                        .toJavaList(),
                List.arrayList(EvaluationResponseUtility.entityPartnerCandidateResponseWithTrustExpressionEvaluation(
                        organization,
                        partnerOrganizationCandidate,
                        PartnerOrganizationCandidateUtility::partnerOrganizationCandidateResponse,
                        OrganizationResponseUtility::evaluationList)).toJavaList());
    }

    private static List<Evaluation<PartnerOrganizationCandidate>> evaluationList(
            final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri,
            final PartnerCandidate partnerCandidate) {

        return trustInteroperabilityProfileUri
                .partnerOrganizationCandidateTrustInteroperabilityProfileUriSetHelper()
                .filter(partnerOrganizationCandidateTrustInteroperabilityProfileUri -> partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper().idHelper() == partnerCandidate.idHelper())
                .map(evaluation -> evaluation);
    }
}
