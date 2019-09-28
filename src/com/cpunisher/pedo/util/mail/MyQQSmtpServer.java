package com.cpunisher.pedo.util.mail;

import javax.mail.internet.AddressException;

public class MyQQSmtpServer extends AbstractSmtpServer {

    public MyQQSmtpServer(String host, String port, String user, String password) throws AddressException {
        super(host, port, user, password);
    }

    public static ISmtpServer getDefault() {
        ISmtpServer smtpServer = null;
        try {
            smtpServer = new MyQQSmtpServer("smtp.qq.com",
                    "465", "1695341114@qq.com", "trknzenqblzidcgd");
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return smtpServer;
    }
}
