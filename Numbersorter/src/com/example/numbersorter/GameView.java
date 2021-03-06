package com.example.numbersorter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * The class which draws the game model on screen
 * 
 * @author Ricky
 *
 */
public class GameView extends View {

	private final Game game;
	private final double TEXTPADDINGH = 0.5;
	private final double TEXTPADDINGV = 0.65;
	private float tileSize;
	private int height, width;
	private int statusBarHeight;
	private boolean toDraw = false;
	private int[][] drawGrid;

	private float initialX = 0;
	private float initialY = 0;
	private float deltaX = 0;
	private float deltaY = 0;
	private float moveX = 0;
	private float moveY = 0;
	private float startx, starty;
	private Rect unsolvebutton, quitbutton;


	public GameView(Context context) {
		super(context);
		game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);

		height = game.getHeight();
		width = game.getWidth();

	}

	@Override
	public void onDraw(Canvas canvas) {

		//calculate the space that the grid occupies, leaving space for buttons.
		float gridheight = getHeight() - Math.max(getWidth(), getHeight()) / 8; 

		Context context = getContext();
		statusBarHeight = (int) Math.ceil(25 * context.getResources()
				.getDisplayMetrics().density);

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

		int[][] gameGrid;
		
		if(toDraw){
			
			gameGrid = drawGrid;
		}
		else{
			
			gameGrid = game.getGrid();
		}

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

		unsolvebutton = new Rect(5, (int) (gridheight + 5),
				(getWidth() / 2 - 5), getHeight() - 5);
		quitbutton = new Rect(getWidth() / 2 + 5, (int) (gridheight + 5),
				getWidth() - 5, getHeight() - 5);

		canvas.drawRect(unsolvebutton, buttons);
		canvas.drawRect(quitbutton, buttons);

		Paint buttonText = new Paint(Paint.ANTI_ALIAS_FLAG);
		buttonText.setColor(getResources().getColor(R.color.white));

		canvas.drawText("Unsolvable?",
				unsolvebutton.left + unsolvebutton.width() / 8,
				unsolvebutton.top + unsolvebutton.height() / 2, buttonText);
		canvas.drawText("Quit", quitbutton.left + unsolvebutton.width() / 7,
				quitbutton.top + quitbutton.height() / 2, buttonText);


	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int column = 0;
		int row = 0;
		boolean ingrid = false;
		boolean inUnsolve = false;
		boolean inQuit = false;

		synchronized (event) {
			try {
				event.wait(16);

				// when user touches the screen
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// reset deltaX and deltaY
					deltaX = deltaY = 0;

					// get initial positions
					initialX = event.getRawX();
					initialY = event.getRawY() - statusBarHeight;
				}

				if (unsolvebutton.contains((int) initialX, (int) initialY)) {

					inUnsolve = true;

				}

				if (quitbutton.contains((int) initialX, (int) initialY)) {

					inQuit = true;

				}

				if (initialX >= startx
						&& initialX <= startx + (tileSize * width)
						&& initialY >= starty
						&& initialY <= starty + (tileSize * height)) {

					row = (int) Math.floor((initialY - starty) / tileSize);
					column = (int) Math.floor((initialX - startx) / tileSize);
					ingrid = true;

				}

				if (event.getAction() == MotionEvent.ACTION_MOVE) {

					if (ingrid) {

						moveX = event.getRawX();
						moveY = event.getRawY() - statusBarHeight;

						int moveRow = (int) Math.floor((moveY - starty)
								/ tileSize);
						int moveColumn = (int) Math.floor((moveX - startx)
								/ tileSize);

						float deltaMoveX = moveX - initialX;
						float deltaMoveY = moveY - initialY;

//						alphaNumber = game.getNumber(row, column);

						if (moveRow != row | moveColumn != column) {

							// Swiped up/down
							if (Math.abs(deltaMoveY) > Math.abs(deltaMoveX)) {

								if (moveRow < height && moveRow >= 0) {
									toDraw = true;
									drawGrid = game.getMovedGrid(Game.VERTICAL, column, moveRow - row);
								} 
								else {

									toDraw = false;
								}

							} else { // Swiped left/right

								if (moveColumn < width && moveColumn >= 0) {
									toDraw = true;
									drawGrid = game.getMovedGrid(Game.HORIZONTAL, row, moveColumn - column);
								} 
								else {

									toDraw = false;
								}
							}
						} 
						else {

							toDraw = false;
						}

						invalidate();
						return true;
					}

				}

				// when screen is released
				if (event.getAction() == MotionEvent.ACTION_UP) {
					float releaseX = event.getRawX();
					float releaseY = event.getRawY() - statusBarHeight;
					deltaX = releaseX - initialX;
					deltaY = releaseY - initialY;


					if (unsolvebutton.contains((int) releaseX, (int) releaseY)
							&& inUnsolve) {

						Context context = getContext();
						Intent i = new Intent(context, UnsolveDialog.class);
						i.putExtra("unsolveable", game.isUnsolvable());
						((Activity) context).startActivityForResult(i, 0);
						return true;
					}

					if (quitbutton.contains((int) releaseX, (int) releaseY)
							&& inQuit) {

						game.saveGame();
						((Game) getContext()).setResult(2);
						((Game) getContext()).finish();

					}

					int newRow = (int) Math.floor((releaseY - starty)
							/ tileSize);
					int newColumn = (int) Math.floor((releaseX - startx)
							/ tileSize);

					if (ingrid && (newColumn != column || newRow != row)) {
						// swiped up
						if (Math.abs(deltaY) > Math.abs(deltaX)) {

							game.swipe(Game.VERTICAL, column, newRow - row);

						} else {

							game.swipe(Game.HORIZONTAL, row, newColumn - column);
						}

						toDraw = false;
						invalidate();

						if (game.checkfinished()) {

							Context context = getContext();
							Intent i = new Intent(context, WinActivity.class);
							i.putExtra("moveCount", game.getMoveCount());
							i.putExtra("boardSize", height*width);
							((Activity) context).startActivityForResult(i, 0);
							return true;

						}

					}
				}
			}

			catch (InterruptedException e) {

				return true;
			}

		}
		return true;
	}

}
