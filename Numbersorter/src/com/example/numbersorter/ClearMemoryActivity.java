package com.example.numbersorter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class ClearMemoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_memory);
	}

	public void cancelPressed(View view){
		
		finish();
	}
	
	public void yesPressed(View view){
		
		deleteFile(MainActivity.LEADERBOARD_SAVE);
		deleteFile(MainActivity.SAVED_GAME);
		finish();
	}

}
