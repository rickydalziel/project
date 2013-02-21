package com.example.numbersorter;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {
	
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 0;
	
	private int height, width, moveCount;
	private int[][] grid = null;
	private int[][] solution;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		
		height = extras.getInt("height");
		width = extras.getInt("width");
	
		if(grid == null){
			grid = initialise();	
		}

		
		GameView gameview = new GameView(this);
		setContentView(gameview);
		gameview.requestFocus();
		
	}
	

	
	private int[][] initialise() {
		
		int[][] output = new int[height][width];
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for (int i = 1; i <= height*width; i++){
			
			numbers.add(i);
			
		}
		
		generateSolution();
		
		Random r = new Random();
		
		for (int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				
				int number = numbers.get(r.nextInt(numbers.size()));
				output[i][j] = number;
				numbers.remove(numbers.indexOf(number));
				
			}
			
		}
	
		return output;
	}

	private void generateSolution() {
		
		solution = new int[width][height];
		
		for (int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				
				solution[i][j] = (height * j) + i + 1;
								
			}
		}
		
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	

	public int[][] getGrid() {
		
		return grid;
		
	}

	public boolean checkfinished() {
		
		return (grid == solution);
		
	}

	public void swipe(int direction, int index, int distance) {

		if(direction == VERTICAL){
			
			int[] newColumn = new int[height];
			
			for (int i = 0; i < height; i++){
				 	
				newColumn[(i + distance) % height] = grid[i][index]; 
				
			}
			
			for (int i = 0; i < height; i++){
			 	
				grid[i][index] = newColumn[i]; 
				
			}
	
		}
		else{
			
			int[] row = grid[index];	
			int[] newRow = new int[width];
			for (int i = 0; i < width; i++){
				
				newRow[(i + distance)% width] = row[i];
				
			}
			
			grid[index] = newRow;			
		}
			
	}

}
