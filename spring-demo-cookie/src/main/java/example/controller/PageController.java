package example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
	private static Logger logger = LoggerFactory.getLogger(PageController.class);
	@RequestMapping(value="/cookie", method=RequestMethod.GET)
	public String cookie(){
		return "cookie";
	}

	@RequestMapping(value="/cookie2", method=RequestMethod.GET)
	public String cookie2(){
		return "cookie2";
	}
}
