package com.cpunisher.pedo.servlet;

import com.cpunisher.pedo.dao.PedoConnectionFactory;
import com.cpunisher.pedo.dao.PedoOperator;
import com.cpunisher.pedo.pojo.PedoData;
import com.cpunisher.pedo.util.PedoDataList;
import com.cpunisher.pedo.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletRegister", urlPatterns = "/register.do", loadOnStartup = 1)
public class ServletRegister extends HttpServlet {

    private static Logger logger = LogManager.getLogger(ServletRegister.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        HttpSession httpSession = req.getSession();

        String stuNum = (String) httpSession.getAttribute("stuNum");
        String name = (String) httpSession.getAttribute("name");
        if (stuNum == null || name == null) {
            writer.write("error");
            logger.error("Can't get attributes from session!");
            return;
        }

        int steps = (int) httpSession.getAttribute("steps");
        PedoOperator operator = new PedoOperator(new PedoConnectionFactory());
        TimeUtil timeUtil = new TimeUtil();
        String tableName = timeUtil.createTableName(timeUtil.now());

        PedoDataList list = new PedoDataList(operator.select("select * from " + tableName + " where stuNum=?", stuNum));
        if (list.getSize() > 0) {
            String sql = "update " + tableName + " set steps=? where stuNum=?";
            operator.insert(sql, steps, stuNum);
            logger.info("Student number " + stuNum  + " has been registered, update steps to " + steps);
        } else {
            String sql = "insert into " + tableName  + " value(?,?,?)";
            operator.insert(sql, stuNum, name, steps);
            logger.info("Student number " + stuNum  + " hasn't been registered, set steps to " + steps);
        }

        httpSession.invalidate();
        logger.info("Session has been invalidate");
        writer.write("success");
    }
}
