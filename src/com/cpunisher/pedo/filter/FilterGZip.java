package com.cpunisher.pedo.filter;


import com.cpunisher.pedo.util.GZipResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "filterGZip", urlPatterns = {"/rankDataDay.do", "/rankDataAll.do"})
public class FilterGZip implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String support = httpServletRequest.getHeader("Accept-Encoding");
        if (support != null && support.toLowerCase().contains("gzip")) {
            GZipResponseWrapper responseWrapper = new GZipResponseWrapper(httpResponse);
            filterChain.doFilter(servletRequest, responseWrapper);
            responseWrapper.finish();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
