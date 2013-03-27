package com.example.numbersorter;

import java.io.Serializable;

/**
 * Wrapper for the data needed for each entry in the leaderboard
 * 
 * @author Ricky
 *
 */
public class LeaderboardEntry implements Serializable, Comparable {

	private int moveCount;
	private String name;
	private int boardSize;

	public LeaderboardEntry(String name, int moveCount, int boardSize) {

		this.moveCount = moveCount;
		this.name = name;
		this.boardSize = boardSize;
	}

	/**
	 * returns the movecount 
	 * 
	 * @return
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * returns the entry name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * compares two leaderboard entries, sorts them by board size then move count.
	 */
	public int compareTo(Object another) {

		if (!(another instanceof LeaderboardEntry)) {

			throw new ClassCastException("A Leaderboard entry expected");
		}

		LeaderboardEntry compared = (LeaderboardEntry) another;
		
		if (compared.getBoardSize() == boardSize) {
			return moveCount - compared.getMoveCount();
		}
		else return boardSize - compared.boardSize;
	}

	public int getBoardSize() {

		return boardSize;
	}

}
