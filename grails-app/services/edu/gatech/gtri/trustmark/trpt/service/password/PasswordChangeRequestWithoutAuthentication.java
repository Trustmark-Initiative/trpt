package edu.gatech.gtri.trustmark.trpt.service.password;

public class PasswordChangeRequestWithoutAuthentication {

    private String external;
    private String passwordNew1;
    private String passwordNew2;

    public PasswordChangeRequestWithoutAuthentication() {
    }

    public PasswordChangeRequestWithoutAuthentication(final String external, final String passwordNew1, final String passwordNew2) {
        this.external = external;
        this.passwordNew1 = passwordNew1;
        this.passwordNew2 = passwordNew2;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(final String external) {
        this.external = external;
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
