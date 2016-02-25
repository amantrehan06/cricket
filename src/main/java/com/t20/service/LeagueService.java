package com.t20.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.t20.models.League;
import com.t20.models.LeagueUser;


public interface LeagueService {
	
	public List<League> getAllLeague();
	public List<LeagueUser> getLeaguesAndBalanceForUser(int userId);
	public boolean addNewLeague(String leagueName,int userId, String randomCode);
	public List<LeagueUser> getAllUsersForLeague(int leagueId);
	public boolean joinLeague(int leagueId, String randomCode, int userId);
	public boolean checkLeagueEnrollmentForUser(int userId,int leagueId);
	public List<HashMap<String, String>> getLeagueMatchesForUser(int userId, int leagueId);
	public boolean saveBidForUser(int userId, int leagueId, int matchId, int playAmount, String prediction);
	public int getAvailableBalanceForUserForLeague(int userId, int leagueId);
	public String getLeagueName(int leagueId);
	public String getBidStats(int leagueId, int matchId, String team);
	public List<HashMap<String,String>> getOthersBidAndPlayAmount(int leagueId, int matchId);

}
