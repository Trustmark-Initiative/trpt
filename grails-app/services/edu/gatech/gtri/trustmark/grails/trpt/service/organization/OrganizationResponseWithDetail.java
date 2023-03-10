package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityPartnerCandidateResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.entityPartnerCandidate.ProtectedEntityTrustInteroperabilityProfileResponse;

import java.util.List;

public final class OrganizationResponseWithDetail {
    private final long id;
    private final String name;
    private final String description;
    private final String uri;
    private final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList;
    private final List<ProtectedEntityPartnerCandidateResponse> entityPartnerCandidateList;

    public OrganizationResponseWithDetail(
            final long id,
            final String name,
            final String description,
            final String uri,
            final List<ProtectedEntityTrustInteroperabilityProfileResponse> entityTrustInteroperabilityProfileList,
            final List<ProtectedEntityPartnerCandidateResponse> entityPartnerCandidateList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.entityTrustInteroperabilityProfileList = entityTrustInteroperabilityProfileList;
        this.entityPartnerCandidateList = entityPartnerCandidateList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public List<ProtectedEntityTrustInteroperabilityProfileResponse> getEntityTrustInteroperabilityProfileList() {
        return entityTrustInteroperabilityProfileList;
    }

    public List<ProtectedEntityPartnerCandidateResponse> getEntityPartnerCandidateList() {
        return entityPartnerCandidateList;
    }
}
