package com.example.numbersorter;

import java.io.Serializable;

/**
 * wrapper for the saved games. Stores move count, grid arrangement and it's height and width
 * @author Ricky
 *
 */
public class SavedGame implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int[][] grid;
	private int moveCount, height, width;
	
	public SavedGame(int[][] grid, int movecount, int height, int width){
		
		this.grid = grid;
		this.moveCount = movecount;
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * returns the height of the grid
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * returns the width of the grid
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * returns the 2 dimensional array representing the grid
	 * @return
	 */
	public int[][] getGrid(){
		
		return grid;
	}
	
	/**
	 * returns the saved movecount
	 * @return
	 */
	public int getMoveCount(){
		
		return moveCount;
	}

}
