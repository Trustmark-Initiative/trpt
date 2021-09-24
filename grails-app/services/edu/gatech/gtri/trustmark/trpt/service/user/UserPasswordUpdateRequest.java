package edu.gatech.gtri.trustmark.trpt.service.user;

public class UserPasswordUpdateRequest {
    private String passwordOld;
    private String passwordNew;

    public UserPasswordUpdateRequest() {
    }

    public UserPasswordUpdateRequest(final String passwordOld, final String passwordNew) {
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(final String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(final String passwordNew) {
        this.passwordNew = passwordNew;
    }
}
