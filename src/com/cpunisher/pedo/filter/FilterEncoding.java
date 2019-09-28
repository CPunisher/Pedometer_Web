package com.cpunisher.pedo.filter;


import com.cpunisher.pedo.util.EncodingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "filterEncoding", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "encoding", value = "utf-8")
})
public class FilterEncoding implements Filter {

    private String encoding;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if ("get".equals(httpRequest.getMethod().toLowerCase())) {
            httpRequest = new EncodingResponseWrapper(httpRequest, encoding);
        } else {
            servletRequest.setCharacterEncoding(encoding);
        }
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(httpRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void destroy() {

    }
}
