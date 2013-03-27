package com.example.numbersorter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * Class which represents the game model, handles saving and loading
 * as well as changing the grid when the user makes a change.
 * @author Ricky
 *
 */
public class Game extends Activity {

	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 0;
	private GameView gameview;

	private int height, width, moveCount;
	private int[][] grid = null;
	private boolean unsolvable;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		moveCount = 0; 

		boolean continueGame = extras.getBoolean("continue");

		//If continue is pressed we want to load a file.
		if (continueGame) {
			
			FileInputStream gameFile;
			ObjectInputStream ois;
			
			try {
				
				gameFile = openFileInput(MainActivity.SAVED_GAME);
				ois = new ObjectInputStream(gameFile);
				SavedGame game = (SavedGame) ois.readObject();
				loadGame(game);
				ois.close();
				
				//if anything goes wrong just initialise a new grid
			} catch (FileNotFoundException e) {
				grid = initialise();
			} catch (StreamCorruptedException e) {
				grid = initialise();
			} catch (IOException e) {
				grid = initialise();
			} catch (ClassNotFoundException e) {
				grid = initialise();
			}
			
			
		} 
		//if continue hasn't been pressed then we're starting a new game
		else { 
			
			height = extras.getInt("height"); //get height and width from the bundle
			width = extras.getInt("width");
			boolean solvableOnly = extras.getBoolean("solvableOnly");


			grid = initialise();
			
			if(solvableOnly){
				while(Math.pow(-1, getInversionNumber()) <= 0){
					
					//Keep creating grids until one is solvable
					grid = initialise();
				}
				
			}


		}


		gameview = new GameView(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		checkUnsolveable();
		setContentView(gameview);
		gameview.requestFocus();

	}

	/**
	 * Performs a check to see if the board is unsolvable
	 * and stores it in a boolean value
	 */
	private void checkUnsolveable() {

		unsolvable = (Math.pow(-1, getInversionNumber()) < 0);
	}

	/**
	 * loads game and sets the variables to what the save file contains
	 * 
	 * @param game The game that is loaded from memory
	 */
	public void loadGame(SavedGame game) {

		grid = game.getGrid();
		moveCount = game.getMoveCount();
		height = game.getHeight();
		width = game.getWidth();

	}

	/**
	 * Calculates inversion number by ticking through array and
	 * counting the numbers which are less than itself infront of it
	 * 
	 * @return the inversion number of the grid
	 */
	private int getInversionNumber() {

		int[] flatGrid = flatten();
		int counter = 0;

		for (int i = 0; i < flatGrid.length; i++) {
			for (int j = i + 1; j < flatGrid.length; j++) {
				if (flatGrid[i] > flatGrid[j]) {

					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * 
	 * @return returns the boolean saying if the grid is solvable or not
	 */
	public boolean isUnsolvable() {

		return unsolvable;
	}

	/**
	 * Initialises the grid by filling it randomly with the numbers 1 - (height*width)
	 * 
	 * @return the populated grid
	 */
	private int[][] initialise() {

		int[][] output = new int[height][width];

		ArrayList<Integer> numbers = new ArrayList<Integer>();

		//build an array to pull the numbers from, ensures they are used only once.
		for (int i = 1; i <= height * width; i++) {

			numbers.add(i); 

		}

		//use a random to pick the numbers from the array.
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

	/**
	 * gets the height of the grid	
	 * @return 
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * returns width of grid
	 * @return 
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * returns grid
	 * @return
	 */
	public int[][] getGrid() {

		return grid;

	}

	/**
	 * checks if the game is finished by checking the inversion value
	 * 
	 * @return a boolean which says whether the game is finished
	 */
	public boolean checkfinished() {

		return (getInversionNumber() == 0);

	}
	
	/**
	 * updates the grid when a move has been made and increments the movecount
	 * 
	 * @param direction the direction in which the numbers are moving (horizontal or vertical)
	 * @param index The index of the row/column to move
	 * @param distance how many places they numbers will move
	 */
	public void swipe(int direction, int index, int distance){
		
		grid = getMovedGrid(direction, index, distance);
		moveCount++;
	}

	/**
	 * Models a move on the grid and returns the outcome of the move
	 * 
	 * @param direction the direction in which the numbers are moving (horizontal or vertical)
	 * @param index The index of the row/column to move
	 * @param distance how many places they numbers will move
	 * @return the new grid layout
	 */
	public int[][] getMovedGrid(int direction, int index, int distance) {

		int[][] newGrid = copyGrid(); //create a copy so it doesn't alter the current grid
		
		if (direction == VERTICAL) {

			int[] newColumn = new int[height]; //create a new column since the swipe is vertical

			for (int i = 0; i < height; i++) {

				newColumn[(i + distance + height) % height] = grid[i][index]; //move each number along by the distance

			}

			for (int i = 0; i < height; i++) {

				newGrid[i][index] = newColumn[i]; //place the new column in the grid

			}

		} else {

			int[] newRow = new int[width]; //Create a new row since the swipe is horizontal
			for (int i = 0; i < width; i++) {

				newRow[(i + distance + width) % width] = grid[index][i]; // move the row contents

			}

			for (int i = 0; i < width; i++) {

				newGrid[index][i] = newRow[i]; //place it in the grid

			}
		}

		return newGrid;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2) {
			setResult(2);
			finish();
		}
	}

	@Override
	public void onBackPressed() {

		setResult(2);
		finish();
	}

	/**
	 * turns the 2D array into a 1D one for checking the inversion number
	 * 
	 * @return The one dimensional grid
	 * 
	 */
	private int[] flatten() {

		int[] newgrid = new int[height * width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				newgrid[(width * i) + j] = grid[i][j];

			}

		}

		return newgrid;
	}

	/**
	 * gets a number from the grid
	 * @param height the row that the number is on
	 * @param width the the column that the number is on
	 * @return The number occupying the grid position
	 */
	public int getNumber(int height, int width) {

		return grid[height][width];
	}
	
	
	/**
	 * returns the movecount
	 * @return
	 */
	public int getMoveCount() {

		return moveCount;
	}

	/**
	 * Saves the game data to file. Creates a new Saved Game object and writes it out.
	 */
	public void saveGame() {
		
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			
			fos = openFileOutput(MainActivity.SAVED_GAME, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(new SavedGame(grid,moveCount, height, width));
			oos.close();
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			Log.d("SaveIOerror", e.toString(), e);
		}
		
		
	}

	/**
	 * creates a copy of the current grid so that it doesn't 
	 * mess with the original
	 * @return a copy of the grid.
	 */
	private int[][] copyGrid(){
		
		int[][] copyGrid = new int[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				
				copyGrid[i][j] = grid[i][j];
				
			}
		}
		
		return copyGrid;
	}
}
