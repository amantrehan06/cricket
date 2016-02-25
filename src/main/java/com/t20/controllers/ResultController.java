package com.t20.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.t20.models.User;
import com.t20.service.impl.ResultServiceImpl;

@Controller
public class ResultController {

	@Autowired ResultServiceImpl resultServiceImpl;
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@RequestMapping("/myScore")
	public ModelAndView myScore(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		
		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		
		User user = (User) request.getSession(false).getAttribute("userObj");
		/*if (user.getIsAdmin().equals("N")) {*/
			List<HashMap<String, String>> resultMap = resultServiceImpl.showResultForUser(user.getId());
			resultMap.get(0).put("name", user.getFirstName() + " " + user.getLastName());
			resultMap.get(0).put("empid", user.getEmp_id());
			modelAndView.addObject("score", resultMap);
			modelAndView.setViewName("result");

		/*}*/
		return modelAndView;
		
	}
	
	@RequestMapping("/allScores")
	public ModelAndView friendsScore(HttpServletRequest request){		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		List<HashMap<String, String>> resultMap = resultServiceImpl.showAllResult0();
		modelAndView.addObject("score", resultMap);
		modelAndView.setViewName("result");

		return modelAndView;
		
	}
	
	@RequestMapping("/graph")
	public ModelAndView graph(HttpServletRequest request){

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		
		List<HashMap<String, String>> resultMap = resultServiceImpl.showAllResult0();
		List<String> finalVal = new ArrayList<String>();
		for (int i = 0; i < resultMap.size(); i++) {
			finalVal.add(i, "[" + resultMap.get(i).get("name") + "-" + resultMap.get(i).get("win") + "]");
		}
		modelAndView.addObject("size",resultMap.size());
		modelAndView.addObject("finalList", finalVal);
		modelAndView.setViewName("resultGraph");

		return modelAndView;
		
	}
	
	/*@RequestMapping("/graph1")
	public ModelAndView graph1(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		List<HashMap<String, String>> resultMap = resultServiceImpl.showAllResult1();
		List<String> finalVal = new ArrayList<String>();
		for (int i = 0; i < resultMap.size(); i++) {
			finalVal.add(i, "[" + resultMap.get(i).get("name") + "-" + resultMap.get(i).get("win") + "]");
		}
		modelAndView.addObject("finalList", finalVal);
		modelAndView.setViewName("resultGraph1");

		return modelAndView;
		
	}*/
	
	/*@RequestMapping("/graph2")
	public ModelAndView graph2(HttpServletRequest request){

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		List<HashMap<String, String>> resultMap = resultServiceImpl.showAllResult2();
		List<String> finalVal = new ArrayList<String>();
		for (int i = 0; i < resultMap.size(); i++) {
			finalVal.add(i, "[" + resultMap.get(i).get("name") + "-" + resultMap.get(i).get("win") + "]");
		}
		modelAndView.addObject("finalList", finalVal);
		modelAndView.setViewName("resultGraph2");

		return modelAndView;

	}*/
}
