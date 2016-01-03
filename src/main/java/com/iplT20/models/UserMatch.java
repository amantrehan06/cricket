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
@Table(name = "user_match")
public class UserMatch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5220701187811389276L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	@Type(type="int")
	private User user;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	@Type(type="int")
	private Match match;
	
	@Column(nullable=false)
	private String predictedStatus;
	
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

	public String getPredictedStatus() {
		return predictedStatus;
	}

	public void setPredictedStatus(String predictedStatus) {
		this.predictedStatus = predictedStatus;
	}
}
