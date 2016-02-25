package com.t20.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t20.models.User;
import com.t20.service.LoginService;
import com.t20.util.HibernateUtil;
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired HibernateUtil hibernateUtil;
	@SuppressWarnings("unchecked")
	public User loginAuthentication(String email, String password) {
		
		Session session = hibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from User where email_id =:email_id and password =:password ");
		
		query.setParameter("email_id", email);
		query.setParameter("password",password);
		
		List<User> list = query.list();
		
		tx.commit();
		session.close();
		
		return list.size() ==1 ? (User)list.get(0) : null;
	}

}
