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
     * 	在其他服务器（demo2.smart-sso.com）上发起ajax请求：demo.smart-sso.com/logout，其中demo.smart-sso.com为本服务器，
     *  然后设置域为demo.smart-sso.com的同名cookie来删除cookie，以达到跨域删除cookie的效果，看能否成功？
     *  答案：可以成功删除，这个demo可以用来单点删除跨域cookie，比如单点退出。
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
        // 删除同名cookie必须显式设置domain和path，同时意味着设置cookie时也要显式设置domain和path才能被删除
        // 删除同名的tookie
        Cookie cookie = new Cookie("MyJwtToken","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("demo.smart-sso.com");
        httpServletResponse.addCookie(cookie);


        List<String> response = new ArrayList<String>();
        response.add(username);
        response.add(password);
        return response;
    }
}
