package com.cpunisher.pedo.util.mail;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Properties;

public class MyQQSmtpServer extends AbstractSmtpServer {

    private static ISmtpServer instance;

    public MyQQSmtpServer(String host, String port, String user, String password) throws AddressException {
        super(host, port, user, password);
    }

    public static ISmtpServer getDefault() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    Properties properties = new Properties();

                    try {
                        properties.load(MyQQSmtpServer.class.getClassLoader().getResourceAsStream("mail.properties"));
                        instance = new MyQQSmtpServer(properties.getProperty("host"), properties.getProperty("port"),
                                properties.getProperty("user"), properties.getProperty("password"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }
}
