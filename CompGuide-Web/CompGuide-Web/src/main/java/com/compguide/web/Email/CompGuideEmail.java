/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Email;

import javax.mail.Message.RecipientType;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;

/**
 *
 * @author hemc
 */
public class CompGuideEmail {

    private String name;
    private String message;
    private String email;

    private static final String TO_EMAIL = "antoniosilva9116@gmail.com";
    private static final String FROM_EMAIL = "compguideguidelines@gmail.com";
    private static final String USERNAME = "compguideguidelines";
    private static final String PASSWORD = "Compguideguidelines16";
    private static final String SUBJECT = "CompGuide";
    private static final String EMAIL_CONTENT = "Dear Mail Crawler," + "\n\n No spam to my email, please!";
    private String emailContentHTML;

    private static final String TO_DEFAULT_NAME = "Aniversariante";
    private static final String TEST_DESTINATARIO = "nunoslvdr@gmail.com";
    private static final String HOSTNAME = "smtp.gmail.com";
    private static final int SMTP_PORT_TLS = 587;
    private static final int SMTP_PORT_SSL = 465;

    public CompGuideEmail() {
    }

    public CompGuideEmail(String name, String message, String email) {
        this.name = name;
        this.message = message;
        this.email = email;
    }

    public void send() {
        Mailer mailer = new Mailer("smtp.gmail.com", SMTP_PORT_SSL, USERNAME, PASSWORD, TransportStrategy.SMTP_SSL);

        Email email = new Email();
        email.setFromAddress(USERNAME, FROM_EMAIL);
        email.setSubject(SUBJECT);
        email.addRecipient(TO_DEFAULT_NAME, TO_EMAIL, RecipientType.TO);
        email.setText(EMAIL_CONTENT);
        emailContentHTML = "<html>"
                + "Message from " + name + ", "
                + "<p> Message: " + message + "</p>"
                + "<p> Email to reply: " + this.email + "</p>"
                + "</html>";
        email.setTextHTML(emailContentHTML);

        mailer.sendMail(email);
    }

}
