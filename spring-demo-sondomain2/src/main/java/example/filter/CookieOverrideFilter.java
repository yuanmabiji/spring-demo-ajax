package example.filter;




import example.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CookieOverrideFilter implements Filter {
    private int i = 0;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if (request.getRequestURI().contains("/deleteCookie")) {
            CookieUtil.deleteCookie("jwtToken" );
            return;
        }
        CookieUtil.setCookie("jwtToken", "jwtToken_value" + i );
        System.out.println("jwtToken_value" + i++);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
