package example.filter;




import example.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CookieOverrideFilter implements Filter {
    private int i = 0;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();

        if(cookies != null) {
            System.out.println("过滤器打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        } else {
            System.out.println("前台请求无cookies");
        }
        // 【重要】注意：ajax或axios跨域请求，即使跨域服务端返回cookie给浏览器，浏览器不会警告提示，但浏览器不会设置cookie，
        // 解决方案只要在ajax请求中设置xhrFields: {withCredentials: true}即可，此时浏览器就会设置跨域返回的cookie。
        // 另外设置xhrFields: {withCredentials: true}，还可以解决跨域请求时不传递cookie的问题，允许携带证书。
        // 总结：xhrFields: {withCredentials: true}即可解决ajax跨域请求携带cookie问题和服务端响应设置cookie给浏览器问题
        // xhrFields 大概的意思是，默认情况下，标准的跨域请求是不会发送cookie等用户认证凭据的。所以，当你再次访问远程api的时候，cookie是不会被带上的，于是乎，服务器理所当然地认为你还没有登录。
        CookieUtil.setCookie("jwtToken", "jwtToken_value" + i );
        System.out.println("jwtToken_value" + i++);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
