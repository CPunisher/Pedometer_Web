package com.cpunisher.pedo.util.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public abstract class AbstractSmtpServer implements ISmtpServer {

    protected Properties properties;
    protected Session mailSession;
    protected InternetAddress from;

    public AbstractSmtpServer(String host, String port, String user, String password) throws AddressException {
        properties= new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        properties.put("mail.smtp.host", host);
        properties.put("mail.user", user);
        properties.put("mail.password", password);
        properties.put("mail.smtp.port", port);
        properties.setProperty("mail.smtp.socketFactory.port", port);

        from = new InternetAddress(user);
        mailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    @Override
    public void sendMail(MailMessage mailMessage) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setFrom(from);
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[] {new InternetAddress(mailMessage.getTo())});
        mimeMessage.setSubject(mailMessage.getTitle(), "utf-8");
        mimeMessage.setContent(mailMessage.getContent(), "text/html; charset=utf-8");

        System.out.println(mailMessage);
        Transport.send(mimeMessage);
    }
}
