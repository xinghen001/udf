package com.xinghen.udf.core.xss;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 跨站请求防范
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
@Slf4j
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("(XssFilter) initialize");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest =
                new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("(XssFilter) destroy");
    }
}
