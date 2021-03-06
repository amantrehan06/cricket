package com.t20.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.t20.models.User;
import com.t20.service.impl.RegistrationServiceImpl;
import com.t20.util.MailUtil;
import com.t20.util.Validations;

@Controller
public class RegistrationController {

	@Autowired RegistrationServiceImpl registrationServiceImpl;
	private static final Logger logger = Logger.getLogger(RegistrationController.class);

	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView prepareForm(HttpServletRequest request) {
		return new ModelAndView("userRegistration", "command", new User());
	}

	@RequestMapping(value = "/register/new", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("SpringWeb") User user,
			HttpServletRequest request) {
		boolean isBlankAndNull = true;
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		if(Validations.isNullorEmpty(user.getEmail_id(),user.getPassword(),user.getEmp_id(),user.getFirstName(),user.getLastName())){
			logger.info("Input parameters are null or empty");
			String errorMsg="input parameters are empty, please re enter";
			/*modelAndView.setViewName("redirect:/logout?errorMsg="+errorMsg);*/
			return modelAndView;
		}
		
		if (registrationServiceImpl.registerNewUser(user)) {
			logger.info("User registered successfully: "+user.getEmail_id());
		/*	MailUtil.sendEmail(user.getEmail_id(),"Hi " + user.getFirstName() + " " + user.getLastName() + "\n\n"
							+ "Thanks for Registering on IPL 2016 T20  2016 Application. Start Predicting now!!" + "\n\n"
							+ "Regards," + "\n" + "IPL ADMIN" + "\n\n" + "http://www.sportcasts.in/t20",
					"REGISTRATION SUCCESSFUL - " + user.getFirstName() + " " + user.getLastName());
	*/		modelAndView.setViewName("index");
			modelAndView.addObject("success", "You have been successfully registered!!");
		}else{
			logger.info("User registration failed: "+user.getEmail_id());
			modelAndView.addObject("failure", "Sorry! Email Id or Unique Id already Registered");
			modelAndView.setViewName("index");
		}
		
		return modelAndView;
	}
}
