package com.iplT20.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iplT20.models.Match;
import com.iplT20.models.User;
import com.iplT20.service.impl.MatchServiceImpl;
import com.iplT20.util.Validations;

@Controller
public class MatchController {
	private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

	MatchServiceImpl matchServiceImpl = new MatchServiceImpl();

	@RequestMapping(value = "/prediction", method = RequestMethod.GET, params = {"resp", "match" })
	public ModelAndView saveUserPrediction(@RequestParam String resp,@RequestParam String match, HttpServletRequest httpRequest) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (httpRequest.getSession(false) == null || httpRequest.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		
		User user = (User) httpRequest.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			if (matchServiceImpl.saveMatchPrediction(Integer.parseInt(match), resp, user.getId())) {
				List<HashMap<String, String>> maplist = matchServiceImpl.getAllMatchesForUser(user.getId());
				modelAndView.addObject("matchList", maplist);
				modelAndView.setViewName("redirect:/home");
			} else {
				modelAndView.setViewName("redirect:/logout");
			}
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/newMatch")
	public ModelAndView addMatch(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (httpRequest.getSession(false) == null || httpRequest.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) httpRequest.getSession(false).getAttribute("userObj");

		String matchDetails = httpRequest.getParameter("matchDetails");
		String matchPlayDate = httpRequest.getParameter("matchPlayDate");
		String team1 = httpRequest.getParameter("team1");
		String team2 = httpRequest.getParameter("team2");

		if (Validations.isNullorEmpty(matchDetails, matchPlayDate, team1, team2)) {
			return modelAndView;
		}

		if (user.getIsAdmin().equals("Y")) {
			Match newMatch = new Match();

			DateFormat formatter;
			Date date = null;
			try {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = (Date) formatter.parse(matchPlayDate);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception !!", e);
			}
			newMatch.setMatchPlayDate(date);
			newMatch.setMatchDetails(matchDetails);
			newMatch.setTeam1(team1);
			newMatch.setTeam2(team2);

			matchServiceImpl.addNewMatch(newMatch);

			List<HashMap<String, String>> maplist = matchServiceImpl.getAllMatchesForAdmin();

			modelAndView.addObject("matchList", maplist);
			modelAndView.setViewName("redirect:/home");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/saveMatchResult", method = RequestMethod.GET, params = {
			"resp", "match" })
	public ModelAndView saveMatchResult(@RequestParam String resp,@RequestParam String match, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}

		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("Y")) {
			matchServiceImpl.saveMatchResult(Integer.parseInt(match), resp);
			List<HashMap<String, String>> maplist = matchServiceImpl.getAllMatchesForAdmin();
			modelAndView.addObject("matchList", maplist);
			modelAndView.setViewName("redirect:/home");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/allPredictions", method = RequestMethod.GET, params = {"matchId"})
	public ModelAndView showFriendsPredictions(@RequestParam String matchId, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			try {
				Integer.parseInt(matchId);
				List<HashMap<String, String>> maplist = matchServiceImpl.getPredictionsForOthers(matchId);

				int totalCount = maplist.size();
				int nPCount = 0;
				int team1Count = 0;
				int team2Count = 0;

				String match = maplist.get(0).get("details");
				String date = maplist.get(0).get("playDate");
				String team1 = match.split(" ")[0];
				String team2 = match.split(" ")[2];

				for (HashMap<String, String> tempMap : maplist) {
					if (tempMap.get("prediction").equals(team1)) {
						team1Count++;
					}
					if (tempMap.get("prediction").equals("NOT PREDICTED YET")) {
						nPCount++;
					}
				}
				team2Count = totalCount - (team1Count + nPCount);
				modelAndView.addObject("t1c", (float) (team1Count * 100) / totalCount);
				modelAndView.addObject("t2c", (float) (team2Count * 100) / totalCount);
				modelAndView.addObject("np", (float) (nPCount * 100) / totalCount);
				modelAndView.addObject("match", match);
				modelAndView.addObject("date", date);
				modelAndView.addObject("t1", team1);
				modelAndView.addObject("t2", team2);
				modelAndView.addObject("predictionList", maplist);
				modelAndView.setViewName("allPredictions");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception !!", e);
			}
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/adjust", method = RequestMethod.POST)
	public ModelAndView adjustPredictionForLeague(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");
		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("Y")) {
			matchServiceImpl.adjustPreddictions(Integer.parseInt(request.getParameter("adjust")));
			modelAndView.setViewName("redirect:/home");
		}
		
		return modelAndView;
	}
}
