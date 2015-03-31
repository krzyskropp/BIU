package com.example.vaadindemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadindemo.domain.Player;

public class PlayerManager {

	private List<Player> db = new ArrayList<Player>();
	
	public void addPlayer(Player player){
		Player p = new Player(player.getFirstname(), player.getLastname(), player.getYear(), player.getTeam());
			
		db.add(p);
	}
	
	public List<Player> findAll() {
		return db;
	}
	
	public void delete(Player player){
		Player toRemove = null;
		for (Player p: db) {
			if (p.getLastname().compareTo(player.getLastname()) == 0 && p.getTeam().compareTo(player.getTeam()) == 0){
				toRemove = p;
				break;
			}
		}
		db.remove(toRemove);
	}
	
	public void update(Player player){

			Player toUpdate = null;
			
			for (Player p: db) {
				if (p.getFirstname().compareTo(player.getFirstname()) == 0 && p.getLastname().compareTo(player.getLastname()) == 0){
					p.setFirstname(player.getFirstname());
					p.setLastname(player.getLastname());
					p.setYear(player.getYear());
					p.setTeam(player.getTeam());
					
					toUpdate = p;
					break;
				}

			}
			
			db.add(toUpdate);
		}
				
}
