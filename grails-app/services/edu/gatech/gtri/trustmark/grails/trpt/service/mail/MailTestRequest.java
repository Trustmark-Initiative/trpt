package edu.gatech.gtri.trustmark.grails.trpt.service.mail;

public class MailTestRequest {

    private String recipient;

    public MailTestRequest() {
    }

    public MailTestRequest(final String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(final String recipient) {
        this.recipient = recipient;
    }
}
