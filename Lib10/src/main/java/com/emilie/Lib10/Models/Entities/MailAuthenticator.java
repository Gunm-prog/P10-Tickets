package com.emilie.Lib10.Models.Entities;

import javax.mail.Authenticator;

public class MailAuthenticator extends Authenticator {

    private final String username;

    private final String password;


    public MailAuthenticator(String username, String password) {
        this.username=username;
        this.password=password;
    }
}
