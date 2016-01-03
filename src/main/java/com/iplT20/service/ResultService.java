package com.iplT20.service;

import java.util.HashMap;
import java.util.List;

public interface ResultService {
	
	public List<HashMap<String, String>> showResultForUser(int userid);
	public List<HashMap<String, String>> showAllResult0();
	public List<HashMap<String, String>> showAllResult1();
	public List<HashMap<String, String>> showAllResult2();

}
