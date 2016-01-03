package com.iplT20.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "league_user_match")
public class LeagueUserMatch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -415256496886131699L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	@Type(type="int")
	private League league;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	@Type(type="int")
	private User user;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	@Type(type="int")
	private Match match;
	
	@Column
	private String prediction;
	
	@Column
	private int playAmount;
	
	@Column
	private int bidFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public int getPlayAmount() {
		return playAmount;
	}

	public void setPlayAmount(int playAmount) {
		this.playAmount = playAmount;
	}
	
	public int getBidFlag() {
		return bidFlag;
	}

	public void setBidFlag(int bidFlag) {
		this.bidFlag = bidFlag;
	}
}
