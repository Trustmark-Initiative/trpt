package edu.gatech.gtri.trustmark.trpt.service.password;

public class PasswordResetStatusRequest {

    private String external;

    public PasswordResetStatusRequest() {
    }

    public PasswordResetStatusRequest(final String external) {
        this.external = external;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(final String external) {
        this.external = external;
    }
}
