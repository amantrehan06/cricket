package com.iplT20.controllers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iplT20.models.LeagueUser;
import com.iplT20.models.User;
import com.iplT20.service.impl.LeagueServiceImpl;

@Controller
public class LeagueController {

	LeagueServiceImpl leagueServiceImpl = new LeagueServiceImpl();
	private static final Logger logger = LoggerFactory.getLogger(LeagueController.class);

	@RequestMapping(value = "/league", method = RequestMethod.GET)
	public ModelAndView prepareLeague(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {

			// Get Leagues for User
			modelAndView.addObject("leagueList", leagueServiceImpl.getAllLeague());
			modelAndView.addObject("leagueUserList", leagueServiceImpl.getLeaguesAndBalanceForUser(user.getId()));
			modelAndView.setViewName("league");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/league/new", method = RequestMethod.POST)
	public ModelAndView createLeague(HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			String randomCode = new BigInteger(130, new SecureRandom()).toString(32);
			String message = "";
			String flag = "";
			if (leagueServiceImpl.addNewLeague(request.getParameter("leagueName"), user.getId(), randomCode)) {
				modelAndView.addObject("leagueAddMessage", randomCode);
				message = randomCode;
				flag = "P";
			} else {
				message = "SORRY !! You can Own only ONE league...and join as many..!! ";
				flag = "F";
			}
			// Get Leagues for User
			modelAndView.setViewName("redirect:/league");
			redirectAttributes.addFlashAttribute("leagueAddMessage", message);
			redirectAttributes.addFlashAttribute("cflag", flag);
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/league/members", method = RequestMethod.POST)
	public ModelAndView showLeague(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			// Check if a User is already Part of the selected League
			try {
				String leagueId = request.getParameter("id");
				/*
				 * modelAndView.addObject( "joinFlag",
				 * leagueServiceImpl.checkLeagueEnrollmentForUser( user.getId(),
				 * Integer.parseInt(leagueId)));
				 */
				List<LeagueUser> memList = leagueServiceImpl.getAllUsersForLeague(Integer.parseInt(leagueId));
				modelAndView.addObject("leagueMembersList", memList);
				if (memList != null && !memList.isEmpty()) {
					modelAndView.addObject("leagueName", leagueServiceImpl
							.getAllUsersForLeague(Integer.parseInt(leagueId)).get(0).getLeague().getLeague_name());
				} else {
					modelAndView.addObject("joinFlag", "false");
				}
				modelAndView.addObject("leagueId", leagueId);
				modelAndView.setViewName("leagueMembers");
			} catch (Exception e) {
				logger.error("Exception!! "+e);
				modelAndView.setViewName("redirect:/logout");
			}

		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/league/members", method = RequestMethod.GET)
	public ModelAndView showLeagueGetRequest(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			modelAndView.setViewName("redirect:/league");
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/league/join", method = RequestMethod.POST)
	public ModelAndView joinLeague(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			if (leagueServiceImpl.joinLeague(Integer.parseInt(request.getParameter("leagueId")),
					request.getParameter("randomCode"), user.getId())) {
				modelAndView.setViewName("redirect:/league");
			} else {
				modelAndView.addObject("joinFlag", leagueServiceImpl.checkLeagueEnrollmentForUser(user.getId(),	Integer.parseInt(request.getParameter("leagueId"))));
				List<LeagueUser> memList = leagueServiceImpl.getAllUsersForLeague(Integer.parseInt(request.getParameter("leagueId")));
				modelAndView.addObject("leagueMembersList", memList);
				
				if (memList != null && !memList.isEmpty()) {
					modelAndView.addObject("leagueName",leagueServiceImpl.getAllUsersForLeague(Integer.parseInt(request.getParameter("leagueId"))).get(0).getLeague().getLeague_name());
				} else {
					modelAndView.addObject("joinFlag", "false");
				}
				
				modelAndView.addObject("leagueId", request.getParameter("leagueId"));
				modelAndView.setViewName("leagueMembers");
				modelAndView.addObject("invalid", "INVALID CODE !!");
			}

		}
		return modelAndView;
	}

	@RequestMapping(value = "/league/leagueMatches", method = RequestMethod.POST)
	public ModelAndView showLeagueMatches(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			try {
				String id = request.getParameter("id");
				List<HashMap<String, String>> leagueUserMatchesList = leagueServiceImpl
						.getLeagueMatchesForUser(user.getId(), Integer.parseInt(id));
				modelAndView.addObject("matchList", leagueUserMatchesList);
				modelAndView.addObject("avlBal",
						leagueServiceImpl.getAvailableBalanceForUserForLeague(user.getId(), Integer.parseInt(id)));
				modelAndView.addObject("leagueName", leagueServiceImpl.getLeagueName(Integer.parseInt(id)));
				modelAndView.addObject("league", id);
				modelAndView.setViewName("leagueMatches");
			} catch (Exception e) {
				logger.error("Exception!! "+e);
				modelAndView.setViewName("redirect:/logout");
			}
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/league/leagueMatches", method = RequestMethod.GET)
	public ModelAndView showLeagueMatchesGetReq(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			try {
				modelAndView.setViewName("redirect:/league");
			} catch (Exception e) {
				modelAndView.setViewName("redirect:/logout");
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/league/saveBid", method = RequestMethod.POST)
	public ModelAndView saveLeagueMatchBid(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {

			boolean allowBid = true;
			int val = 0;
			int i = 31;
			while (i <= 62) {
				String matchId = request.getParameter("match" + i);
				if (matchId != null) {
					val = i;
					break;
				}
				i++;
			}
			
			Integer bidAmount = Integer.parseInt(request.getParameter("bidAmt" + val));
			Integer match = Integer.parseInt(request.getParameter("match" + val));
			Integer league = Integer.parseInt(request.getParameter("league" + val));
			String team = request.getParameter("team" + val);
			
			if (bidAmount < 200	&& match >= 31	&& match <= 58) {
				modelAndView.addObject("minAmt", "MINIMUM POINTS FOR THIS MATCH IS 200 POINTS");
				allowBid = false;
			}
			if (bidAmount < 500	&& match >= 59	&& match <= 61) {
				allowBid = false;
				modelAndView.addObject("minAmt", "MINIMUM POINTS FOR THIS MATCH IS 500 POINTS");
			}
			if (bidAmount < 1000 && match == 62) {
				allowBid = false;
				modelAndView.addObject("minAmt", "MINIMUM POINTS FOR THIS MATCH IS 1000 POINTS");
			}

			if (allowBid) {
				if (leagueServiceImpl.saveBidForUser(user.getId(),league,match,bidAmount, team)) {
				} else {
					modelAndView.addObject("failMessage", "INSUFFICIENT POINT BALANCE !!");
				}
			}
			modelAndView.addObject("avlBal", leagueServiceImpl.getAvailableBalanceForUserForLeague(user.getId(),
					Integer.parseInt(request.getParameter("league" + val))));
			List<HashMap<String, String>> leagueUserMatchesList = leagueServiceImpl
					.getLeagueMatchesForUser(user.getId(), Integer.parseInt(request.getParameter("league" + val)));
			modelAndView.addObject("matchList", leagueUserMatchesList);
			modelAndView.addObject("leagueName",
					leagueServiceImpl.getLeagueName(Integer.parseInt(request.getParameter("league" + val))));
			modelAndView.addObject("league", request.getParameter("league" + val));
			modelAndView.setViewName("leagueMatches");
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/league/rules", method = RequestMethod.GET)
	public ModelAndView leagueRules(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
			User user = (User) request.getSession(false)
					.getAttribute("userObj");
			if (user.getIsAdmin().equals("N")) {
				modelAndView.setViewName("leagueRules");
			}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/league/viewBidStats", method = RequestMethod.POST)
	public ModelAndView bidStatistics(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {

			String team1 = request.getParameter("team1");
			String team2 = request.getParameter("team2");
			String match = request.getParameter("match");
			String league = request.getParameter("league");

			String team_1 = leagueServiceImpl.getBidStats(Integer.parseInt(league), Integer.parseInt(match), team1);
			String team_2 = leagueServiceImpl.getBidStats(Integer.parseInt(league), Integer.parseInt(match), team2);

			modelAndView.addObject("team1", team1);
			modelAndView.addObject("team1Amt", team_1);
			modelAndView.addObject("team2", team2);
			modelAndView.addObject("team2Amt", team_2);

			modelAndView.addObject("leagueName", leagueServiceImpl.getLeagueName(Integer.parseInt(league)));
			modelAndView.addObject("leagueId", league);
			modelAndView.addObject("match", match);
			modelAndView.addObject("bids",leagueServiceImpl.getOthersBidAndPlayAmount(Integer.parseInt(league), Integer.parseInt(match)));
			modelAndView.setViewName("bidStatus");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/league/viewBidStats", method = RequestMethod.GET)
	public ModelAndView bidStatisticsGetRequest(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			modelAndView.setViewName("redirect:/league");
		}
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/league/leaderBoard", method = RequestMethod.POST)
	public ModelAndView showLeaderBoard(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			String league = request.getParameter("league");
			List<LeagueUser> leaderBoard = leagueServiceImpl.getAllUsersForLeague(Integer.parseInt(league));

			List<String> finalVal = new ArrayList<String>();
			// int rank = 0;
			// int firstUserAvlBalance =
			// leaderBoard.get(0).getAvailable_balance();
			for (int i = 0; i < leaderBoard.size(); i++) {
				// int avlBal = leaderBoard.get(i).getAvailable_balance();
				// if(avlBal == firstUserAvlBalance && i!=0){
				finalVal.add(i,
						"[" + leaderBoard.get(i).getUser().getFirstName() + " "
								+ leaderBoard.get(i).getUser().getLastName() + "-"
								+ leaderBoard.get(i).getAvailable_balance() + "]");
				// }else{
				// rank++;
				// finalVal.add(i,"["+leaderBoard.get(i).getUser().getFirstName()+"
				// "+leaderBoard.get(i).getUser().getLastName()+"-"+
				// rank+"]");
				// }
				// firstUserAvlBalance=avlBal;
			}
			modelAndView.addObject("sizeList", leaderBoard.size());
			modelAndView.addObject("leagueName", leagueServiceImpl.getLeagueName(Integer.parseInt(league)));
			modelAndView.addObject("leagueId", league);
			modelAndView.addObject("finalList", finalVal);
			modelAndView.setViewName("leaderboard");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/league/leaderBoard", method = RequestMethod.GET)
	public ModelAndView showLeaderBoardGetRequest(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/logout");

		if (request.getSession(false) == null || request.getSession(false).getAttribute("userObj") == null) {
			logger.info("Session is Null, Please relogin");
			return modelAndView;
		}
		User user = (User) request.getSession(false).getAttribute("userObj");
		if (user.getIsAdmin().equals("N")) {
			modelAndView.setViewName("redirect:/league");
		}

		return modelAndView;
	}
}
