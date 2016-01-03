package com.iplT20.service;

import com.iplT20.models.User;

public interface LoginService {
	
	public User loginAuthentication(String userEmail, String userPassword);
}
