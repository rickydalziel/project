package com.example.numbersorter;

import java.io.Serializable;

public class SavedGame implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] grid;
	private int moveCount, height, width;
	
	public SavedGame(int[][] grid, int movecount, int height, int width){
		
		this.grid = grid;
		this.moveCount = movecount;
		this.width = width;
		this.height = height;
		
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int[][] getGrid(){
		
		return grid;
	}
	
	public int getMoveCount(){
		
		return moveCount;
	}

}
