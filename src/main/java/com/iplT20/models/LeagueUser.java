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
@Table(name = "league_user")
public class LeagueUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167844363015906935L;
	
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
	
	@Column(nullable=false)
	private int available_balance;

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

	public int getAvailable_balance() {
		return available_balance;
	}

	public void setAvailable_balance(int available_balance) {
		this.available_balance = available_balance;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}
}
