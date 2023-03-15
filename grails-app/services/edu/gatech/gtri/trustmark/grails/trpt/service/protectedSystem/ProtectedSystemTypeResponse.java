package edu.gatech.gtri.trustmark.grails.trpt.service.protectedSystem;

public final class ProtectedSystemTypeResponse {

    private final String value;
    private final String label;

    public ProtectedSystemTypeResponse(
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
