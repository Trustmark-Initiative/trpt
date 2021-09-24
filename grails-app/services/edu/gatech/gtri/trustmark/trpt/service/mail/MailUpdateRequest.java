package edu.gatech.gtri.trustmark.trpt.service.mail;

public class MailUpdateRequest {

    private String host;
    private String port;
    private String username;
    private String password;
    private String author;

    public MailUpdateRequest() {
    }

    public MailUpdateRequest(
            final String host,
            final String port,
            final String username,
            final String password,
            final String author) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.author = author;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(final String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
}
