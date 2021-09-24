package edu.gatech.gtri.trustmark.trpt.service.mail;

public class MailResponse {

    private final String host;
    private final long port;
    private final String username;
    private final String password;
    private final String author;

    public MailResponse(final String host, final long port, final String username, final String password, final String author) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.author = author;
    }

    public String getHost() {
        return host;
    }

    public long getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthor() {
        return author;
    }
}
