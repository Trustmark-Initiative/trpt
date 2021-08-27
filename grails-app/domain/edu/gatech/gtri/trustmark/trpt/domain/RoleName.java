package edu.gatech.gtri.trustmark.trpt.domain;

public enum RoleName {
    ROLE_ADMINISTRATOR("Administrator"),
    ROLE_ADMINISTRATOR_ORGANIZATION("Organization Administrator");

    private final String name;

    RoleName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
