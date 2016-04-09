package com.t20.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t20.models.League;
import com.t20.models.LeagueUser;
import com.t20.models.LeagueUserMatch;
import com.t20.models.Match;
import com.t20.models.MatchStatus;
import com.t20.models.User;
import com.t20.models.UserMatch;
import com.t20.service.MatchService;
import com.t20.util.HibernateUtil;
@Service
public class MatchServiceImpl implements MatchService {
	@Autowired HibernateUtil hibernateUtil;
	Logger logger = Logger.getLogger(MatchServiceImpl.class);
	
	public boolean saveMatchPrediction(int id, String response, int userId) {

		boolean successflag = false;
		Session session = hibernateUtil.getSession();
		UserMatch um = new UserMatch();
		Transaction tx = session.beginTransaction();
		um.setPredictedStatus(response);

		Match m = (Match) session.get(Match.class, id);
		User u = (User) session.get(User.class, userId);
		um.setMatch(m);		
		um.setUser(u);
		// Check for already Saved Match Status
		Query q = session
				.createQuery("from UserMatch where match_id= :id and user_id =:userid");
		q.setParameter("id", um.getMatch());
		q.setParameter("userid", userId);
		@SuppressWarnings("unchecked")
		List<UserMatch> res = q.list();
		if (res.size() == 0) {
			session.save(um);
		} else {
			UserMatch ums = (UserMatch) res.get(0);
			ums.setPredictedStatus(um.getPredictedStatus());
			// If Condition to Check for Time Validity in case of False Get
			// Request Attack
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar currTime = Calendar.getInstance();
			// Modified For Amazon Server
			currTime.add(Calendar.HOUR, 5);
			currTime.add(Calendar.MINUTE, 30);
			// Modified For Amazon Server
			String currDateString = formatter.format(currTime.getTime());
			Date matchdate = null;
			Date currdate = null;
			try {
				matchdate = (Date) formatter.parse(m.getMatchPlayDate()
						.toString());
				Calendar cal = Calendar.getInstance();
				cal.setTime(matchdate);
				cal.add(Calendar.MINUTE, -15);
				matchdate = cal.getTime();
				currdate = (Date) formatter.parse(currDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			logger.info("Current/Prediction Date: "+currdate +" - Match Date: "+matchdate);
			if (!currdate.after(matchdate)) {
				session.update(ums);
				successflag = true;
				tx.commit();
			} 
		}
		session.close();
		return successflag;
	}

	@SuppressWarnings({ "unchecked" })
	public List<HashMap<String, String>> getAllMatchesForUser(int userId) {
		Session session = hibernateUtil.getSession();

		Query q = session.createSQLQuery("select i.id,i.matchDetails,i.matchPlayDate, "
						+ "i.team1,i.team2,u.predictedStatus status, m.status actual from user_match u inner"
						+ " join iplmatch i on i.id = u.match_id inner join match_status m on m.match_id = u.match_id and u.user_id=:id");

		q.setParameter("id", userId);
		List<Object> resultList = q.list();
		session.close();
		return processMatchRecords(resultList, "Y");
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getAllMatchesForAdmin() {
		Session session = hibernateUtil.getSession();

		List<Object> resultList =session.createCriteria(MatchStatus.class, "ms").createCriteria("ms.match", "Match", JoinType.RIGHT_OUTER_JOIN)
				.setProjection(Projections.projectionList().add(Projections.property("Match.id").as("id"))
								.add(Projections.property("Match.matchDetails").as("matchDetails"))
								.add(Projections.property("Match.matchPlayDate").as("matchPlayDate"))
								.add(Projections.property("Match.team1").as("team1"))
								.add(Projections.property("Match.team2").as("team2"))
								.add(Projections.property("ms.status").as("status"))).list();

		session.close();
		return processMatchRecords(resultList, "N");
	}

	private List<HashMap<String, String>> processMatchRecords(List<Object> list, String userFlag) {

		List<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();

		if (list != null) {
			for (Object tableData : list) {
				Object[] projection = (Object[]) tableData;
				HashMap<String, String> result = new HashMap<String, String>();

				result.put("id", String.valueOf((Integer) projection[0]));
				result.put("matchDetails", (String) projection[1].toString()
						.toUpperCase().replaceAll("VS", "  V/S  "));
				Calendar tempCal = Calendar.getInstance();
				tempCal.setTime((Date) projection[2]);
				// result.put("matchPlayDate",
				// String.valueOf((Date)projection[2]));
				result.put("matchPlayDate", tempCal.getTime().toString()
						.replaceAll("IST", "").replaceAll(":00 ", "")
						.replaceAll("2016", "").replaceAll("UTC", ""));
				result.put("team1", (String) projection[3]);
				result.put("team2", (String) projection[4]);
				result.put("status", (String) projection[5]);
				if (userFlag.equals("Y")) {
					result.put("actual", (String) projection[6]);
				}
				/*if (userFlag.equals("N")
						&& Integer.parseInt(result.get("id")) > 30) {
					result.put("adjustButtonFlag",checkMatchAdjustmentStatus(Integer.parseInt(result.get("id"))));
				}*/
				if (userFlag.equals("N") /*&& Integer.parseInt(result.get("id")) <= 30*/) {
					result.put("adjustButtonFlag", "T");
				}
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
					matchdate = (Date) formatter.parse(String.valueOf((Date) projection[2]));
					Calendar cal = Calendar.getInstance();
					cal.setTime(matchdate);
					cal.add(Calendar.MINUTE, -15);
					matchdate = cal.getTime();
					currdate = (Date) formatter.parse(currDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (currdate.after(matchdate)) {
					result.put("matchEnable", "e");
					result.put("en", "d");
				} else {
					result.put("matchEnable", "d");
					result.put("en", "e");
				}
				if (Integer.parseInt(result.get("id").toString()) <= 30) {
					result.put("c", "1");
				} else {
					result.put("c", "2");
				}
				maplist.add(result);
			}
		}
		return maplist;
	}

	public void addNewMatch(Match match) {
		Session session = hibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(match);
		tx.commit();
		tx = session.beginTransaction();
		MatchStatus ms = new MatchStatus();
		Match m = (Match) session.get(Match.class, match.getId());
		ms.setStatus("NULL");
		ms.setMatch(m);
		session.save(ms);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public void saveMatchResult(int id, String response) {
		Session session = hibernateUtil.getSession();
		Query q = session.createQuery("from MatchStatus where match_id = :id");
		q.setParameter("id", id);
		List<MatchStatus> res = q.list();

		/*
		 * q = session.createQuery("from User"); List<User> userList = q.list();
		 * 
		 * Match m = (Match) session.get(Match.class, id); String mailSubj =
		 * "Result for Match No. " + m.getMatchNumber() + " - " + m.getTeam1() +
		 * " VS " + m.getTeam2(); String mailContent = "Hi User" + "\n\n" +
		 * "The Winner/Result for this match is - " + response + "\n\n" +
		 * "Congratulations to all the winners " +
		 * "for this match prediction. Keep predicting !!" + "\n\n" + "Regards,"
		 * + "\n" + "IPL ADMIN";
		 */

		MatchStatus ms = (MatchStatus) res.get(0);
		ms.setStatus(response);
		Transaction tx = session.beginTransaction();
		session.update(ms);
		// Update All League Users
		if (id >= 31) {
			calculateAndUpdateLeagueBalances(response,
					getLoosingTeamForMatch(id, response), id);
		}
		tx.commit();
		session.close();
		/*
		 * for (int i = 0; i < userList.size(); i++) {
		 * sendMailToAllUSers(((User) userList.get(i)).getEmail_id(),
		 * mailContent, mailSubj); try { Thread.sleep(200); } catch
		 * (InterruptedException e) { e.printStackTrace(); } }
		 */

	}

	/*
	 * private void sendMailToAllUSers(String toAddr, String mailContent, String
	 * mailSubj) { MailUtil.sendEmail(toAddr, mailContent, mailSubj); }
	 */

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getPredictionsForOthers(String matchId) {
		Session session = hibernateUtil.getSession();

		List<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();

		Query q = session
				.createSQLQuery("select i.matchPlayDate , concat(i.team1,' V/S ', i.team2) matchDetails, "
						+ "ucase(concat(u.firstName,' ', u.lastName)) name , um.predictedStatus "
						+ "from user_match um inner join user u on (u.id=um.user_id and u.isAdmin = 'N') "
						+ "right outer join iplmatch i on(i.id=um.match_id) where i.id=:matchId order by 3;");
		q.setParameter("matchId", Integer.parseInt(matchId));
		List<Object> result = q.list();
		session.close();

		for (Object tableData : result) {
			Object[] row = (Object[]) tableData;
			HashMap<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("playDate", row[0].toString());
			resultMap.put("details", row[1].toString());
			resultMap.put("name", row[2].toString());
			resultMap.put("prediction", row[3].toString());

			maplist.add(resultMap);
		}
		return maplist;
	}

	@SuppressWarnings("unchecked")
	public void adjustPreddictions(int matchId) {
		Session session = hibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		LeagueUser luser = null;
		LeagueUserMatch lum = null;
		String userleageMatch = "";
		String mainPrediction = "";
		Query q = session.createQuery("from League");
		List<League> leagues = q.list();
		for (League l : leagues) {
			q = session
					.createSQLQuery("select CAST(id as CHAR(10)) AS id from league_user where league_id = :leagueId");
			q.setParameter("leagueId", l.getId());
			List<String> usersForLeague = q.list();

			for (String leagueUser : usersForLeague) {
				luser = (LeagueUser) session.get(LeagueUser.class,
						Integer.parseInt(leagueUser));
				q = session
						.createSQLQuery("select CAST(id as CHAR(10)) AS id from league_user_match "
								+ "where user_id = :userId and match_id = :matchId and league_id= :leagueId");
				q.setParameter("userId", luser.getUser().getId());
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());

				userleageMatch = q.list().get(0).toString();

				lum = (LeagueUserMatch) session.get(LeagueUserMatch.class,
						Integer.parseInt(userleageMatch));
				if (lum.getBidFlag() == 0) {
					q = session
							.createSQLQuery("select predictedStatus from user_match where user_id = :userId and match_id = :matchId");
					q.setParameter("userId", luser.getUser().getId());
					q.setParameter("matchId", matchId);
					mainPrediction = q.list().get(0).toString();
					if (matchId >= 31 && matchId <= 58) {
						if (luser.getAvailable_balance() >= 200) {
							lum.setPlayAmount(200);
							luser.setAvailable_balance(luser
									.getAvailable_balance() - 200);
						} else {
							lum.setPlayAmount(luser.getAvailable_balance());
							luser.setAvailable_balance(0);
						}
					}
					if (matchId >= 59 && matchId <= 61) {
						if (luser.getAvailable_balance() >= 500) {
							lum.setPlayAmount(500);
							luser.setAvailable_balance(luser
									.getAvailable_balance() - 500);
						} else {
							lum.setPlayAmount(luser.getAvailable_balance());
							luser.setAvailable_balance(0);
						}
					}
					if (matchId == 62) {
						if (luser.getAvailable_balance() >= 1000) {
							lum.setPlayAmount(1000);
							luser.setAvailable_balance(luser
									.getAvailable_balance() - 1000);
						} else {
							lum.setPlayAmount(luser.getAvailable_balance());
							luser.setAvailable_balance(0);
						}
					}
					lum.setBidFlag(1);
					lum.setPrediction(mainPrediction);
					session.update(lum);
					session.update(luser);
				}
			}
		}
		transaction.commit();
		session.close();
	}

	private String getLoosingTeamForMatch(int id, String winningTeam) {
		Session session = hibernateUtil.getSession();
		Match m = (Match) session.get(Match.class, id);
		session.close();
		if (winningTeam.equals("DRAW")) {
			return "DRAW";
		} else {
			if (winningTeam.equals(m.getTeam1())) {
				return m.getTeam2();
			} else {
				return m.getTeam1();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void calculateAndUpdateLeagueBalances(String winningTeam,
			String loosingTeam, int matchId) {
		Session session = hibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		LeagueUser luser = null;
		LeagueUserMatch lum = null;

		Query q = session.createQuery("from League");
		List<League> leagues = q.list();
		for (League l : leagues) {
			if (!winningTeam.equals("DRAW")) {

				q = session
						.createSQLQuery("select CAST(COALESCE(sum(playAmount),0) AS CHAR(10)) as playAmountTotal from league_user_match "
								+ "where prediction = :winingTeam and match_id = :matchId and league_id = :leagueId");
				q.setParameter("winingTeam", winningTeam);
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());
				String winningTotal = q.list().get(0).toString();

				q = session
						.createSQLQuery("select CAST(id AS CHAR(10)) as id from league_user_match "
								+ "where prediction = :winingTeam and match_id = :matchId and league_id = :leagueId");
				q.setParameter("winingTeam", winningTeam);
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());
				List<String> winnersList = q.list();

				// //////////////////////////////////////////////////////////////////////////////////////////////////

				q = session
						.createSQLQuery("select CAST(COALESCE(sum(playAmount),0) AS CHAR(10)) as playAmountTotal from league_user_match "
								+ "where prediction in( :loosingTeam, 'NOT PREDICTED YET') and match_id = :matchId and league_id = :leagueId");
				q.setParameter("loosingTeam", loosingTeam);
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());

				String loosingTotal = q.list().get(0).toString();

				q = session
						.createSQLQuery("select CAST(id AS CHAR(10)) as id from league_user_match "
								+ "where prediction = :winingTeam and match_id = :matchId and league_id = :leagueId");
				q.setParameter("winingTeam", loosingTeam);
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());
				List<String> loosersList = q.list();

				double pointsScaleFactor = 1.0;

				if (Integer.parseInt(loosingTotal) == 0) {
					for (String winner : winnersList) {
						int id = Integer.parseInt(winner);
						lum = (LeagueUserMatch) session.get(
								LeagueUserMatch.class, id);

						q = session
								.createSQLQuery("select CAST(id as CHAR(50)) as id from league_user where user_id = :userId and league_id = :leagueId");
						q.setParameter("userId", lum.getUser().getId());
						q.setParameter("leagueId", lum.getLeague().getId());

						luser = (LeagueUser) session.get(LeagueUser.class,
								Integer.parseInt(q.list().get(0).toString()));
						luser.setAvailable_balance(luser.getAvailable_balance()
								+ lum.getPlayAmount());
						session.save(luser);
					}
				}

				else if (Integer.parseInt(winningTotal) == 0) {
					for (String looser : loosersList) {
						int id = Integer.parseInt(looser);
						lum = (LeagueUserMatch) session.get(
								LeagueUserMatch.class, id);

						q = session
								.createSQLQuery("select CAST(id as CHAR(50)) as id from league_user where user_id = :userId and league_id = :leagueId");
						q.setParameter("userId", lum.getUser().getId());
						q.setParameter("leagueId", lum.getLeague().getId());

						luser = (LeagueUser) session.get(LeagueUser.class,
								Integer.parseInt(q.list().get(0).toString()));
						luser.setAvailable_balance(luser.getAvailable_balance()
								+ lum.getPlayAmount());
						session.save(luser);
					}
				}

				else {
					pointsScaleFactor = (double) Integer.parseInt(loosingTotal)
							/ Integer.parseInt(winningTotal);

					for (String winner : winnersList) {
						int id = Integer.parseInt(winner);
						lum = (LeagueUserMatch) session.get(
								LeagueUserMatch.class, id);

						q = session
								.createSQLQuery("select CAST(id as CHAR(50)) as id from league_user where user_id = :userId and league_id = :leagueId");
						q.setParameter("userId", lum.getUser().getId());
						q.setParameter("leagueId", lum.getLeague().getId());

						luser = (LeagueUser) session.get(LeagueUser.class,
								Integer.parseInt(q.list().get(0).toString()));
						luser.setAvailable_balance(luser.getAvailable_balance()
								+ lum.getPlayAmount()
								+ (int) (lum.getPlayAmount() * pointsScaleFactor));
						session.save(luser);
					}
				}
			}else{
				q = session
						.createSQLQuery("select CAST(id AS CHAR(10)) as id from league_user_match "
								+ "where match_id = :matchId and league_id = :leagueId");
				q.setParameter("matchId", matchId);
				q.setParameter("leagueId", l.getId());
				List<String> winnersList = q.list();
				
				for (String winner : winnersList) {
					int id = Integer.parseInt(winner);
					lum = (LeagueUserMatch) session.get(
							LeagueUserMatch.class, id);

					q = session
							.createSQLQuery("select CAST(id as CHAR(50)) as id from league_user where user_id = :userId and league_id = :leagueId");
					q.setParameter("userId", lum.getUser().getId());
					q.setParameter("leagueId", lum.getLeague().getId());

					luser = (LeagueUser) session.get(LeagueUser.class,
							Integer.parseInt(q.list().get(0).toString()));
					luser.setAvailable_balance(luser.getAvailable_balance()
							+ lum.getPlayAmount());
					session.save(luser);
				}
			}
		}
		transaction.commit();
	}

	private String checkMatchAdjustmentStatus(int id) {
		Session session = hibernateUtil.getSession();
		Query q = session
				.createSQLQuery("select CAST(count(*) as CHAR(20)) as num from league_user_match where "
						+ "bidFlag = 0 and match_id = :matchId");
		q.setParameter("matchId", id);
		int count = Integer.parseInt(q.list().get(0).toString());
		session.close();
		return count == 0 ? "T" : "F";
	}
	
}
