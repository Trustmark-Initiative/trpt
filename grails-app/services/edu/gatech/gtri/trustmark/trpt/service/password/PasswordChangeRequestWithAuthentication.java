package edu.gatech.gtri.trustmark.trpt.service.password;

public class PasswordChangeRequestWithAuthentication {

    private String passwordOld;
    private String passwordNew1;
    private String passwordNew2;

    public PasswordChangeRequestWithAuthentication() {
    }

    public PasswordChangeRequestWithAuthentication(final String passwordOld, final String passwordNew1, final String passwordNew2) {
        this.passwordOld = passwordOld;
        this.passwordNew1 = passwordNew1;
        this.passwordNew2 = passwordNew2;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(final String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew1() {
        return passwordNew1;
    }

    public void setPasswordNew1(final String passwordNew1) {
        this.passwordNew1 = passwordNew1;
    }

    public String getPasswordNew2() {
        return passwordNew2;
    }

    public void setPasswordNew2(final String passwordNew2) {
        this.passwordNew2 = passwordNew2;
    }
}
