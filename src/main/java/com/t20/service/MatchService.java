package com.t20.service;

import java.util.HashMap;
import java.util.List;

import com.t20.models.Match;

public interface MatchService {
	
	// For User
	public boolean saveMatchPrediction(int id, String response, int userId);
	public List<HashMap<String, String>> getAllMatchesForUser(int userId);
	public List<HashMap<String, String>> getPredictionsForOthers(String matchId);
	
	
	// For Administrator
	public List<HashMap<String, String>> getAllMatchesForAdmin();
	public void addNewMatch(Match match);
	public void saveMatchResult(int id, String response);
	public void adjustPreddictions(int matchId);

}
