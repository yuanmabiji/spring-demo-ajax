package example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin // 总结：不使用@CrossOrigin，跨域ajax请求进不来controller，但能进入filter
@Controller
public class LogoutController {

    private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

    /**
     * 	在本服务器（demo2.smart-sso.com）上发起ajax请求：demo2.smart-sso.com/logout，
     * @param httpServletRequest
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/logout", method= RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public List<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("username") String username, @RequestParam("password") String password){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            System.out.println("LogoutController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        } else {
            System.out.println("前台请求无cookies");
        }

        // 删除同名的tookie
        Cookie cookie = new Cookie("MyJwtToken","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("demo2.smart-sso.com");
        httpServletResponse.addCookie(cookie);


        List<String> response = new ArrayList<String>();
        response.add(username);
        response.add(password);
        return response;
    }
}
