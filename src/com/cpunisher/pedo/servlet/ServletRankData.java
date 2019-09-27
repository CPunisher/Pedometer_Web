package com.cpunisher.pedo.servlet;

import com.cpunisher.pedo.dao.PedoConnectionFactory;
import com.cpunisher.pedo.dao.PedoOperator;
import com.cpunisher.pedo.pojo.PedoData;
import com.cpunisher.pedo.pojo.PedoDate;
import com.cpunisher.pedo.util.PedoDataList;
import com.cpunisher.pedo.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletRankData", urlPatterns = "/rankData.do", loadOnStartup = 0)
public class ServletRankData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();


        PedoOperator operator = new PedoOperator(new PedoConnectionFactory());
        TimeUtil timeUtil = new TimeUtil();
        PedoDate now = timeUtil.now();
        PedoDate temp = timeUtil.now();

        PedoDataList monthDatas = new PedoDataList();
        PedoDataList weekDatas = new PedoDataList();
        PedoDataList dayDatas = new PedoDataList();
        for (int i = 1; i <= timeUtil.getMonthDays(now.getMonth()); i++) {
            temp.setDay(i);
            String sql = "select * from " + timeUtil.createTableName(temp);
            PedoDataList list = new PedoDataList(operator.select(sql));
            for (PedoData dayData : list.getDataList()) {
                dayData.setDate(temp);
                if (!weekDatas.hasDataOfNumber(dayData.getStuNum()))
                    weekDatas.addCopy(dayData, PedoDate.WEEKLY_DATE);
                if (!monthDatas.hasDataOfNumber(dayData.getStuNum()))
                    monthDatas.addCopy(dayData, PedoDate.MONTHLY_DATE);
            }

            monthDatas.mergeAllData(list);

            if (i == now.getDay()) {
                dayDatas = list;
            }
            if (now.getDay() - i <= 7 && now.getDay() - i >= 0) {
                weekDatas.mergeAllData(list);
            }
        }
        dayDatas.merge(weekDatas).merge(monthDatas);
        writer.write(dayDatas.toJson());
    }
}
