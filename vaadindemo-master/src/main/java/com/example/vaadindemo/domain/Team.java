package com.example.vaadindemo.domain;

public class Team {

	private String name;
	private String coach;
	private int year;
	private String league;
	
	
	public Team(String name, String coach, int year, String league) {
		super();
		this.name = name;
		this.coach = coach;
		this.year = year;
		this.league = league;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", coach=" + coach + ", year=" + year
				+ ", league=" + league + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}
	
	
	
}
