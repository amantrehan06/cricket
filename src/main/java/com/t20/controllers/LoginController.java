package com.t20.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.t20.models.User;
import com.t20.service.impl.LoginServiceImpl;
import com.t20.service.impl.MatchServiceImpl;
import com.t20.util.Validations;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
	public MatchServiceImpl matchServiceImpl = new MatchServiceImpl();

	@Resource(name="teamsName")
	private List<String> teamNameList;
	
	@Resource(name="teamsCode")
	ArrayList<String> teamCodeList;
	
	@RequestMapping(value = "")
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		// Default View
		modelAndView.setViewName("redirect:/logout");

		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		// Check for Null && Blank
		if (Validations.isNullorEmpty(userEmail) || Validations.isNullorEmpty(userPassword)) {
			logger.info("Email or password is blank or Null");
			return modelAndView;
		}
			
		// Get LoggedIn User
		User loggedUser = loginServiceImpl.loginAuthentication(userEmail,userPassword);
		if(loggedUser==null){
			logger.info("User is Null");
			return modelAndView;
		}
	
		HttpSession session = request.getSession(true);
		modelAndView.setViewName("home");
		session.setAttribute("username", loggedUser.getFirstName() + " " + loggedUser.getLastName());
		session.setAttribute("userObj", loggedUser);
		session.setAttribute("sessionid", request.getSession().getId());

		// Check For Administrator User
		if (loggedUser.getIsAdmin().equals("Y")) {
			modelAndView.addObject("matchList", matchServiceImpl.getAllMatchesForAdmin());
			modelAndView.addObject("user", "admin");
		} else {
			modelAndView.addObject("matchList", matchServiceImpl.getAllMatchesForUser(loggedUser.getId()));
			modelAndView.addObject("user", "nadmin");
		}
		
		return modelAndView;

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView doHome(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}

		List<HashMap<String, String>> resList = null;
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("Y")) {
			resList = matchServiceImpl.getAllMatchesForAdmin();			
			modelAndView.addObject("user", "admin");
		} else {
			resList = matchServiceImpl.getAllMatchesForUser(user.getId());
			modelAndView.addObject("user", "nadmin");
		}
	
		
		modelAndView.addObject("matchList", resList);
		modelAndView.setViewName("home");
		
		return modelAndView;
	}

	@RequestMapping(value = "/teamsList", method = RequestMethod.GET)
	public @ResponseBody String teamsList(HttpServletRequest request) {
	
		StringBuilder response=new StringBuilder("");
		response.append("[");
		for(int i=0;i<teamNameList.size();i++){
			response.append("\"");
			response.append(teamNameList.get(i));
			response.append("\"");	
			if (i+1 != teamNameList.size()){
				response.append(",");	
			}		
		}
		response.append("]");
		return response.toString();

	}
	
	
	@RequestMapping(value = "/teamCode", method = RequestMethod.GET)
	public @ResponseBody String teamCode(HttpServletRequest request) {
	
		StringBuilder response=new StringBuilder("");
		response.append("[");
		for(int i=0;i<teamNameList.size();i++){
			response.append("\"");
			response.append(teamNameList.get(i));
			response.append("\"");	
			if (i+1 != teamNameList.size()){
				response.append(",");	
			}		
		}
		response.append("]");
		return response.toString();

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest request) {
	
		if (request.getSession(false) != null) {
			request.getSession(false).setAttribute("userObj",null);
			request.getSession(false).invalidate();
		}
		return "redirect:/";

	}
}