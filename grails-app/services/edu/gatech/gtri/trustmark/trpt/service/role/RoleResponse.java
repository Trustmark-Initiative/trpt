package edu.gatech.gtri.trustmark.trpt.service.role;

public class RoleResponse {
    private final long id;
    private final String value;
    private final String label;

    public RoleResponse(
            final long id,
            final String value,
            final String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
