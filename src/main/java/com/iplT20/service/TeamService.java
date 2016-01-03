package com.iplT20.service;

import java.util.List;

import com.iplT20.models.Team;

public interface TeamService {
	
	public void addTeam(Team team);
	public List<Team> getAllTeams();

}
