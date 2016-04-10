package com.t20.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t20.service.ResultService;
import com.t20.util.HibernateUtil;
@Service
public class ResultServiceImpl implements ResultService{
	@Autowired HibernateUtil hibernateUtil;
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> showResultForUser(int userid) {
		Session session = hibernateUtil.getSession();
		List<HashMap<String, String>> listOfMap = new ArrayList<HashMap<String,String>>();
		
		HashMap<String,String> resMap = new HashMap<String, String>(); 
		Query q = session.createSQLQuery("select count(*) from user_match u inner join match_status "
				+ "m on m.status = u.predictedStatus and u.user_id =:id and m.match_id=u.match_id and m.status <> 'NULL'");
		q.setParameter("id", userid);
		List<Object>  resScore = q.list();
		
		q = session.createSQLQuery("select COALESCE(count(*),0) np from user_match u inner join match_status m on m.match_id=u.match_id and m.status <> 'NULL' and u.user_id = :id and "
				+ "u.predictedStatus = 'NOT PREDICTED YET'");
		q.setParameter("id", userid);
		List<Object>  resNpScore = q.list();
		
		q = session.createSQLQuery("select count(*) from match_status where status <> 'NULL'");
		List<Object>  resTotal = q.list();
		
		session.close();
		
		BigInteger total = new BigInteger(resTotal.get(0).toString());
		BigInteger win = new BigInteger(resScore.get(0).toString());
		BigInteger np = new BigInteger(resNpScore.get(0).toString());
		BigInteger npAndWin = np.add(win);
		BigInteger loss = total.subtract(npAndWin);
		
		resMap.put("total", total.toString());
		resMap.put("win", win.toString());
		resMap.put("np", np.toString());
		resMap.put("loss", loss.toString());
		float ac = (Float.parseFloat(win.toString())/(Float.parseFloat(total.subtract(npAndWin).toString())+Float.parseFloat(win.toString())))*100F;
		resMap.put("ac", String.valueOf(Math.round(ac)));
		listOfMap.add(resMap);
		
		return listOfMap;
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> showAllResult0() {
		Session session = hibernateUtil.getSession();
		List<HashMap<String, String>> listOfMap = new ArrayList<HashMap<String,String>>();
		Query q = session.createSQLQuery("select q.win, u.emp_id,ucase(u.firstName) firstName,ucase(u.lastName) lastName,u.id, q.points from "
				+ "(select count(*) win,user_id id, SUM(ipl.points) points from user_match u inner join match_status m inner join iplmatch ipl "
				+ "on m.status = u.predictedStatus and m.match_id=u.match_id and ipl.id = u.match_id and m.status <> 'NULL' group by user_id) as q right outer "
				+ "join user u on u.id = q.id and u.isAdmin ='N' order by 6 DESC,1 DESC,3 ASC");
		
		List<Object> res= q.list();
		
		q = session.createSQLQuery("select count(*) from match_status where status <> 'NULL'");
		List<Object>  resTotal = q.list();
		
		BigInteger total = new BigInteger(resTotal.get(0).toString());
		for(Object tableData : res) {
			Object[] columns = (Object[]) tableData;
			HashMap<String,String> result= new HashMap<String, String>();
			
			BigInteger win = new BigInteger(columns[0]==null ? "0" : String.valueOf(columns[0]));
			/*if(String.valueOf(columns[1]).equals("0")){
				continue;
			}*/
			q = session.createSQLQuery("select COALESCE(count(*),0) np from user_match u inner join match_status m on m.match_id=u.match_id and m.status <> 'NULL' and u.user_id = :id and "
					+ "u.predictedStatus = 'NOT PREDICTED YET'");
			q.setParameter("id", Integer.parseInt(String.valueOf(columns[4])));
			
			String points = "0";
			if(columns[5]!=null){
				Double d = (Double)columns[5];
				points = String.valueOf(d.intValue());
			}
			
			result.put("points",points);
			List<Object>  resNpScore = q.list();
			BigInteger np = new BigInteger(resNpScore.get(0).toString());
			BigInteger npAndWin = np.add(win);
			result.put("win",win.toString());
			result.put("empid",String.valueOf(columns[1]));
			result.put("name",String.valueOf(columns[2])+" "+String.valueOf(columns[3]));
			result.put("total", total.toString());
			result.put("np", np.toString());
			//float ac = (Float.parseFloat(win.toString())/(Float.parseFloat(total.subtract(npAndWin).toString())+Float.parseFloat(win.toString())))*100F;
			float ac = (Float.parseFloat(win.toString())/(Float.parseFloat(total.toString())))*100F;
			result.put("ac", String.valueOf(Math.round(ac)));
			result.put("loss", total.subtract(npAndWin).toString());
			
			listOfMap.add(result);
		}
		return listOfMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> showAllResult1() {
		Session session = hibernateUtil.getSession();
		List<HashMap<String, String>> listOfMap = new ArrayList<HashMap<String,String>>();
		Query q = session.createSQLQuery("select q.win, u.emp_id,ucase(u.firstName) firstName,ucase(u.lastName) lastName, q.points from "
				+ "(select count(*) win,user_id id, SUM(ipl.points) points from user_match u inner join match_status m inner join iplmatch ipl "
				+ "on m.status = u.predictedStatus and m.match_id=u.match_id and ipl.id = u.match_id and m.status <> 'NULL' and m.match_id <=28  group by user_id) as q right outer "
				+ "join user u on u.id = q.id and u.isAdmin ='N' order by 5 DESC,1 DESC,3 ASC");
		
		List<Object> res= q.list();
		
		q = session.createSQLQuery("select count(*) from match_status where status <> 'NULL' and match_id <=28 ");
		List<Object>  resTotal = q.list();
		
		BigInteger total = new BigInteger(resTotal.get(0).toString());
		for(Object tableData : res) {
			Object[] columns = (Object[]) tableData;
			HashMap<String,String> result= new HashMap<String, String>();
			
			BigInteger win = new BigInteger(columns[0]==null ? "0" : String.valueOf(columns[0]));
			if(String.valueOf(columns[1]).equals("0")){
				continue;
			}
			result.put("win",win.toString());
			result.put("empid",String.valueOf(columns[1]));
			result.put("name",String.valueOf(columns[2])+" "+String.valueOf(columns[3]));
			result.put("total", total.toString());
			result.put("loss", total.subtract(win).toString());
			result.put("points",String.valueOf(columns[4]));
			
			listOfMap.add(result);
		}
		return listOfMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> showAllResult2() {
		Session session = hibernateUtil.getSession();
		List<HashMap<String, String>> listOfMap = new ArrayList<HashMap<String,String>>();
	/*	Query q = session.createSQLQuery("select q.win, u.emp_id,ucase(u.firstName) firstName,ucase(u.lastName) lastName, q.points points from"
				+ "(select count(*) win,user_id id, SUM(ipl.points) points from user_match u inner join match_status m inner join iplmatch ipl"
				+ "on m.status = u.predictedStatus and m.match_id=u.match_id and ipl.id = u.match_id and m.status <> 'NULL' and m.match_id > 28  group by user_id) as q right outer "
				+ "join user u on u.id = q.id and u.isAdmin ='N' order by 5 DESC,1 DESC,3 ASC");
*/	
		Query q = session.createSQLQuery("select q.win, u.emp_id,ucase(u.firstName) firstName,ucase(u.lastName) lastName, q.points from "
				+ "(select count(*) win,user_id id, SUM(ipl.points) points from user_match u inner join match_status m inner join iplmatch ipl "
				+ "on m.status = u.predictedStatus and m.match_id=u.match_id and ipl.id = u.match_id and m.status <> 'NULL' and m.match_id >28  group by user_id) as q right outer "
				+ "join user u on u.id = q.id and u.isAdmin ='N' order by 5 DESC,1 DESC,3 ASC");
		

		
		List<Object> res= q.list();
		
		q = session.createSQLQuery("select count(*) from match_status where status <> 'NULL' and match_id > 28");
		List<Object>  resTotal = q.list();
		
		BigInteger total = new BigInteger(resTotal.get(0).toString());
		for(Object tableData : res) {
			Object[] columns = (Object[]) tableData;
			HashMap<String,String> result= new HashMap<String, String>();
			
			BigInteger win = new BigInteger(columns[0]==null ? "0" : String.valueOf(columns[0]));
			if(String.valueOf(columns[1]).equals("0")){
				continue;
			}
			result.put("win",win.toString());
			result.put("empid",String.valueOf(columns[1]));
			result.put("name",String.valueOf(columns[2])+" "+String.valueOf(columns[3]));
			result.put("total", total.toString());
			result.put("loss", total.subtract(win).toString());
			result.put("points",String.valueOf(columns[4]));
			
			listOfMap.add(result);
		}
		return listOfMap;
	}

}
