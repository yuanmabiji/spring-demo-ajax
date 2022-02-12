package com.example.filter;


import com.example.util.CookieUtil;

import javax.servlet.*;
import java.io.IOException;

public class CookieOverrideFilter implements Filter {
    private int i = 0;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CookieUtil.setCookie("jwtToken", "jwtToken_value" + i );
        System.out.println("jwtToken_value" + i++);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
