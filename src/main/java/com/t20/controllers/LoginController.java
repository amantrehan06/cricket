package com.t20.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.t20.models.User;
import com.t20.service.impl.LoginServiceImpl;
import com.t20.service.impl.MatchServiceImpl;
import com.t20.util.Validations;

@Controller
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired LoginServiceImpl loginServiceImpl;
	@Autowired MatchServiceImpl matchServiceImpl;

	@Resource(name="teamsName")
	private List<String> teamNameList;
	
	@Resource(name="teamsCode")
	ArrayList<String> teamCodeList;
	
	@RequestMapping(value = "")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request) {
		return "contact";
	}
	
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String prepareFormNotice(HttpServletRequest request) {
		return "notice";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView doLogout(HttpServletRequest request,RedirectAttributes redirectAttributes) {
	
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		String errorMsg = request.getParameter("errorMsg");
		if (request.getSession(false) != null){
			if(errorMsg!=null && !errorMsg.equals("")){
				redirectAttributes.addFlashAttribute("failure", errorMsg);
			}
			else if(request.getSession(false).getAttribute("userObj")!=null){
				redirectAttributes.addFlashAttribute("success", "Logged out successfully");
			}
			else{
				redirectAttributes.addFlashAttribute("failure", "Oops something went wrong, please re-login");
			}
			request.getSession(false).setAttribute("userObj",null);
			request.getSession(false).invalidate();
		}
		return modelAndView;

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
			logger.info("Email or password is blank or Null: "+userEmail +" ,"+userPassword);
			return modelAndView;
		}
		
		logger.info("User attemped logged in: "+userEmail);
		// Get LoggedIn User
		User loggedUser = loginServiceImpl.loginAuthentication(userEmail,userPassword);
		if(loggedUser==null){
			String errorMsg="email or password is incorrect";
			logger.info("Incorrect credentials username, password: "+userEmail +" ,"+userPassword);
			/*modelAndView.setViewName("redirect:/logout?errorMsg="+errorMsg);*/
			return modelAndView;
		}
	
		logger.info("User logged in successfully: "+userEmail);
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
}
