package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Evaluation;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystemType;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.EvaluationResponseUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityTrustInteroperabilityProfileResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate.PartnerSystemCandidateUtility;
import org.gtri.fj.data.List;

import static edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationResponseUtility.organizationResponse;
import static org.gtri.fj.data.List.arrayList;

public final class ProtectedSystemResponseUtility {
    private ProtectedSystemResponseUtility() {
    }

    public static ProtectedSystemTypeResponse protectedSystemTypeResponse(
            final ProtectedSystemType protectedSystemType) {

        return new ProtectedSystemTypeResponse(
                protectedSystemType.name(),
                protectedSystemType.getName());
    }

    public static ProtectedSystemResponse protectedSystemResponse(
            final ProtectedSystem protectedSystem) {

        return new ProtectedSystemResponse(
                organizationResponse(protectedSystem.organizationHelper()),
                protectedSystem.idHelper(),
                protectedSystemTypeResponse(protectedSystem.getType()),
                protectedSystem.getName(),
                protectedSystem
                        .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                        .map(EvaluationResponseUtility::entityTrustInteroperabilityProfileResponse)
                        .sort(ProtectedEntityTrustInteroperabilityProfileResponse.protectedEntityTrustInteroperabilityProfileResponseOrd)
                        .toJavaList(),
                PartnerSystemCandidate
                        .findAllByOrganizationInAndTypeInHelper(arrayList(protectedSystem.organizationHelper()), protectedSystem.getType().getPartnerSystemCandidateTypeList())
                        .map(partnerSystemCandidate -> EvaluationResponseUtility.entityPartnerCandidateResponse(
                                protectedSystem,
                                partnerSystemCandidate,
                                PartnerSystemCandidateUtility::partnerSystemCandidateResponse,
                                ProtectedSystemResponseUtility::evaluationList))
                        .sort(ProtectedEntityPartnerCandidateResponse.protectedEntityPartnerCandidateResponseOrd)
                        .toJavaList());
    }

    public static ProtectedSystemResponseWithTrustExpressionEvaluation protectedSystemResponseWithTrustExpressionEvaluation(
            final ProtectedSystem protectedSystem,
            final PartnerSystemCandidate partnerSystemCandidate) {

        return new ProtectedSystemResponseWithTrustExpressionEvaluation(
                organizationResponse(protectedSystem.organizationHelper()),
                protectedSystem.idHelper(),
                protectedSystemTypeResponse(protectedSystem.getType()),
                protectedSystem.getName(),
                protectedSystem
                        .protectedSystemTrustInteroperabilityProfileUriSetHelper()
                        .map(EvaluationResponseUtility::entityTrustInteroperabilityProfileResponse)
                        .sort(ProtectedEntityTrustInteroperabilityProfileResponse.protectedEntityTrustInteroperabilityProfileResponseOrd)
                        .toJavaList(),
                List.arrayList(EvaluationResponseUtility.entityPartnerCandidateResponseWithTrustExpressionEvaluation(
                        protectedSystem,
                        partnerSystemCandidate,
                        PartnerSystemCandidateUtility::partnerSystemCandidateResponse,
                        ProtectedSystemResponseUtility::evaluationList)).toJavaList());
    }

    private static List<Evaluation<PartnerSystemCandidate>> evaluationList(
            final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri,
            final PartnerCandidate partnerCandidate) {

        return trustInteroperabilityProfileUri
                .partnerSystemCandidateTrustInteroperabilityProfileUriSetHelper()
                .filter(partnerSystemCandidateTrustInteroperabilityProfileUri -> partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper() == partnerCandidate.idHelper())
                .map(evaluation -> evaluation);
    }
}
