package com.cpunisher.pedo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

public class EncodingResponseWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private String encoding;

    public EncodingResponseWrapper(HttpServletRequest request, String encoding) {
        super(request);
        this.request = request;
        this.encoding = encoding;
    }

    @Override
    public String getParameter(String name) {
        String par = request.getParameter(name);
        if (par != null) {
            try {
                par = new String(par.getBytes(), encoding);
                return par;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return par;
    }
}
