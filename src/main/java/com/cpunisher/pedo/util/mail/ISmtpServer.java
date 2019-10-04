package com.cpunisher.pedo.util.mail;

import javax.mail.MessagingException;
import javax.mail.Session;

public interface ISmtpServer {

    void sendMail(MailMessage mailMessage) throws MessagingException;
}
