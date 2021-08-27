package edu.gatech.gtri.trustmark.trpt.service.user;

public class UserUpdateRequest {

    private long id;
    private String username;
    private String nameFamily;
    private String nameGiven;
    private String telephone;
    private boolean userEnabled;
    private boolean userLocked;
    private boolean userExpired;
    private boolean passwordExpired;
    private long organization;
    private long role;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(
            final long id,
            final String username,
            final String nameFamily,
            final String nameGiven,
            final String telephone,
            final boolean userEnabled,
            final boolean userLocked,
            final boolean userExpired,
            final boolean passwordExpired,
            final long organization,
            final long role) {

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

    public long getOrganization() {
        return organization;
    }

    public long getRole() {
        return role;
    }
}
