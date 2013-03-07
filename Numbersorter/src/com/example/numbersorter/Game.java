package com.example.numbersorter;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Game extends Activity {

	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 0;
	private GameView gameview;

	private int height, width, moveCount;
	private int[][] grid = null;
	private int[][] solution;
	private boolean solvable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		height = extras.getInt("height");
		width = extras.getInt("width");

		if (grid == null) {
			grid = initialise();
		}

		gameview = new GameView(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		checkUnsolveable();
		setContentView(gameview);
		gameview.requestFocus();

	}
	
	private void checkUnsolveable(){
		
		int[] flatGrid = flatten();
		int counter = 0;
		
		for (int i = 0; i < flatGrid.length; i++){			
			for (int j = i+1; j < flatGrid.length; j++){		
				if(flatGrid[i] > flatGrid[j]){
					
					counter++;		
				}
			}		
		}
		
		solvable = (Math.pow(-1, counter) > 0);
	}
	
	public boolean isUnsolvable(){
		
		return solvable;
	}

	private int[][] initialise() {

		int[][] output = new int[height][width];

		ArrayList<Integer> numbers = new ArrayList<Integer>();

		for (int i = 1; i <= height * width; i++) {

			numbers.add(i);

		}

		generateSolution();

		Random r = new Random();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				int number = numbers.get(r.nextInt(numbers.size()));
				output[i][j] = number;
				numbers.remove(numbers.indexOf(number));

			}

		}

		return output;
	}

	private void generateSolution() {

		solution = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				solution[i][j] = (width * i) + j + 1;

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

		if (direction == VERTICAL) {

			int[] newColumn = new int[height];

			for (int i = 0; i < height; i++) {

				newColumn[(i + distance + height) % height] = grid[i][index];

			}

			for (int i = 0; i < height; i++) {

				grid[i][index] = newColumn[i];

			}

		} else {

			int[] newRow = new int[width];
			for (int i = 0; i < width; i++) {

				newRow[(i + distance + width) % width] = grid[index][i];

			}

			for (int i = 0; i < width; i++) {

				grid[index][i] = newRow[i];

			}
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==2){
	    	setResult(2);
	        finish();
	    }
	}
	
	@Override
	public void onBackPressed() {
		
		setResult(2);
		finish();
	}

	private int[] flatten(){
		
		int[] newgrid = new int[height*width];
		
		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j++){
				
				newgrid[(width * i) + j] = grid[i][j];
				
			}
			
		}
		
		
		return newgrid;
	}
	
}


	
