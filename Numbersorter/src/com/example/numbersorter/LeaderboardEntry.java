package com.example.numbersorter;

import java.io.Serializable;

public class LeaderboardEntry implements Serializable, Comparable {
	

	private int moveCount;
	private String name;
	
	public LeaderboardEntry( String name, int moveCount){
		
		this.moveCount = moveCount;
		this.name = name;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public String getName() {
		return name;
	}

	public int compareTo(Object another) {

		if(!(another instanceof LeaderboardEntry)){
			
			throw new ClassCastException("A Leaderboard entry expected");
		}
		
		return moveCount - ((LeaderboardEntry)another).getMoveCount();
	}
	
	
	
	

}
