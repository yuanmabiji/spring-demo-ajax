package example.controller;


import example.util.CookieUtil;
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

/**
 * 测试页面：http://127.0.0.1:8080/cookie
 * 总结论：
 * 1，服务端返回localhost域名（因为没有.）的cookie，浏览器不会保存；用127.0.0.1或带点（.）的域名可以
 * 2，不跨域情况下，不同形式(get或Post)的ajax请求或表单请求等只要cookie域名符合请求url都会带去服务端
 * 3，不同形式(get或Post)的ajax请求或表单请求等，服务端返回的cookie，浏览器同样会保存。
 */
@Controller
public class CookieController {
	private static Logger logger = LoggerFactory.getLogger(CookieController.class);

	@RequestMapping(value="/addCookie")
	public String addCookie(){
		// 生成新的token，发送到客户端
		CookieUtil.setCookie("Admin-Token", "newToken666", 36000);
		return "cookie";
	}

	/**
	 * 前台请求：http://server.smart-sso.com:8081/testCrossDomainSetCookie
	 * 浏览器告警：This Set-Cookie was blocked because its Domain attribute was invalid with regards to the current host url.
	 * 因为浏览器是在server.smart-sso.com上发起请求，你却要设置域为demo.smart-sso.com（不同domain）的cookie，所以可以返回cookie给浏览器，但浏览器拒绝设置cookie
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/testCrossDomainSetCookie")
	public String addCookie2(HttpServletRequest request,HttpServletResponse response){
		Cookie cookie = new Cookie("testCrossDomainSetCookie","eric");
		cookie.setDomain("demo.smart-sso.com");
		response.addCookie(cookie);
		return "cookie";
	}


	/**
     * 结论1：对于是localhost的ajax的get请求，服务端设置cookie，浏览器不会保存，
	 * 浏览器报错提示：This attempt to set a cookie via a Set-Cookie header was blocked due to user preferences
		但127.2.0.7的http请求浏览器却可以保存，然后不同方式的ajax请求也会带着cookie过去, TODO 为啥localhost不行呢？
	 * 答：今天在本地调试代码的时候，再域名中使用localhost，结果一直调试不成功，最后发现在登录时，setcookie()没有设置进去
	 *
	 *  于是发现了，在使用localhost调试时，保存cookie是无效的。记录一下。
	 *
	 *  原因：手册里关于setCookie说了，域名要有至少两个点，你用127.0.0.1都可以。否则就会被浏览器拒绝。
	 *
	 * 　　　手册原文：
	 * 　　domain names must contain at least two dots (.),
	 * 　　hence 'localhost' is invalid and the browser will refuse to set the cookie!
	 * 　　instead for localhost you should use false
	 *  解决：直接使用127.0.0.1      或者    自己定义一个域名，在host中做映射
	 *
	 *  参考：https://www.cnblogs.com/nww57/p/5113329.html
	 *
	 *  结论2：不跨域情况下，不同形式(get或Post)的ajax请求或表单请求等只要cookie域名符合请求url都会带去服务端
	 *  @param username
     * @param password
     * @return
     */
	@ResponseBody
	@RequestMapping(value="/ajaxGetListWithResponseCookie", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<String> ajaxGetListWithResponseCookie(@ModelAttribute("username") String username, @RequestParam("password") String password){
		CookieUtil.setCookie("ajaxGetListWithResponseCookie", "ajaxGetListWithResponseCookie_Value");
		logger.info("Request List....");
		logger.info("username : "+username);
		logger.info("password : "+password);
		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return response;
	}

	/**
	 * 结论：ajax的post请求，结论同AJAX的GET请求，如上
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxPostListWithResponseCookie", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<String> ajaxPostListWithResponseCookie(String username, String password){
		CookieUtil.setCookie("ajaxPostListWithResponseCookie", "ajaxPostListWithResponseCookie");
		logger.info("Request List....");
		logger.info("username : "+username);
		logger.info("password : "+password);
		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return response;
	}

	/**
	 * 结论：html表单<form></>的post请求，结论同AJAX的GET请求，如上
	 */
	@RequestMapping(value="/formPostSubmitWithResponseCookie", method=RequestMethod.POST)
	public String formPostSubmitWithResponseCookie(){
		CookieUtil.setCookie("formPostSubmitWithResponseCookie", "formPostSubmitWithResponseCookie_Value");
		return "cookie";
	}

	/**
	 * 结论：html表单<form></>的get请求，结论同AJAX的GET请求，如上
	 */
	@RequestMapping(value="/formGetSubmitWithResponseCookie", method=RequestMethod.GET)
	public String formGetSubmitWithResponseCookie(){
		CookieUtil.setCookie("formGetSubmitWithResponseCookie", "formGetSubmitWithResponseCookie_Value");
		return "cookie";
	}



}
