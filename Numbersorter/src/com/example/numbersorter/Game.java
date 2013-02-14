package com.example.numbersorter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Game extends Activity {
	
	private int height, width, moveCount;
	private int[][] board;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		
		height = extras.getInt("height");
		width = extras.getInt("width");
		
		GameView gameview = new GameView(this);
		setContentView(gameview);
		gameview.requestFocus();
		
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}



}
