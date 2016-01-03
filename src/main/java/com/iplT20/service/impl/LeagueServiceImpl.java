package com.iplT20.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.iplT20.models.League;
import com.iplT20.models.LeagueUser;
import com.iplT20.models.LeagueUserMatch;
import com.iplT20.models.Match;
import com.iplT20.models.User;
import com.iplT20.service.LeagueService;
import com.iplT20.util.HibernateUtil;

public class LeagueServiceImpl implements LeagueService{

	@SuppressWarnings("unchecked")
	public List<League> getAllLeague() {
		Session session = HibernateUtil.getInstance().getSession();
		Query q = session.createQuery("from League");
		List<League> leagueList= q.list();
		session.close();
		return leagueList;
	}

	@SuppressWarnings("unchecked")
	public List<LeagueUser> getLeaguesAndBalanceForUser(int userId) {
		Session session = HibernateUtil.getInstance().getSession();
		Query q = session.createQuery("from LeagueUser where user_id = :userId");
		q.setParameter("userId", userId);
		List<LeagueUser> leagueUserList= q.list();
		session.close();
		return leagueUserList;
	}

	@SuppressWarnings("unchecked")
	public boolean addNewLeague(String leagueName, int userId, String randomCode) {
		boolean saveFlag = false;
		Session session = HibernateUtil.getInstance().getSession();
		Transaction transaction  = session.beginTransaction();
		League league = new League();
		LeagueUser leagueUser = new LeagueUser();
		User u = (User)session.get(User.class, userId);
		
		league.setRandomCode(randomCode);
		league.setLeague_name(leagueName);
		league.setUser(u);
		
		Query q = session.createQuery("from League where user_id = :userId");
		q.setParameter("userId", userId);
		if(q.list().size()==0){
			leagueUser.setLeague(league);
			leagueUser.setUser(u);
			leagueUser.setAvailable_balance(8100);
			session.save(league);
			session.save(leagueUser);
			
			// Add All Matches For User for this League
			Query qq = session.createQuery("from Match where id >=31");
			List<Match> matchesList = qq.list();
			for (int i=0;i<matchesList.size();i++){
				LeagueUserMatch lum = new LeagueUserMatch();
				lum.setLeague(league);
				lum.setMatch((Match)matchesList.get(i));
				lum.setPrediction("NOT PREDICTED YET");
				lum.setUser(u);
				lum.setPlayAmount(0);
				lum.setBidFlag(0);
				session.save(lum);
			}
			transaction.commit();
			saveFlag = true;
		}
		session.close();
		return saveFlag;
	}

