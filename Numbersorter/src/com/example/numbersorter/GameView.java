package com.example.numbersorter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View {

	private final Game game;
	private float tileSize;
	private int height, width, selx, sely, max;
	private final Rect selRect = new Rect();

	public GameView(Context context) {
		super(context);
		game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		height = game.getHeight();
		width = game.getWidth();

		if (height > width) {
			max = height;
		} else {

			max = width;
		}

	}

	//
	// @Override
	// public void onSizeChanged(int w, int h, int oldw, int oldh) {
	//
	// tileSize = Math.min(w, h) / max;
	//
	// super.onSizeChanged(w, h, oldw, oldh);
	//
	// }

	@Override
	public void onDraw(Canvas canvas) {

		tileSize = Math.min(getHeight()/height, getWidth()/width);

		// draw the background
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// draw the grid

		Paint grid = new Paint();
		grid.setColor(getResources().getColor(R.color.test));
		grid.setStrokeWidth(4);

		float startx;
		float starty;
		
		starty = (getHeight() - tileSize * height) / 2;
		startx = (getWidth() - tileSize*width)/2;
		

		for (int i = 0; i <= height; i++) {

			canvas.drawLine(startx, starty + i * tileSize,
					startx + (width * tileSize) + 2, starty + i * tileSize, grid);


		}

		for (int i = 0; i <= width; i++) {

			canvas.drawLine(startx + i * tileSize, starty, startx + i
					* tileSize, starty + (height * tileSize) + 2, grid);
			
			
		}

	}

}
