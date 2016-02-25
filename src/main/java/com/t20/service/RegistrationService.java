package com.t20.service;
import org.springframework.stereotype.Service;

import com.t20.models.User;

public interface RegistrationService {
	
	public boolean registerNewUser(User user);

}
