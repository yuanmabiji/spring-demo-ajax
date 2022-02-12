package com.example;

import com.example.filter.CookieOverrideFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SpringDemoAjaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoAjaxApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean myFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CookieOverrideFilter());
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegistrationBean;
	}

}
