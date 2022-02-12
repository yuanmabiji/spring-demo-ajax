package com.example.controller;

import com.example.domain.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
	private static Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "index";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "login";
	}

	@RequestMapping(value="/cookie", method=RequestMethod.GET)
	public String cookie(){
		return "cookie";
	}
}
