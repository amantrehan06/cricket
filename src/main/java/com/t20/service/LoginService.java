package com.t20.service;

import com.t20.models.User;

public interface LoginService {
	
	public User loginAuthentication(String userEmail, String userPassword);
}
