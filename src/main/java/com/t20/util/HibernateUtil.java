package com.t20.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
/*
	private static HibernateUtil hibernateUtil;

	private HibernateUtil() {

	}
	
	public static HibernateUtil getInstance() {
		if (hibernateUtil == null) {
			hibernateUtil = new HibernateUtil();
		}
		return hibernateUtil;
	}*/

	@Autowired
	private SessionFactory sessionFactory;
   // private static ServiceRegistry serviceRegistry;
	
	private SessionFactory createSessionFactory() {
/*
		if(sessionFactory ==null) {
			Configuration configuration = new Configuration();
        	configuration.configure();
        	serviceRegistry = new ServiceRegistryBuilder().applySettings(
        			configuration.getProperties()).buildServiceRegistry();
        	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}*/
		return sessionFactory;
	}

	public Session getSession() {
		return createSessionFactory().openSession();
	}
	
	public void closeSession() {
		createSessionFactory().close();
	}

}
