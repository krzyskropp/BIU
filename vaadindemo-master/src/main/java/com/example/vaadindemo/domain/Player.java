package com.example.vaadindemo.domain;

import java.util.Date;

public class Player {

	private String firstname;
	private String lastname;
	private Date year;
	private String team;
	
	public Player(String firstname, String lastname, Date string, String team){
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = string;
		this.team = team;
	}

	
	@Override
	public String toString() {
		return "Player [ firstname=" + firstname + ", lastname="
				+ lastname + ", year=" + year + ", team=" + team + "]";
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date date) {
		this.year = date;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
