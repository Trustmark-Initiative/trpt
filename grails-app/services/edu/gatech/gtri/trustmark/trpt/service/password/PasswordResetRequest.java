package edu.gatech.gtri.trustmark.trpt.service.password;

public class PasswordResetRequest {

    private String username;

    public PasswordResetRequest() {
    }

    public PasswordResetRequest(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
