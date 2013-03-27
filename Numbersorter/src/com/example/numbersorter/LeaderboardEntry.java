package com.example.numbersorter;

import java.io.Serializable;

public class LeaderboardEntry implements Serializable, Comparable {

	private int moveCount;
	private String name;
	private int boardSize;

	public LeaderboardEntry(String name, int moveCount) {

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
