package com.t20.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.t20.models.Team;
import com.t20.service.TeamService;
import com.t20.util.HibernateUtil;

public class TeamServiceImpl implements TeamService {
	
	@SuppressWarnings("unchecked")
	public void addTeam(Team team) {
		Session session = HibernateUtil.getInstance().getSession();
		Transaction transaction =  session.beginTransaction();
		Query q = session.createQuery("from Team where teamCode = :code");
		q.setParameter("code", team.getTeamCode());
		List<Team> resList = q.list();
		if(resList.size()==0){
			session.save(team);
			transaction.commit();
		}
		session.close();
		
	}

	@SuppressWarnings("unchecked")
	public List<Team> getAllTeams() {
		Session session = HibernateUtil.getInstance().getSession();
		Query q = session.createQuery("from Team");
		List<Team> teams = q.list();  
		session.close();
		return teams;
	}
}
