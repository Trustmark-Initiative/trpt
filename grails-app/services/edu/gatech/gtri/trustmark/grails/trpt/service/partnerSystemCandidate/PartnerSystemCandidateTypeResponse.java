package edu.gatech.gtri.trustmark.grails.trpt.service.partnerSystemCandidate;

public class PartnerSystemCandidateTypeResponse {

    private final String value;
    private final String label;

    public PartnerSystemCandidateTypeResponse(
            final String value,
            final String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
