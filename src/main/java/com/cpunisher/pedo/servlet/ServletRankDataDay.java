package com.cpunisher.pedo.servlet;

import com.cpunisher.pedo.dao.PedoConnectionFactory;
import com.cpunisher.pedo.dao.PedoOperator;
import com.cpunisher.pedo.pojo.PedoData;
import com.cpunisher.pedo.pojo.PedoDate;
import com.cpunisher.pedo.util.PedoDataList;
import com.cpunisher.pedo.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletRankDataDay", urlPatterns = "/rankDataDay.do", loadOnStartup = 0)
public class ServletRankDataDay extends HttpServlet {

    private static Logger logger = LogManager.getLogger(ServletRankDataDay.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();

        PedoOperator pedoOperator = new PedoOperator(new PedoConnectionFactory());
        TimeUtil timeUtil = new TimeUtil();
        PedoDate now = timeUtil.now();

        String sql = "select * from " + timeUtil.createTableName(now)  + " order by steps desc";
        PedoDataList dailyData = new PedoDataList(pedoOperator.select(sql));
        writer.write(dailyData.subList(0, Math.min(dailyData.getSize(), 5)).toJson());
    }
}
