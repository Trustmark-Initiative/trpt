package edu.gatech.gtri.trustmark.grails.trpt.service.role;

public class RoleResponse {
    private final String value;
    private final String label;

    public RoleResponse(
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
