package com.iplT20.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iplT20.models.Team;
import com.iplT20.models.User;
import com.iplT20.service.impl.MatchServiceImpl;
import com.iplT20.service.impl.TeamServiceImpl;
import com.iplT20.util.Validations;
@Controller
public class TeamController {

	TeamServiceImpl teamServiceImpl = new TeamServiceImpl();
	MatchServiceImpl matchServiceImpl = new MatchServiceImpl();

	@RequestMapping(value = "/newTeam", method = RequestMethod.POST)
	public ModelAndView addTeam(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		boolean nullAndBlank = true;
		modelAndView.setViewName("redirect:/logout");
		List<HashMap<String, String>> resList = null;

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("userObj") != null) {

			User user = (User) request.getSession(false)
					.getAttribute("userObj");

			if (!Validations.isNull(request.getParameter("teamName"))
					&& !Validations.isNull(request.getParameter("teamCode"))
					&& !Validations.isNull(request.getParameter("captain"))) {
				if (!Validations.isBlank(request.getParameter("teamName"))
						&& !Validations.isBlank(request
								.getParameter("teamCode"))
						&& !Validations
								.isBlank(request.getParameter("captain"))) {
					nullAndBlank = false;
				}
			}

			if (!nullAndBlank) {
				if (user.getIsAdmin().equals("Y")) {
					Team team = new Team();

					team.setTeamName(request.getParameter("teamName"));
					team.setTeamCode(request.getParameter("teamCode"));
					team.setCaptain(request.getParameter("captain"));
					teamServiceImpl.addTeam(team);

					resList = matchServiceImpl.getAllMatchesForAdmin();
					modelAndView.addObject("user", "admin");

					modelAndView.addObject("matchList", resList);
					modelAndView.setViewName("home");
					modelAndView.addObject("teamsuccess",
							"Successfully Added Team");
				}
			}
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/teams")
	public ModelAndView showTeams(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		List<Team> resList = teamServiceImpl.getAllTeams();

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("userObj") != null) {
			
			modelAndView.addObject("teams",resList);
			modelAndView.setViewName("team");
		}
		return modelAndView;
	}
	
}
