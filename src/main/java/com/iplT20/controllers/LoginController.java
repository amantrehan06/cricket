package com.iplT20.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iplT20.models.User;
import com.iplT20.service.impl.LoginServiceImpl;
import com.iplT20.service.impl.MatchServiceImpl;
import com.iplT20.util.Validations;

@Controller
public class LoginController {

	public LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
	public MatchServiceImpl matchServiceImpl = new MatchServiceImpl();

	@RequestMapping(value = "")
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		boolean nullAndBlank = true;
		// Default View
		modelAndView.setViewName("redirect:/logout");

		// Check for Null && Blank
		if (!Validations.isNull(request.getParameter("userEmail"))
				&& !Validations.isNull(request.getParameter("userPassword"))) {
			if (!Validations.isBlank(request.getParameter("userEmail"))
					&& !Validations
							.isBlank(request.getParameter("userPassword"))) {
				nullAndBlank = false;
			}
			if (!nullAndBlank) {
				// Get LoggedIn User
				User loggedUser = loginServiceImpl.loginAuthentication(
						request.getParameter("userEmail"),
						request.getParameter("userPassword"));

				if (loggedUser != null) {

					HttpSession session = request.getSession(true);

					modelAndView.setViewName("home");
					session.setAttribute("username", loggedUser.getFirstName()
							+ " " + loggedUser.getLastName());
					session.setAttribute("userObj", loggedUser);
					session.setAttribute("sessionid", request.getSession()
							.getId());

					// Check For Administrator User
					if (loggedUser.getIsAdmin().equals("Y")) {
						modelAndView.addObject("matchList",
								matchServiceImpl.getAllMatchesForAdmin());
						modelAndView.addObject("user", "admin");
					} else {
						modelAndView.addObject("matchList", matchServiceImpl
								.getAllMatchesForUser(loggedUser.getId()));
						modelAndView.addObject("user", "nadmin");
					}
				}
			} 
		}
		return modelAndView;

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView doHome(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("userObj") != null) {
			List<HashMap<String, String>> resList = null;
			User user = (User) request.getSession(false)
					.getAttribute("userObj");
			if (user.getIsAdmin().equals("Y")) {
				resList = matchServiceImpl.getAllMatchesForAdmin();
				modelAndView.addObject("user", "admin");
			} else {
				resList = matchServiceImpl.getAllMatchesForUser(user.getId());
				modelAndView.addObject("user", "nadmin");
			}

			modelAndView.addObject("matchList", resList);
			modelAndView.setViewName("home");
		}
		return modelAndView;
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
