package edu.gatech.gtri.trustmark.trpt.service.user;

import edu.gatech.gtri.trustmark.trpt.service.organization.OrganizationResponse;
import edu.gatech.gtri.trustmark.trpt.service.role.RoleResponse;

public class UserResponse {

    private final long id;
    private final String username;
    private final String nameFamily;
    private final String nameGiven;
    private final String telephone;
    private final boolean userEnabled;
    private final boolean userLocked;
    private final boolean userExpired;
    private final boolean passwordExpired;
    private final OrganizationResponse organization;
    private final RoleResponse role;
    private final boolean editable;

    public UserResponse(
            final long id,
            final String username,
            final String nameFamily,
            final String nameGiven,
            final String telephone,
            final boolean userEnabled,
            final boolean userLocked,
            final boolean userExpired,
            final boolean passwordExpired,
            final OrganizationResponse organization,
            final RoleResponse role,
            final boolean editable) {
        this.id = id;
        this.username = username;
        this.nameFamily = nameFamily;
        this.nameGiven = nameGiven;
        this.telephone = telephone;
        this.userEnabled = userEnabled;
        this.userLocked = userLocked;
        this.userExpired = userExpired;
        this.passwordExpired = passwordExpired;
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

    public String getTelephone() {
        return telephone;
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public boolean isUserLocked() {
        return userLocked;
    }

    public boolean isUserExpired() {
        return userExpired;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
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
