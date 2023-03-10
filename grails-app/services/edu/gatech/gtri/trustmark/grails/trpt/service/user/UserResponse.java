package edu.gatech.gtri.trustmark.grails.trpt.service.user;

import edu.gatech.gtri.trustmark.grails.trpt.service.organization.OrganizationResponse;
import edu.gatech.gtri.trustmark.grails.trpt.service.role.RoleResponse;

public class UserResponse {

    private final long id;
    private final String username;
    private final String nameFamily;
    private final String nameGiven;
    private final String contactEmail;
    private final OrganizationResponse organization;
    private final RoleResponse role;
    private final boolean editable;

    public UserResponse(
            final long id,
            final String username,
            final String nameFamily,
            final String nameGiven,
            final String contactEmail,
            final OrganizationResponse organization,
            final RoleResponse role,
            final boolean editable) {
        this.id = id;
        this.username = username;
        this.nameFamily = nameFamily;
        this.nameGiven = nameGiven;
        this.contactEmail = contactEmail;
        this.organization = organization;
        this.role = role;
        this.editable = editable;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public String getNameGiven() {
        return nameGiven;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }

    public RoleResponse getRole() {
        return role;
    }

    public boolean isEditable() {
        return editable;
    }
}
