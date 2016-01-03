package com.iplT20.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iplT20.models.User;
import com.iplT20.service.impl.RegistrationServiceImpl;
import com.iplT20.util.MailUtil;
import com.iplT20.util.Validations;

@Controller
public class RegistrationController {

	RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

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

		if (!Validations.isNull(user.getEmail_id())
				&& !Validations.isNull(user.getPassword())
				&& !Validations.isNull(user.getEmp_id())
				&& !Validations.isNull(user.getFirstName())
				&& !Validations.isNull(user.getLastName())) {
			if (!Validations.isBlank(user.getEmail_id())
					&& !Validations.isBlank(user.getPassword())
					&& !Validations.isBlank(user.getEmp_id())
					&& !Validations.isBlank(user.getFirstName())
					&& !Validations.isBlank(user.getLastName())) {
				isBlankAndNull = false;
			}
		}
		if (!isBlankAndNull) {
				if (registrationServiceImpl.registerNewUser(user)) {
					MailUtil.sendEmail(user.getEmail_id(), 
					"Hi "+user.getFirstName()+" "+user.getLastName()+"\n\n"+"Thanks for Registering on IPL 2015 Application. Start Predicting now!!"+"\n\n"+"Regards,"+"\n"+"IPL ADMIN"
				+"\n\n"+"http://www.sportcasts.in/iplT20", "REGISTRATION SUCCESSFUL - "+user.getFirstName()+" "+user.getLastName());
					modelAndView.setViewName("regSuccess");
					modelAndView.addObject("message",
							"You have been successfully registered!!");
				}
		}
		return modelAndView;
	}
}
