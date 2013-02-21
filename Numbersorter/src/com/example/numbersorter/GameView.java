package com.example.numbersorter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameView extends View {

	private final Game game;
	private final double TEXTPADDINGH = 0.5;
	private final double TEXTPADDINGV = 0.65;
	private float tileSize;
	private int height, width;
	private final Rect selRect = new Rect();

	private float initialX = 0;
	private float initialY = 0;
	private float deltaX = 0;
	private float deltaY = 0;
	private float startx, starty;

	public GameView(Context context) {
		super(context);
		game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		height = game.getHeight();
		width = game.getWidth();
		
		//Remove title bar
		



	}

	@Override
	public void onDraw(Canvas canvas) {

		float gridheight = getHeight() - 50;

		tileSize = Math.min(gridheight / height, getWidth() / width);

		// draw the background
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// draw the grid

		Paint grid = new Paint();
		grid.setColor(getResources().getColor(R.color.black));
		grid.setStrokeWidth(4);

		Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
		text.setColor(getResources().getColor(R.color.black));
		text.setTextSize((float) (tileSize * 0.5));
		text.setTextAlign(Align.CENTER);

		starty = (gridheight - tileSize * height) / 2;
		startx = (getWidth() - tileSize * width) / 2;

		int[][] gameGrid = game.getGrid();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				String number = Integer.toString(gameGrid[i][j]);
				canvas.drawText(
						number,
						(int) (startx + (j * tileSize) + (TEXTPADDINGH * tileSize)),
						(int) (starty + (i * tileSize) + (TEXTPADDINGV * tileSize)),
						text);

			}

		}

		for (int i = 0; i <= height; i++) {

			canvas.drawLine(startx, starty + i * tileSize, startx
					+ (width * tileSize) + 2, starty + i * tileSize, grid);

		}

		for (int i = 0; i <= width; i++) {

			canvas.drawLine(startx + i * tileSize, starty, startx + i
					* tileSize, starty + (height * tileSize) + 2, grid);

		}

		Paint buttons = new Paint();
		buttons.setColor(getResources().getColor(R.color.black));

		Rect unsolvebutton = new Rect(5, (int) (gridheight + 5),
				(getWidth() / 2 - 5), getHeight() - 5);
		Rect quitbutton = new Rect(getWidth() / 2 + 5, (int) (gridheight + 5),
				getWidth() - 5, getHeight() - 5);

		canvas.drawRect(unsolvebutton, buttons);
		canvas.drawRect(quitbutton, buttons);

		Paint buttonText = new Paint(Paint.ANTI_ALIAS_FLAG);
		buttonText.setColor(getResources().getColor(R.color.white));

		canvas.drawText("Unsolvable?", unsolvebutton.left + 40,
				unsolvebutton.top + 25, buttonText);
		canvas.drawText("Quit", quitbutton.left + 60, quitbutton.top + 25,
				buttonText);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int column = 0;
		int row = 0;
		boolean ingrid = false;

		synchronized (event) {
			try {
				event.wait(16);

				// when user touches the screen
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// reset deltaX and deltaY
					deltaX = deltaY = 0;

					// get initial positions
					initialX = event.getRawX();
					initialY = event.getRawY();
				}

				if (initialX >= startx
						&& initialX <= startx + (tileSize * width)
						&& initialY >= starty
						&& initialY <= starty + (tileSize * height)) {

					row = (int) Math.floor((initialY - starty) / tileSize);
					column = (int) Math.floor((initialX - startx) / tileSize);
					ingrid = true;

				}

				// when screen is released
				if (event.getAction() == MotionEvent.ACTION_UP) {
					deltaX = event.getRawX() - initialX;
					deltaY = event.getRawY() - initialY;

					if (ingrid) {
						// swiped up
						if (Math.abs(deltaY) > Math.abs(deltaX)) {

							if (deltaY > tileSize) {
								game.swipe(
										game.VERTICAL,
										column,
										(int) Math.min(height - 1, deltaY
												/ tileSize));
							}

						} else {
							if (deltaX > tileSize) {

								game.swipe(
										game.HORIZONTAL,
										row,
										(int) Math.min(width - 1, deltaX
												/ tileSize));
							}

						}

						invalidate();
					}
				}
			}

			catch (InterruptedException e) {

				return true;
			}

		}
		return ingrid;
	}
}
