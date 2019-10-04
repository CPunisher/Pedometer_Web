package com.cpunisher.pedo.servlet;

import com.cpunisher.pedo.util.mail.ISmtpServer;
import com.cpunisher.pedo.util.mail.MailMessage;
import com.cpunisher.pedo.util.mail.MyQQSmtpServer;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servletCallback", urlPatterns = "/callback.do", loadOnStartup = 0)
public class ServletCallback extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = "A callback from " + req.getParameter("nickname");
        String body = "My email is " + req.getParameter("email") + "\n" + req.getParameter("content");

        try {
            ISmtpServer smtpServer = MyQQSmtpServer.getDefault();
            smtpServer.sendMail(new MailMessage("1343316114@qq.com", title, body));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
