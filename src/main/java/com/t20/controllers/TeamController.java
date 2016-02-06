package com.t20.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.t20.models.Team;
import com.t20.models.User;
import com.t20.service.impl.MatchServiceImpl;
import com.t20.service.impl.TeamServiceImpl;
import com.t20.util.Validations;
@Controller
public class TeamController {

	TeamServiceImpl teamServiceImpl = new TeamServiceImpl();
	MatchServiceImpl matchServiceImpl = new MatchServiceImpl();
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

	@RequestMapping(value = "/newTeam", method = RequestMethod.POST)
	public ModelAndView addTeam(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		List<HashMap<String, String>> resList = null;

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");

		String teamName = request.getParameter("teamName");
		String teamCode = request.getParameter("teamCode");
		String captain = request.getParameter("captain");
		
		if (Validations.isNullorEmpty(teamName, teamCode, captain)) {
			return modelAndView;
		}

		if (user.getIsAdmin().equals("Y")) {
			Team team = new Team();

			team.setTeamName(teamName);
			team.setTeamCode(teamCode);
			team.setCaptain(captain);
			teamServiceImpl.addTeam(team);

			resList = matchServiceImpl.getAllMatchesForAdmin();
			modelAndView.addObject("user", "admin");

			modelAndView.addObject("matchList", resList);
			modelAndView.setViewName("home");
			modelAndView.addObject("teamsuccess", "Successfully Added Team");
		}
		

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/teams")
	public ModelAndView showTeams(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		List<Team> resList = teamServiceImpl.getAllTeams();

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}

		modelAndView.addObject("teams", resList);
		modelAndView.setViewName("team");

		return modelAndView;
	}
	
}
