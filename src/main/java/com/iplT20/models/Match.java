package com.iplT20.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "iplmatch")
public class Match implements Serializable{

	private static final long serialVersionUID = 9050138419409107828L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String team1;
	
	@Column(nullable=false)
	private String team2;
	
	@Column(nullable=false)
	private String matchDetails;
	
	@Column(nullable=false)
	@Type(type="timestamp")
	private Date matchPlayDate;
	
	@Column
	private int matchNumber; 

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatchDetails() {
		return matchDetails;
	}

	public void setMatchDetails(String matchDetails) {
		this.matchDetails = matchDetails;
	}

	public Date getMatchPlayDate() {
		return matchPlayDate;
	}

	public void setMatchPlayDate(Date matchPlayDate) {
		this.matchPlayDate = matchPlayDate;
	}

	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

}
