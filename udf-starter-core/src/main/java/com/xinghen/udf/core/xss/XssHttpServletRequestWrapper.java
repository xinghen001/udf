package com.xinghen.udf.core.xss;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 跨站请求防范
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        return HtmlUtils.htmlEscape(parameter);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        int length = parameterValues.length;
        String[] escapseValues = new String[length];
        for (int i = 0; i < length; i++) {
            escapseValues[i] = HtmlUtils.htmlEscape(parameterValues[i]);
        }
        return escapseValues;
    }
}
