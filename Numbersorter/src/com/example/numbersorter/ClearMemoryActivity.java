package com.example.numbersorter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

/**
 * This class is the screen that comes up asking the user if they
 * want to clear the memory.
 * @author Ricky
 *
 */
public class ClearMemoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_memory);
	}

	/**
	 * Called when the cancel button is pressed, should close view
	 * @param view
	 */
	public void cancelPressed(View view){
		
		finish();
	}
	
	/**
	 * Called when the yes button is pressed, deletes the memory files.
	 * @param view
	 */
	public void yesPressed(View view){
		
		deleteFile(MainActivity.LEADERBOARD_SAVE);
		deleteFile(MainActivity.SAVED_GAME);
		finish();
	}

}
