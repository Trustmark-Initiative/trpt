package edu.gatech.gtri.trustmark.grails.trpt.service.permission;

import org.gtri.fj.data.Option;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.arrayList;

public enum Role {
    ROLE_ADMINISTRATOR("Administrator", "trpt-admin"),
    ROLE_ADMINISTRATOR_ORGANIZATION("Organization Administrator", "trpt-org-admin");

    private final String label;
    private final String value;

    Role(
            final String label,
            final String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static Option<Role> fromValue(final String value) {
        requireNonNull("value", value);

        return arrayList(Role.values()).filter(role -> role.getValue().equals(value)).headOption();
    }
}