	@SuppressWarnings("unchecked")
	public List<LeagueUser> getAllUsersForLeague(int leagueId) {
		Session session = HibernateUtil.getInstance().getSession();
		
		Query q = session.createQuery("from LeagueUser where league_id = :leagueId order by available_balance ASC");
		q.setParameter("leagueId", leagueId);
		List<LeagueUser> leagueUsers = q.list(); 
		session.close();
		return leagueUsers;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String,String>> getOthersBidAndPlayAmount(int leagueId, int matchId) {
		Session session = HibernateUtil.getInstance().getSession();
		List<HashMap<String,String>> finalResult = new ArrayList<HashMap<String,String>>();
		Query q = session.createSQLQuery("select ucase(concat(u.firstName,' ', u.lastName)) name, playAmount, prediction "
				+ "from league_user_match lu inner join user u on u.id = lu.user_id "
				+ "where league_id = :leagueId and match_id = :matchId order by prediction");
		
		q.setParameter("leagueId", leagueId);
		q.setParameter("matchId", matchId);
		
		List<Object> leagueUsers = q.list(); 
		
		for (Object tableData : leagueUsers) {
			Object[] projection = (Object[]) tableData;
			
			HashMap<String, String> result = new HashMap<String, String>();
			
			result.put("name", (String)projection[0].toString());
			result.put("playAmount", (String) projection[1].toString());
			result.put("prediction", (String) projection[2].toString());
			finalResult.add(result);
		}
		session.close();
		return finalResult;
	}

	@SuppressWarnings("unchecked")
	public boolean joinLeague(int leagueId, String randomCode, int userId) {
		boolean joinFlag = false;
		Session session = HibernateUtil.getInstance().getSession();
		Transaction transaction  = session.beginTransaction();
		
		LeagueUser leagueUser = new LeagueUser();
		League l = (League)session.get(League.class, leagueId);
		User u = (User)session.get(User.class, userId);
		
		leagueUser.setAvailable_balance(8100);
		leagueUser.setLeague(l);
		leagueUser.setUser(u);
		
		if(l.getRandomCode().equals(randomCode)){
			session.save(leagueUser);
			// Add All Matches For User for this League
			Query qq = session.createQuery("from Match where id >=31");
			List<Match> matchesList = qq.list();
			for (int i=0;i<matchesList.size();i++){
				LeagueUserMatch lum = new LeagueUserMatch();
				lum.setLeague(l);
				lum.setMatch((Match)matchesList.get(i));
				lum.setPrediction("NOT PREDICTED YET");
				lum.setUser(u);
				lum.setPlayAmount(0);
				lum.setBidFlag(0);
				session.save(lum);
			}
			transaction.commit();
			joinFlag = true;
		}
		
		session.close();
		return joinFlag;
	}

	public boolean checkLeagueEnrollmentForUser(int userId, int leagueId) {
		Session session = HibernateUtil.getInstance().getSession();
		boolean joinFlag = false;
		Query q = session.createQuery("from LeagueUser where user_id = :userId and league_id = :leagueId");
		q.setParameter("userId", userId);
		q.setParameter("leagueId", leagueId);
		
		if(q.list().size()==0){
			joinFlag= true;
		}
		session.close();
		return joinFlag;
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getLeagueMatchesForUser(int userId,
			int leagueId) {
		Session session = HibernateUtil.getInstance().getSession();
		//Query q = session.createQuery("from LeagueUserMatch where league_id = :leagueId and user_id = :userId");
		Query q = session.createSQLQuery("select l.bidFlag,playAmount,prediction,m.id,m.matchDetails,m.matchPlayDate,"
				+ "m.team1,m.team2,ms.status,l.league_id from league_user_match l inner join iplmatch m on "
				+ "(m.id = l.match_id) inner join match_status ms on (ms.match_id = m.id) where l.league_id = :leagueId and l.user_id = :userId");
		q.setParameter("leagueId", leagueId);
		q.setParameter("userId", userId);
		//List<LeagueUserMatch> leagueMatchList = q.list();
		List<Object> resultList = q.list();
		List<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();
		
		for (Object tableData : resultList) {
			Object[] projection = (Object[]) tableData;
			HashMap<String, String> result = new HashMap<String, String>();
			
			result.put("bidFlag", String.valueOf((Integer) projection[0]));
			result.put("playAmount", (String) projection[1].toString());
			result.put("prediction", (String) projection[2].toString());
			result.put("id", (String) projection[3].toString());
			result.put("matchDetails", (String) projection[4].toString()
					.toUpperCase().replaceAll("VS", "  V/S  "));		
			
			Calendar tempCal = Calendar.getInstance();
			tempCal.setTime((Date) projection[5]);
			result.put("matchPlayDate", tempCal.getTime().toString()
					.replaceAll("IST", "").replaceAll(":00 ", "")
					.replaceAll("2015", "").replaceAll("UTC", ""));
			result.put("team1", (String) projection[6]);
			result.put("team2", (String) projection[7]);
			result.put("status", (String) projection[8]);
			result.put("league_id", (String) projection[9].toString());
			
			// Check for Enable Disable Status
			DateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Calendar currTime = Calendar.getInstance();
			// Modified For Amazon Server
			currTime.add(Calendar.HOUR, 5);
			currTime.add(Calendar.MINUTE, 30);
			// Modified For Amazon Server
			String currDateString = formatter.format(currTime.getTime());
			Date matchdate = null;
			Date currdate = null;
			try {
				matchdate = (Date) formatter.parse(String
						.valueOf((Date) projection[5]));
				Calendar cal = Calendar.getInstance();
				cal.setTime(matchdate);
				cal.add(Calendar.MINUTE, -15);
				matchdate = cal.getTime();
				currdate = (Date) formatter.parse(currDateString);
			} catch (ParseException e) {
			}
			if (currdate.after(matchdate)) {
				result.put("en", "d");
			} else {
				result.put("en", "e");
			}
			maplist.add(result);
		}
		session.close();
		return maplist;
	}

	public boolean saveBidForUser(int userId, int leagueId, int matchId, int playAmount, String prediction) {
		boolean bidSaveFlag = false;
		Session session = HibernateUtil.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		Query q = session.createQuery("from LeagueUserMatch where user_id = :userId and league_id = :leagueId and match_id = :matchId");
		q.setParameter("userId", userId);
		q.setParameter("leagueId", leagueId);
		q.setParameter("matchId", matchId);
		
		LeagueUserMatch singleMatchRecord = (LeagueUserMatch)q.list().get(0);
		
		q = session.createQuery("from LeagueUser where user_id = :userId and league_id = :leagueId");
		q.setParameter("userId", userId);
		q.setParameter("leagueId", leagueId);
		
		LeagueUser sinLeagueUser = (LeagueUser)q.list().get(0);
		if(sinLeagueUser.getAvailable_balance()-playAmount>=0){
		
			sinLeagueUser.setAvailable_balance(sinLeagueUser.getAvailable_balance()-playAmount);

			singleMatchRecord.setBidFlag(1);
			singleMatchRecord.setPlayAmount(playAmount);;
			singleMatchRecord.setPrediction(prediction);
		
			session.update(sinLeagueUser);
			session.update(singleMatchRecord);
		
			transaction.commit();
			bidSaveFlag = true;
		}
		session.close();
		return bidSaveFlag;
	}

	public int getAvailableBalanceForUserForLeague(int userId, int leagueId) {
		Session session = HibernateUtil.getInstance().getSession();
		Query q = session.createQuery("from LeagueUser where user_id = :userId and league_id = :leagueId");
		q.setParameter("userId", userId);
		q.setParameter("leagueId", leagueId);
		int amt = ((LeagueUser)q.list().get(0)).getAvailable_balance();
		session.close();
		return amt;
	}

	public String getLeagueName(int leagueId) {
		Session session = HibernateUtil.getInstance().getSession();
		League l = (League)session.get(League.class, leagueId);
		session.close();
		return l.getLeague_name();
	}

	@SuppressWarnings("unchecked")
	public String getBidStats(int leagueId, int matchId, String team) {
		Session session = HibernateUtil.getInstance().getSession();
		Query q = session.createSQLQuery("select CAST(COALESCE(sum(playAmount),0) AS CHAR(50)) as playAmount from league_user_match where league_id = :leagueId and match_id = :matchId and prediction = :team");
		q.setParameter("leagueId", leagueId);
		q.setParameter("matchId", matchId);
		q.setParameter("team", team);
		
		List<String> playAmt = q.list();
		String amount= playAmt.get(0);
		session.close();
		return amount;
	}
}
