package com.bank.controller.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharSetFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(CharSetFilter.class);
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("requestEncoding");
        if(encoding==null){
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Current characterEncoding " + servletRequest.getCharacterEncoding());
        if(servletRequest.getCharacterEncoding()==null){
            servletRequest.setCharacterEncoding(encoding);
            LOG.info("Set encoding " + encoding);
        }
        servletResponse.setContentType("text/html; charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
