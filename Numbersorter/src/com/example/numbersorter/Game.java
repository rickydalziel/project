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

		if (continueGame) {
			
			FileInputStream gameFile;
			ObjectInputStream ois;
			
			try {
				
				gameFile = openFileInput(MainActivity.SAVED_GAME);
				ois = new ObjectInputStream(gameFile);
				SavedGame game = (SavedGame) ois.readObject();
				loadGame(game);
				ois.close();
				
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
		else {
			
			height = extras.getInt("height");
			width = extras.getInt("width");
			boolean solvableOnly = extras.getBoolean("solvableOnly");


			grid = initialise();
			
			if(solvableOnly){
				while(Math.pow(-1, getInversionNumber()) <= 0){
					
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

	private void checkUnsolveable() {

		unsolvable = (Math.pow(-1, getInversionNumber()) < 0);
	}

	public void loadGame(SavedGame game) {

		grid = game.getGrid();
		moveCount = game.getMoveCount();
		height = game.getHeight();
		width = game.getWidth();

	}

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

	public boolean isUnsolvable() {

		return unsolvable;
	}

	private int[][] initialise() {

		int[][] output = new int[height][width];

		ArrayList<Integer> numbers = new ArrayList<Integer>();

		for (int i = 1; i <= height * width; i++) {

			numbers.add(i);

		}

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

		return (getInversionNumber() == 0);

	}
	
	public void swipe(int direction, int index, int distance){
		
		grid = getMovedGrid(direction, index, distance);
		moveCount++;
	}

	public int[][] getMovedGrid(int direction, int index, int distance) {
		


		int[][] newGrid = copyGrid();
		
		if (direction == VERTICAL) {

			int[] newColumn = new int[height];

			for (int i = 0; i < height; i++) {

				newColumn[(i + distance + height) % height] = grid[i][index];

			}

			for (int i = 0; i < height; i++) {

				newGrid[i][index] = newColumn[i];

			}

		} else {

			int[] newRow = new int[width];
			for (int i = 0; i < width; i++) {

				newRow[(i + distance + width) % width] = grid[index][i];

			}

			for (int i = 0; i < width; i++) {

				newGrid[index][i] = newRow[i];

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

	private int[] flatten() {

		int[] newgrid = new int[height * width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				newgrid[(width * i) + j] = grid[i][j];

			}

		}

		return newgrid;
	}

	public int getNumber(int height, int width) {

		return grid[height][width];
	}

	public int getMoveCount() {

		return moveCount;
	}

	public void saveGame() {
		
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			
			fos = openFileOutput(MainActivity.SAVED_GAME, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(new SavedGame(grid,moveCount, height, width));
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("SaveIOerror", e.toString(), e);
		}
		
		
	}

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
