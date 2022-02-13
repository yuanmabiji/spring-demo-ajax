package example.controller;


import example.domain.DataSet;
import example.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@CrossOrigin // 总结：不使用@CrossOrigin，跨域ajax请求进不来controller，但能进入filter
@Controller
public class CrossOriginController {
	private static Logger logger = LoggerFactory.getLogger(CrossOriginController.class);

	// 【重要】注意：ajax或axios跨域请求，即使跨域服务端返回cookie给浏览器，浏览器不会警告提示，但浏览器不会设置cookie，
	// 解决方案只要在ajax请求中设置xhrFields: {withCredentials: true}即可，此时浏览器就会设置跨域返回的cookie。
	// 另外设置xhrFields: {withCredentials: true}，还可以解决跨域请求时不传递cookie的问题，允许携带证书。
	// 总结：xhrFields: {withCredentials: true}即可解决ajax跨域请求携带cookie问题和服务端响应设置cookie给浏览器问题
	// xhrFields 大概的意思是，默认情况下，标准的跨域请求是不会发送cookie等用户认证凭据的。所以，当你再次访问远程api的时候，cookie是不会被带上的，于是乎，服务器理所当然地认为你还没有登录。
	@ResponseBody
	@RequestMapping(value="/ajaxGetCrossOrigin", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<String> ajaxGetCrossOrigin(HttpServletRequest httpServletRequest, @ModelAttribute("username") String username, @RequestParam("password") String password){
		// 生成新的token，发送到客户端
		//CookieUtil.setCookie("Admin-Token", "newToken666", 36000);
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies != null) {
			System.out.println("CrossOriginController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		} else {
			System.out.println("前台请求无cookies");
		}
		logger.info("ajaxGetCrossOrigin....");
		logger.info("username : "+username);
		logger.info("password : "+password);
		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return response;
	}

	/**
	 * 在其他服务器（demo2.smart-sso.com）上发起ajax请求：demo.smart-sso.com/ajaxGetCrossOriginWithSetDiffDomainCookie，其中demo.smart-sso.com为本服务器，
	 * 然后设置域为demo.smart-sso.com的cookie，看能否设置成功？
	 * 结论：设置不成功，浏览器告警：This Set-Cookie was blocked because its Domain attribute was invalid with regards to the current host url.
	 *      说明服务端（demo.smart-sso.com）设置跨域cookie(demo2.smart-sso.com)总不成功，虽然浏览器是在demo2.smart-sso.com中的ajax发送的跨域请求
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxGetCrossOriginWithSetDiffDomainCookie", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<String> ajaxGetCrossOriginWithSetDiffDomainCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("username") String username, @RequestParam("password") String password){
		// 生成新的token，发送到客户端
		//CookieUtil.setCookie("Admin-Token", "newToken666", 36000);
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies != null) {
			System.out.println("CrossOriginController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		} else {
			System.out.println("前台请求无cookies");
		}

		Cookie cookie = new Cookie("ajaxGetCrossOriginWithSetDiffDomainCookie","ajaxGetCrossOriginWithSetDiffDomainCookie_Value");
		cookie.setDomain("demo2.smart-sso.com");
		httpServletResponse.addCookie(cookie);

		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return response;
	}

	/**
	 * 在其他服务器（demo2.smart-sso.com）上发起ajax请求：demo.smart-sso.com/ajaxGetCrossOriginWithSetDiffDomainCookie，其中demo.smart-sso.com为本服务器，
	 * 然后设置域为demo2.smart-sso.com的cookie，看能否设置成功？
	 * * 结论：设置成功，
	 * 	 *      说明服务端（demo.smart-sso.com）设置同域cookie(demo.smart-sso.com)总成功，虽然浏览器是在demo2.smart-sso.com中的ajax发送的跨域请求
	 * 	 结论2：浏览器告警：This Set-Cookie was blocked because its Domain attribute was invalid with regards to the current host url.
	 * 	       是以服务端设置跨域则告警，跟浏览器前端无关。
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxGetCrossOriginWithSetSameDomainCookie", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<String> ajaxGetCrossOriginWithSetSameDomainCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("username") String username, @RequestParam("password") String password){
		// 生成新的token，发送到客户端
		//CookieUtil.setCookie("Admin-Token", "newToken666", 36000);
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies != null) {
			System.out.println("CrossOriginController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		} else {
			System.out.println("前台请求无cookies");
		}

		Cookie cookie = new Cookie("ajaxGetCrossOriginWithSetSameDomainCookie","ajaxGetCrossOriginWithSetSameDomainCookie_Value");
		cookie.setDomain("demo.smart-sso.com");
		httpServletResponse.addCookie(cookie);

		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return response;
	}


	@ResponseBody
	@RequestMapping(value="/ajaxPostCrossOrigin", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> ajaxPostCrossOrigin(HttpServletRequest httpServletRequest, @RequestBody DataSet dataSet){
		Cookie[] cookies = httpServletRequest.getCookies();
		System.out.println("CrossOriginController打印前台post请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
		if(cookies != null) {
			System.out.println("CrossOriginController打印前台请求" + httpServletRequest.getRequestURI() + "携带过来的cookie");
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		} else {
			System.out.println("前台请求无cookies");
		}
		logger.info("Request Entity.... - {}", dataSet);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", dataSet.getUsername());
		map.put("password", dataSet.getPassword());

		ResponseEntity<Object> response = new ResponseEntity<Object>(map, HttpStatus.OK);
		return response;
	}


	/**
	 * 结论：表单跨域请求支持携带cookie，支持响应设置cookie（父cookie及同域名cookie）
	 */
	@RequestMapping(value="/formPostSubmitWithCrossOriginCookie", method=RequestMethod.POST)
	@ResponseBody
	public void formPostSubmitWithResponseCookie(){
		CookieUtil.setCookie("formPostSubmitWithCrossOriginCookie", "formPostSubmitWithCrossOriginCookie_Value");
		//return "cookie";
	}

	/**
	 * 结论：表单跨域请求支持携带cookie，支持响应设置cookie（父cookie及同域名cookie）
	 */
	@RequestMapping(value="/formGetSubmitWithCrossOriginCookie", method=RequestMethod.GET)
	@ResponseBody
	public String formGetSubmitWithResponseCookie(){
		CookieUtil.setCookie("formGetSubmitWithCrossOriginCookie", "formGetSubmitWithCrossOriginCookie_Value");
		return "cookie";
	}
}
