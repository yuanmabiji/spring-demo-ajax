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
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value="/login", method= RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public List<String> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("username") String username, @RequestParam("password") String password){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            System.out.println("LoginController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        } else {
            System.out.println("前台请求无cookies");
        }

        // 登录后模拟添加MyJwtToken到cookie
        Cookie cookie = new Cookie("MyJwtToken","MyJwtToken_Value"); // domain默认为demo2.smart-sso.com
        cookie.setDomain("demo2.smart-sso.com");
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        List<String> response = new ArrayList<String>();
        response.add("登录成功");
        response.add("登录成功");
        return response;
    }
}
