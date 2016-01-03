package com.iplT20.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.iplT20.models.Match;
import com.iplT20.models.User;
import com.iplT20.models.UserMatch;
import com.iplT20.service.RegistrationService;
import com.iplT20.util.HibernateUtil;

public class RegistrationServiceImpl implements RegistrationService {

	@SuppressWarnings("unchecked")
	public boolean registerNewUser(User user) {
		Session session = HibernateUtil.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		Query query1 = session
				.createQuery("from User where email_id = :email_id");
		query1.setParameter("email_id", user.getEmail_id());
		Query query2 = session
				.createQuery("from User where emp_id = :emp_id");
		query2.setParameter("emp_id", user.getEmp_id());
		
		List<User> result1 = query1.list();
		List<User> result2 = query2.list();
		if(result1.size()==0 && result2.size()==0){
			user.setIsAdmin("N");
			session.save(user);
			// Now Save User's Match Table;
			Query q1 = session.createQuery("from User where email_id = :email_id");
			q1.setParameter("email_id", user.getEmail_id());
			List<User> userList = q1.list();
			
			Query q = session.createQuery("from Match");
			List<Match> matchesList = q.list();
			for (int i=0;i<matchesList.size();i++){
				UserMatch um = new UserMatch();
				um.setMatch((Match)matchesList.get(i));
				um.setPredictedStatus("NOT PREDICTED YET");
				um.setUser((User)userList.get(0));
				session.save(um);
			}
			// Now Save User's Match Table;
			transaction.commit();
			session.close();
			return true;
		}else{
			return false;
		}
	}
}
