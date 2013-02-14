package com.example.numbersorter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;

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


	@Override
	public void onDraw(Canvas canvas) {
		
		float gridheight = getHeight() - 50;

		tileSize = Math.min(gridheight/height, getWidth()/width);

		// draw the background
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// draw the grid

		Paint grid = new Paint();
		grid.setColor(getResources().getColor(R.color.black));
		grid.setStrokeWidth(4);

		float startx;
		float starty;
		
		starty = (gridheight - tileSize * height) / 2;
		startx = (getWidth() - tileSize*width)/2;
		

		for (int i = 0; i <= height; i++) {

			canvas.drawLine(startx, starty + i * tileSize,
					startx + (width * tileSize) + 2, starty + i * tileSize, grid);

		}

		for (int i = 0; i <= width; i++) {

			canvas.drawLine(startx + i * tileSize, starty, startx + i
					* tileSize, starty + (height * tileSize) + 2, grid);			
			
		}
		
		Paint buttons = new Paint();
		buttons.setColor(getResources().getColor(R.color.black));
		
		Rect unsolvebutton = new Rect(5, (int) (gridheight+5), (getWidth()/2 - 5), getHeight() -5 );
		Rect quitbutton = new Rect(getWidth()/2 +5, (int) (gridheight+5), getWidth()-5, getHeight()-5);
		
		canvas.drawRect(unsolvebutton , buttons);
		canvas.drawRect(quitbutton, buttons); 
		
		Paint buttonText = new Paint(Paint.ANTI_ALIAS_FLAG);
		buttonText.setColor(getResources().getColor(R.color.white));
		
		canvas.drawText("Unsolvable?", unsolvebutton.left + 40, unsolvebutton.top + 25, buttonText);
		canvas.drawText("Quit", quitbutton.left + 60, quitbutton.top+25, buttonText);
				

	}



	
	

}
