package com.example.numbersorter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import android.view.View;

/**
 * Class which holds all the leaderboard entries
 * 
 * @author Ricky
 *
 */
public class Leaderboard implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<LeaderboardEntry> entries;
	private LeaderboardEntry lastAdded;

	
	public Leaderboard(){
		
		entries = new ArrayList<LeaderboardEntry>();
	}
	
	/**
	 * adds an entry to the leaderboard  and then sorts the arraylist
	 * also keeps track of the last entry added.
	 * 
	 * @param entry The leaderboard entry to be added
	 */
	public void addEntry(LeaderboardEntry entry){
		
		entries.add(entry);
		lastAdded = entry;
		Collections.sort(entries);
		
	}
	
	/**
	 * returns the leaderboard
	 * @return
	 */
	public ArrayList<LeaderboardEntry> getEntries(){
		
		return entries;
		
	}
	
	/**
	 * returns the entry that was last added.
	 * 
	 * @return
	 */
	public LeaderboardEntry getLastAdded(){
		
		return lastAdded;
	}
	
	

}
