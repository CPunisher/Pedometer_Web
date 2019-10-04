package com.cpunisher.pedo.servlet;

import com.cpunisher.pedo.pojo.Code2Session;
import com.cpunisher.pedo.pojo.ResultGetSteps;
import com.cpunisher.pedo.pojo.StepInfo;
import com.cpunisher.pedo.pojo.StepInfoList;
import com.cpunisher.pedo.util.CryptUtil;
import com.cpunisher.pedo.util.http.HttpHelper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

@WebServlet(name = "servletGetSteps", urlPatterns = "/getSteps.do", loadOnStartup = 1)
public class ServletGetSteps extends HttpServlet {

    private static final String APP_ID = "wxe69f108c67362339";
    private static final String APP_SECRET = "fa6411009077d1d28f35a0d7a7f20f40";

    private static Logger logger = LogManager.getLogger(ServletGetSteps.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();

        String stuNum = req.getParameter("stuNum");
        String encryptedData = req.getParameter("encryptedData");
        String iv = req.getParameter("iv");
        String code = req.getParameter("code");

        CloseableHttpClient client = HttpClients.createDefault();
        Gson gson = new Gson();
        CryptUtil cryptUtil = new CryptUtil();
        HttpHelper httpHelper = new HttpHelper(client);
        HttpResponse httpResponse;

        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID +
                "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code");
        httpResponse = client.execute(httpGet);
        String sessionKey = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Code2Session.class).getSession_key();
        logger.info("Session key resolved: " + sessionKey);

        String s = "['bangding','10006','" + stuNum + "','" + cryptUtil.encryptMD5("888888") + "']";
        httpResponse = httpHelper.sendPost("http://quantiwang.cn:8012/cloud/DflyServer", new BasicNameValuePair("name", s));
        String stuInfo = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        logger.info("Student info has been got: " + stuInfo);

        StepInfo[] stepInfoList = gson.fromJson(new String(cryptUtil.decrypt(encryptedData, iv, sessionKey)),
                StepInfoList.class).getStepInfoList();
        ResultGetSteps resultGetSteps = new ResultGetSteps();
        int start = stuInfo.indexOf(':');
        int end = stuInfo.indexOf(',');
        if(start != -1 && end != -1) {
            resultGetSteps.setName(stuInfo.substring(start + 1, end));
        } else {
            resultGetSteps.setName("error");
            logger.error("Student info is illegal!");
        }

        if (stepInfoList.length > 0) {
            resultGetSteps.setStepInfo(stepInfoList[stepInfoList.length - 1]);
        } else {
            resultGetSteps.setName("error");
            logger.error("No running data!");
        }

        if (!"error".equals(resultGetSteps.getName())) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("stuNum", stuNum);
            httpSession.setAttribute("name", resultGetSteps.getName());
            httpSession.setAttribute("steps", resultGetSteps.getStepInfo().getStep());
            resultGetSteps.setSessionId(httpSession.getId());
            logger.info("Session's attributes are set with the session id: " + httpSession.getId());
        }

        writer.write(gson.toJson(resultGetSteps));
    }
}
