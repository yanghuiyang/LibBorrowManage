package com.yang.bishe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/open")
public class OpenController {
	@RequestMapping("/gofindBook")
	public ModelAndView gofindBook(){
		return new ModelAndView("open/findBook");
	}
	  @RequestMapping("/goComplexSearch")
	   public ModelAndView goComplexFind(){
		  return new ModelAndView("open/complexSearchBook");
	    }
	  @RequestMapping("/goStatistics")
	   public ModelAndView goStatistics(){
		  return new ModelAndView("open/statistics");
	    }
}
