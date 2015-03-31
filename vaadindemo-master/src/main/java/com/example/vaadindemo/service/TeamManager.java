package com.example.vaadindemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadindemo.domain.Team;

public class TeamManager {

private List<Team> db = new ArrayList<Team>();
	
	public void addTeam(Team Team){
		Team p = new Team(Team.getCoach(), Team.getName(), Team.getYear(), Team.getLeague());
		db.add(p);
	}
	
	public List<Team> findAll() {
		return db;
	}
	
	public void delete(Team Team){
		Team toRemove = null;
		for (Team p: db) {
			if (p.getName().compareTo(Team.getName()) == 0){
				toRemove = p;
				break;
			}
		}
		db.remove(toRemove);
	}
	
	public void update(Team Team){
		Team p = new Team(Team.getCoach(), Team.getName(), Team.getYear(), Team.getLeague());
		db.add(p);
		
		Team toEdit = null;
		for (Team p2: db) {
			if (p2.getName().compareTo(Team.getName()) == 0){
				toEdit = p2;
				break;
			}
		}
		db.remove(toEdit);
		db.add(p);
	}
}
