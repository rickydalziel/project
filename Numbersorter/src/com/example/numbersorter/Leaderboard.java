package com.example.numbersorter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import android.view.View;

public class Leaderboard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<LeaderboardEntry> entries;
	private LeaderboardEntry lastAdded;
	
	public Leaderboard(){
		
		entries = new ArrayList<LeaderboardEntry>();
	}
	
	public void addEntry(LeaderboardEntry entry){
		
		entries.add(entry);
		lastAdded = entry;
		Collections.sort(entries);
		
	}
	
	public ArrayList<LeaderboardEntry> getEntries(){
		
		return entries;
		
	}
	
	public LeaderboardEntry getLastAdded(){
		
		return lastAdded;
	}
	


}